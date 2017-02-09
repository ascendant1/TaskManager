package com.controller;

import com.model.*;
import com.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static com.controller.Manager.logger;

public class Controller implements ActionListener {

    private Model model;
    private MainView view;
    //private ModalView modalView;
    private AddController addController;

    public void setModel (Model model) {
        this.model = model;
        Data data = new DataSave ();
        try {
            data.upload(this.model, new File (Data.FILE_DATA));
        }
        catch (IOException e) {
            System.out.println("upload dont work");
        }
        catch (ClassNotFoundException e) {

        }

    }

    public void saveModel () {
        Data data = new DataSave ();

        try {
            data.unload(this.model, new File (Data.FILE_DATA));
        }
        catch (IOException e) {
            System.out.println("save dont work");
        }
    }

    public void setView (MainView view) {
        this.view = view;
        this.view.addActionListener (this);

        addController  = new AddController();

        this.view.update (this.model);
    }

    public void actionPerformed (ActionEvent event) {
        MainView view = (MainView) event.getSource();

        if( event.getActionCommand().equals (View.ACTION_CLOSE) ) {
            saveModel();
            view.close ();
            logger.info ("End the work!");
            System.exit (0);
        }

        else if (event.getActionCommand().equals(View.ACTION_UPDATE)) {
            this.view.update (this.model);
            logger.info ("View updated");
        }

        else if (event.getActionCommand().equals(View.ACTION_ADD_TASK)) {
            ModalView modalView = new AddEditForm(this.view.getFrame());
            addController.setView(modalView);

            modalView.setVisible(true);

            if (addController.getTask() != null) {
                this.model.add(addController.getTask());
                logger.info("\"" + addController.getTask().getTitle() + "\"" + " successfully added to the model.");
            }

            addController.restart();
            this.view.update(this.model);

        }

        else if (event.getActionCommand().equals(View.ACTION_EDIT_TASK)) {
            try {
                if (model.size() > 0) {
                    ModalView modalView = new AddEditForm(this.view.getFrame());
                    addController.setTask(this.model.getTask(this.view.getSelectedIndex()));
                    addController.setView(modalView);
                    addController.updateView();

                    modalView.setVisible(true);

                    addController.restart();
                    this.view.update (this.model);
                }
            }
            catch (ModelException e) {
                this.view.showError(e.getMessage());
            }
        }

        else if (event.getActionCommand().equals(View.ACTION_REMOVE_TASK)) {
            try {
                if (model.size() > 0) {
                    model.remove(model.getTask(this.view.getSelectedIndex()));
                }
            }
            catch (ModelException e) {
                this.view.showError(e.getMessage());
            }

            this.view.update (this.model);
        }
    }

    public Model getModel () {
        return this.model;
    }

    public void update () {
        this.view.update(this.model);
    }

    public void showMessage (String string) {
        this.view.showMessage (string);
    }
}

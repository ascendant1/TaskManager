package com.controller;

import com.model.*;
import com.tasks.Task;
import com.view.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import static com.controller.Manager.logger;

public class Controller implements ActionListener {

    private Model model;
    private MainView view;
    private ModalView modalView;

    public void setModel (Model model) {
        this.model = model;
        Data data = new DataSave ();
        try {
            data.upload(this.model, new File (Data.FILE_DATA));
        }
        catch (IOException e) {
            logger.warn ("upload don`t work!");
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
            logger.warn("save don`t work");
        }
    }

    public void setView (MainView view) {
        this.view = view;
        this.view.addActionListener (this);

        this.view.update (this.model);
    }

    public void actionPerformed (ActionEvent event) {
        View view = (View) event.getSource();

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
            this.modalView = new AddEditForm(this.view.getFrame());
            modalView.addActionListener (this);
            logger.info ("Modal view created.");
            modalView.setVisible(true);
            logger.info ("Modal view visible");

            if (model.getTask() != null) {
                this.model.add();
            }
            this.view.update(this.model);
        }

        else if (event.getActionCommand().equals(View.ACTION_EDIT_TASK)) {
            logger.info ("Enter to edit task.");
            modalView = new AddEditForm(this.view.getFrame());

            try {
                model.setTask(model.getTask(this.view.getSelectedIndex()));
            }
            catch (ModelException e) {
                this.view.showError(e.getMessage());
            }

            modalView.update (model);
            modalView.addActionListener(this);

            modalView.setVisible (true);

            this.view.update (model);
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

        else if (event.getActionCommand().equals(ModalView.ACTION_SAVE_TASK)) {
            model.setTask(new Task());
            try {
                model.setTitle(this.modalView.getTitle());
                if (this.modalView.getRepeated()) {
                    model.setTime(this.modalView.getStartTime(),
                            this.modalView.getEndTime(),
                            this.modalView.getInterval());
                } else {
                    model.setTime(this.modalView.getTime());
                }
                model.setActive(this.modalView.getActive());

                logger.info ("\"" + model.getTask().getTitle() + "\"" + " start time: " + model.getTask().getStartTime() + " added!");
                this.modalView.close ();

            } catch (ModelException e) {
                modalView.showError(e.getMessage());
            }
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

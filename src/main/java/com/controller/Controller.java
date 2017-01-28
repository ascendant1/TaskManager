package com.controller;

import com.model.*;
import com.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller implements ActionListener {

    private TaskList model;
    private View view;

    public void setModel (TaskList model) {
        this.model = model;
    }


    public void setView (View view) {
        this.view = view;
        this.view.addActionListener (this);

        this.view.update(model);
    }

    public void actionPerformed (ActionEvent event) {
        View view = (View) event.getSource();

        if( event.getActionCommand().equals (View.ACTION_CLOSE) ) {
            view.close ();
            System.exit (0);
        }

        if (event.getActionCommand().equals(View.ACTION_UPDATE)) {
            this.view.update (this.model);
            System.out.println ("ok");
        }

        if (event.getActionCommand().equals(View.ACTION_ADD_TASK)) {
            AddController temp = new AddController ();
            AddEditForm dialog = new AddEditForm();
            temp.setView(dialog);

            dialog.setVisible(true);
            if (temp.getTask() != null)
                this.model.add(temp.getTask ());
            this.view.update (this.model);
        }

        if (event.getActionCommand().equals(View.ACTION_SAVE_TASK)) {
        }
        if (event.getActionCommand().equals(View.ACTION_REMOVE_TASK)) {

            if (model.size() > 0) {
                model.remove( model.getTask( this.view.getIndexRemove() ) );
            }

            this.view.update(this.model);
        }
    }
}

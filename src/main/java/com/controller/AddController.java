package com.controller;

import com.model.Task;
import com.view.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AddController implements ActionListener {
    private Task task;

    private View view;

    public AddController () {

    }

    public AddController (Task task) {

    }

    public void setView (View view) {
        this.view = view;

        this.view.addActionListener(this);

    }

    public void actionPerformed (ActionEvent event) {
        View view = (View) event.getSource();

        if( event.getActionCommand().equals (View.ACTION_CLOSE) ) {
            this.view.close ();
        }

        if (event.getActionCommand().equals (View.ACTION_SAVE_TASK)) {
            this.task = new Task (this.view.getTitle (), new Date() );
            this.view.close ();
        }
    }

    public Task getTask () {
        return this.task;
    }
}

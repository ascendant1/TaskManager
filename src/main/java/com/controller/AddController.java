package com.controller;

import com.tasks.Task;
import com.view.ModalView;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddController implements ActionListener {
    private Task task;

    private ModalView view;

    public AddController () {

    }

    public void setTask (Task task) {
        this.task = task;
    }

    public void setView (ModalView view) {
        this.view = view;

        this.view.addActionListener(this);
    }

    public void restart () {
        this.task = null;
    }
    public void actionPerformed (ActionEvent event) {
        ModalView view = (ModalView) event.getSource();

        if( event.getActionCommand().equals (ModalView.ACTION_CLOSE) ) {
            this.view.close();


        }

        else if (event.getActionCommand().equals (ModalView.ACTION_SAVE_TASK)) {
            try {
                if (task == null) {
                    addTask();

                }
                else {
                    editTask();
                }
                this.view.close();
            }
            catch (Exception e) {
                this.view.showError(e.getMessage());
            }
        }

    }

    private void addTask () {
        Task temp = new Task ();
        temp.setTitle(this.view.getTitle());
        if (this.view.getRepeated()) {
            temp.setTime(this.view.getStartTime(),
                    this.view.getEndTime(),
                    this.view.getInterval());
        }
        else {
            temp.setTime (this.view.getTime());
        }
        temp.setActive(this.view.getActive());

        this.task = temp;
    }

    public void updateView () {
        this.view.setTitle(this.task.getTitle());

        if (this.task.isRepeated()){
            this.view.setRepeated(true);
            this.view.setTime (this.task.getStartTime(),
                    this.task.getEndTime(),
                    this.task.getRepeatInterval());
        }
        else {
            this.view.setRepeated(false);
            this.view.setTime(this.task.getTime());
        }

        this.view.setActive(this.task.isActive ());
    }

    private void editTask () {
        task.setTitle(this.view.getTitle());
        if (this.view.getRepeated()) {
            task.setTime(this.view.getStartTime(),
                    this.view.getEndTime(),
                    this.view.getInterval());
        }
        else {
            task.setTime (this.view.getTime());
        }
        task.setActive(this.view.getActive());
    }

    public Task getTask () {
        return this.task;
    }
}

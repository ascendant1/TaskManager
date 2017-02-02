package com.controller;

import com.model.*;
import com.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class Controller implements ActionListener {

    private TaskList model;
    private View view;

    public void setModel (TaskList model) {
        this.model = model;
    }


    public void setView (View view) {
        this.view = view;
        this.view.addActionListener (this);

        this.view.update (this.model, getCalendar());
    }

    public void actionPerformed (ActionEvent event) {
        View view = (View) event.getSource();

        if( event.getActionCommand().equals (View.ACTION_CLOSE) ) {
            view.close ();
            System.exit (0);
        }

        if (event.getActionCommand().equals(View.ACTION_UPDATE)) {
            this.view.update (this.model, getCalendar());
            //this.view.updateCalendar(Tasks.calendar(this.model, new Date (), new Date (new Date().getTime() + 7 * View.TIME_MS_DAY)));
        }

        /*if (event.getActionCommand().equals(View.ACTION_CALENDAR)) {
           // this.view.update(this.model, Tasks.calendar(this.model, new Date (), new Date (new Date().getTime() + 7 * View.TIME_MS_DAY)));
            this.view.update (this.model, getCalendar());
        }*/

        if (event.getActionCommand().equals(View.ACTION_ADD_TASK)) {
            AddController temp = new AddController ();
            AddEditForm dialog = new AddEditForm();
            temp.setView(dialog);

            dialog.setVisible(true);
            if (temp.getTask() != null)
                this.model.add(temp.getTask ());

            this.view.update(this.model, getCalendar());

        }

        if (event.getActionCommand().equals(View.ACTION_EDIT_TASK)) {
            try {
                if (model.size() > 0) {
                    AddController temp = new AddController(model.getTask(this.view.getSelectedIndex()));
                    AddEditForm dialog = new AddEditForm();
                    temp.setView(dialog);
                    temp.updateView();
                    dialog.setVisible(true);


                    this.view.update (this.model, getCalendar());
                }
            }
            catch (Exception e) {
                this.view.showError(e.getMessage());
            }
        }

        if (event.getActionCommand().equals(View.ACTION_REMOVE_TASK)) {

            if (model.size() > 0) {
                model.remove( model.getTask( this.view.getSelectedIndex() ) );
            }

            this.view.update (this.model, getCalendar());
        }
    }

    private SortedMap<Date, Set<Task>> getCalendar () {
        return Tasks.calendar(this.model, new Date (), new Date (new Date().getTime() + 7 * View.TIME_MS_DAY));
    }
}

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


    public void setView (MainForm view) {
        this.view = view;
        this.view.addActionListener (this);
        this.view.updateAllTasksArea (this.model);
    }

    public void actionPerformed (ActionEvent event) {
        View view = (View) event.getSource();

        if( event.getActionCommand().equals (View.ACTION_CLOSE) ) {
            view.close ();
            System.exit (0);
        }

        if (event.getActionCommand().equals(View.ACTION_UPDATE)) {
            view.updateAllTasksArea(this.model);
            System.out.println ("ok");
        }
    }
}

package com.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionController extends Controller{

    public static void addNewTaskButtonListener () {
        view.addNewTaskListener(new ActionListener() {
            public void actionPerformed (ActionEvent event) {
                view.updateAllTasksArea(model);
            }
        });
    }
}

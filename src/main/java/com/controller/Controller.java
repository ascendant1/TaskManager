package com.controller;

import com.model.*;
import com.view.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {
    public static void main(String[] args) {
        Controller controller = new Controller ();
        TaskList model = new LinkedTaskList();
        MainForm view = new MainForm ();

        controller.setModel(model);
        controller.setView(view);

        Task task = new Task ("football", new Date());
        model.add (task);

    }

    protected static TaskList model;
    protected static MainForm view;

    public void setModel (TaskList model) {

        Controller.model = model;

    }


    public void setView (MainForm view) {
        Controller.view = view;

        ActionController.addNewTaskButtonListener();

        Controller.view.updateAllTasksArea (Controller.model);
    }
}

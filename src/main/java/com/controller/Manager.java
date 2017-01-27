package com.controller;

import com.model.LinkedTaskList;
import com.model.Task;
import com.model.TaskList;
import com.view.MainForm;

import java.util.Date;

public class Manager {
    public static void main(String[] args) {
        Controller controller = new Controller ();
        TaskList model = new LinkedTaskList();
        MainForm view = new MainForm ();

        controller.setModel(model);
        controller.setView(view);

        Task task = new Task ("football", new Date());
        model.add (task);

    }
}

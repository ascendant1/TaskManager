package com.controller;

import com.model.*;
import com.view.MainForm;
import com.view.MainView;

public class Manager {
    public static void main(String[] args) {
        Controller controller = new Controller ();
        Model model = new Model();
        MainView view = new MainForm();

        controller.setModel(model);
        controller.setView(view);

        Notifier notifier = new Notifier (controller);
        notifier.run ();
    }
}

package com.controller;

import com.model.*;
import com.view.MainForm;
import com.view.MainView;
import org.apache.log4j.Logger;


public class Manager {
    public static Logger logger = Logger.getLogger(Manager.class);

    public static void main(String[] args) {
        logger.info ("Start the work");
        Controller controller = new Controller ();
        Model model = new Model();
        MainView view = new MainForm();

        controller.setModel(model);
        controller.setView(view);

        Notifier notifier = new Notifier (controller);
        notifier.run ();
    }
}

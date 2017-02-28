package com.controller;

import com.tasks.Task;
import org.apache.log4j.Logger;
import java.util.Date;

public class Notifier extends Controller implements Runnable{
    private static final Logger logger = Logger.getLogger(Notifier.class);

    public static final long CHECK_TIME = 10000;

    private Controller controller;

    Notifier (Controller controller) {
        this.controller = controller;
    }

    public synchronized void run () {
        while (true) {

            for (Task task : controller.getModel().getModel()){
                check (task);
            }

            try{
                wait (CHECK_TIME);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void check (Task task) {
        if (task.nextTimeAfter(new Date()) != null) {
            long nextTime = task.nextTimeAfter(new Date()).getTime();
        }
        else {
            task.setActive(false);
        }

        if (task.isActive()) {
            if (task.isRepeated()) {
                if (Math.abs (task.nextTimeAfter(new Date()).getTime() - System.currentTimeMillis()) < CHECK_TIME ) {
                    controller.showMessage ("It`s time for " + "\"" + task.getTitle() + "\"" + " !");
                    logger.info("Notifier - "+ "\"" + task.getTitle() + "\"");
                }
            }
            else {
                if ( Math.abs(task.getTime().getTime() - System.currentTimeMillis()) < CHECK_TIME ) {
                    controller.showMessage ("It`s time for " + "\"" + task.getTitle() + "\"" + " !");
                    task.setActive(false);
                    logger.info("Notifier - " + "\"" + task.getTitle() + "\"");
                }
            }
        }
    }
}

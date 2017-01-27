package com.view;

import com.model.TaskList;

import java.awt.event.*;
/**
   * View
   * work only with model
 */
public interface View {
    String ACTION_CLOSE = "close";
    String ACTION_UPDATE = "update";

    //void addNewTask ();

    void addActionListener (ActionListener al);

    void updateAllTasksArea (TaskList model);

    void update ();

    void close ();

    void showError (String message);

}

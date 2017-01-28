package com.view;

import com.model.TaskList;

import javax.swing.*;
import java.awt.event.*;
/**
   * View
   * work only with model
 */
public interface View {
    String ACTION_CLOSE = "close";
    String ACTION_UPDATE = "update";
    String ACTION_ADD_TASK = "add";
    String ACTION_REMOVE_TASK = "remove";
    String ACTION_SAVE_TASK = "save";

    String getTitle ();

    JFrame getFrame ();
    void addActionListener (ActionListener al);

    int getIndexRemove ();

    void update (TaskList model);

    void close ();

    void showError (String message);

}

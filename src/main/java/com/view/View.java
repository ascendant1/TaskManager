package com.view;

import com.model.Task;
import com.model.TaskList;

import javax.swing.*;
import java.awt.event.*;
import java.util.Date;
import java.util.Set;
import java.util.SortedMap;

/**
   * View
   * work only with model
 */
public interface View {
    String ACTION_CLOSE = "close";
    String ACTION_UPDATE = "update";
    String ACTION_ADD_TASK = "add";
    String ACTION_EDIT_TASK = "edit";
    String ACTION_REMOVE_TASK = "remove";
    String ACTION_CALENDAR = "week";
    long TIME_MS_DAY = 86400000;

    JFrame getFrame ();

    void addActionListener (ActionListener al);

    void removeActionListener (ActionListener al);

    int getSelectedIndex ();

    void update (TaskList model, SortedMap<Date, Set<Task>> map);

    void close ();

    void showError (String message);

}

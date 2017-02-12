package com.view;

import com.model.Model;
import java.awt.event.*;

public interface View {
    String ACTION_CLOSE = "close";
    String ACTION_UPDATE = "update";
    String ACTION_ADD_TASK = "add";
    String ACTION_EDIT_TASK = "edit";
    String ACTION_REMOVE_TASK = "remove";
    long TIME_MS_DAY = 86400000;

    void addActionListener (ActionListener al);

    void removeActionListener (ActionListener al);

    //Методы управления видом

    void close ();

    void update (Model model);

    void showError (String message);

    void showMessage (String message);

}

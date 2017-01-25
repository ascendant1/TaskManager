package com.view;

import java.awt.event.*;
/**
   * View
   * work only with model
 */
public interface View {

    //void addNewTask ();

    void update ();

    void close ();

    void showError (String message);

}

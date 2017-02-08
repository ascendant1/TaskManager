package com.view;

import com.model.Model;

import javax.swing.*;

public interface MainView extends View{
    int getSelectedIndex ();

    void update (Model model);

    JFrame getFrame ();
}

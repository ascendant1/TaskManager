package com.view;

import com.model.Model;

import javax.swing.*;

public abstract class SwingMainView extends SwingView implements MainView{
    public abstract int getSelectedIndex ();

    public abstract void update (Model model);

    public abstract JFrame getFrame ();

    public void close() {
        frame.setVisible(false);
        frame.dispose();
    }
}

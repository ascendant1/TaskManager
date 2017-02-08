package com.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class SwingModalView extends SwingView implements ModalView {
    private static final int DIALOG_WIDTH = 450;
    private static final int DIALOG_HEIGHT = 300;

    private List<ActionListener> listeners = new ArrayList<ActionListener>();
    protected JDialog dialog;

    public SwingModalView () {

    }

    public SwingModalView (JFrame frame) {
        this.frame = frame;
    }

    public void createDialog () {
        dialog = new JDialog(frame);
        dialog.setTitle("Add/edit");
        dialog.setSize(new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT));
        dialog.setLocationRelativeTo(frame);
        dialog.setModal(true);
    }

    public void setVisible (boolean flag) {
        dialog.setVisible(flag);
    }
    public void close() {
        dialog.setVisible(false);
        dialog.dispose();
    }
}

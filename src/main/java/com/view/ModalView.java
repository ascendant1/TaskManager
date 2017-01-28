package com.view;

import com.model.TaskList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class ModalView extends JDialog implements View {
    private List<ActionListener> listeners = new ArrayList<ActionListener>();

    public void addActionListener (ActionListener al) {
        listeners.add (al);
    }

    public void removeActionListener (ActionListener al) {
        listeners.remove (al);
    }

    public abstract void update(TaskList model);

    protected void fireAction(String command) {
        ActionEvent event = new ActionEvent(this, 0, command);
        for (Iterator listener = listeners.iterator();
             listener.hasNext();) {
            ((ActionListener) listener.next()).actionPerformed(event);
        }
    }

    public JFrame getFrame () {
        return new JFrame();
    }

    public void close() {
        this.setVisible(false);
        this.dispose();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

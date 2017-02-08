package com.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public abstract class SwingView implements View {
    private List <ActionListener> listeners = new ArrayList <ActionListener> ();
    protected JFrame frame = new JFrame ();

    public void addActionListener (ActionListener al) {
        listeners.add (al);
    }

    public void removeActionListener (ActionListener al) {
        listeners.remove (al);
    }

    protected void fireAction(String command) {
        ActionEvent event = new ActionEvent(this, 0, command);
        for (Iterator listener = listeners.iterator();
             listener.hasNext();) {
            ((ActionListener) listener.next()).actionPerformed(event);
        }
    }

    public abstract void close ();

    public void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showMessage (String message) {
        JOptionPane.showMessageDialog(frame, message, "Reminder", JOptionPane.INFORMATION_MESSAGE);
    }
}

package com.view;

import com.model.Model;
import javax.swing.*;
import java.awt.event.*;
import java.util.Date;

public class AddEditForm extends SwingModalView {

    private static final int DAYS = 1000;
    private static final int HOURS = 23;
    private static final int MINUTES = 59;

    private JPanel contentPane;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JTextField titleField;
    private JRadioButton repeatedRadioButton;
    private JCheckBox activeCheckBox;
    private JSpinner endTimeField;
    private JSpinner startTimeField;
    private JSpinner timeField;
    private JSpinner intervalDaysField;
    private JSpinner intervalMinutesField;
    private JSpinner intervalHoursField;
    private JPanel intervalPanel;

    public AddEditForm(JFrame frame) {
        super(frame);
        createDialog();
        dialog.setContentPane(contentPane);
        dialog.setResizable(false);

        startTimeField.setEnabled(false);
        endTimeField.setEnabled(false);
        setEnabledInterval(false);
        setIntervalField();
        setTimeSpinner(timeField);
        setTimeSpinner(startTimeField);
        setTimeSpinner(endTimeField);


        dialog.getRootPane().setDefaultButton(buttonSave);

        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireAction(ModalView.ACTION_SAVE_TASK);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
                fireAction(ModalView.ACTION_CLOSE_MODAL);
            }
        });

        repeatedRadioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (repeatedRadioButton.isSelected()) {
                    timeField.setEnabled(false);
                    startTimeField.setEnabled(true);
                    endTimeField.setEnabled(true);
                    setEnabledInterval(true);
                } else {
                    timeField.setEnabled(true);
                    startTimeField.setEnabled(false);
                    endTimeField.setEnabled(false);
                    setEnabledInterval(false);
                }
            }
        });

        // call onCancel() when cross is clicked
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    //Установливает значения для полей с интервалами
    private void setIntervalField() {
        intervalDaysField.setModel(new SpinnerNumberModel(0, 0, DAYS, 1));
        intervalDaysField.setEditor(new JSpinner.NumberEditor(intervalDaysField));
        intervalHoursField.setModel(new SpinnerNumberModel(0, 0, HOURS, 1));
        intervalHoursField.setEditor(new JSpinner.NumberEditor(intervalHoursField));
        intervalMinutesField.setModel(new SpinnerNumberModel(0, 0, MINUTES, 1));
        intervalMinutesField.setEditor(new JSpinner.NumberEditor(intervalMinutesField));
    }

    private void setEnabledInterval(boolean flag) {
        intervalDaysField.setEnabled(flag);
        intervalHoursField.setEnabled(flag);
        intervalMinutesField.setEnabled(flag);
    }

    private void setTimeSpinner(JSpinner spinner) {
        spinner.setModel(new SpinnerDateModel());
        spinner.setEditor(new JSpinner.DateEditor(spinner, ModalView.DATE_FORMAT));
    }

    public String getTitle() {
        return titleField.getText();
    }

    public void setTitle(String title) {
        titleField.setText(title);
    }

    public boolean getRepeated() {
        return repeatedRadioButton.isSelected();
    }

    public void setRepeated(boolean flag) {
        repeatedRadioButton.setSelected(flag);
    }

    public boolean getActive() {
        return activeCheckBox.isSelected();
    }

    public void setActive(boolean flag) {
        activeCheckBox.setSelected(flag);
    }

    public Date getTime() {
        return (Date) timeField.getValue();
    }

    public void setTime(Date date) {
        timeField.setValue(date);
    }

    public Date getStartTime() {
        return (Date) startTimeField.getValue();
    }

    public Date getEndTime() {
        return (Date) endTimeField.getValue();
    }

    public int getInterval() {
        return (Integer) intervalDaysField.getValue() * ModalView.DAY +
                (Integer) intervalHoursField.getValue() * ModalView.HOUR +
                (Integer) intervalMinutesField.getValue() * ModalView.MINUTE;
    }

    public void setInterval(int interval) {
        int days = interval / ModalView.DAY;
        int hours = (interval % ModalView.DAY) / ModalView.HOUR;
        int minutes = (interval % ModalView.HOUR) / ModalView.MINUTE;

        intervalDaysField.setValue(days);
        intervalHoursField.setValue(hours);
        intervalMinutesField.setValue(minutes);
    }

    public void setTime(Date startTime, Date endTime, int interval) {
        startTimeField.setValue(startTime);
        endTimeField.setValue(endTime);
        setInterval(interval);
    }

    public void update(Model model) {
        this.setTitle(model.getTask().getTitle());

        if (model.getTask().isRepeated()){
            this.setRepeated(true);
            this.setTime (model.getTask().getStartTime(),
                    model.getTask().getEndTime(),
                    model.getTask().getRepeatInterval());
        }
        else {
            this.setRepeated(false);
            this.setTime(model.getTask().getTime());
        }

        this.setActive(model.getTask().isActive ());
    }

    private void onSave() {
        fireAction(ModalView.ACTION_SAVE_TASK);
        dialog.dispose();
    }

    private void onCancel() {
        dialog.dispose();
    }

}

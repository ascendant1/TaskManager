package com.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import com.model.Model;
import javax.swing.*;
import java.awt.*;
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

        if (model.getTask().isRepeated()) {
            this.setRepeated(true);
            this.setTime(model.getTask().getStartTime(),
                    model.getTask().getEndTime(),
                    model.getTask().getRepeatInterval());
        } else {
            this.setRepeated(false);
            this.setTime(model.getTask().getTime());
        }

        this.setActive(model.getTask().isActive());
    }

    private void onSave() {
        fireAction(ModalView.ACTION_SAVE_TASK);
        dialog.dispose();
    }

    private void onCancel() {
        dialog.dispose();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonSave = new JButton();
        buttonSave.setText("Save");
        panel2.add(buttonSave, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(9, 12, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Title");
        panel3.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(7, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        titleField = new JTextField();
        panel3.add(titleField, new GridConstraints(0, 1, 1, 11, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        repeatedRadioButton = new JRadioButton();
        repeatedRadioButton.setText("Repeated");
        panel3.add(repeatedRadioButton, new GridConstraints(1, 1, 1, 11, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Time");
        panel3.add(label2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Start time");
        panel3.add(label3, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("End time");
        panel3.add(label4, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("Interval");
        panel3.add(label5, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        activeCheckBox = new JCheckBox();
        activeCheckBox.setText("Active");
        panel3.add(activeCheckBox, new GridConstraints(6, 1, 1, 11, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        timeField = new JSpinner();
        panel3.add(timeField, new GridConstraints(2, 1, 1, 11, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(100, -1), null, null, 0, false));
        startTimeField = new JSpinner();
        panel3.add(startTimeField, new GridConstraints(3, 1, 1, 11, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        endTimeField = new JSpinner();
        panel3.add(endTimeField, new GridConstraints(4, 1, 1, 11, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intervalPanel = new JPanel();
        intervalPanel.setLayout(new GridLayoutManager(1, 6, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(intervalPanel, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        intervalDaysField = new JSpinner();
        intervalPanel.add(intervalDaysField, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("days");
        intervalPanel.add(label6, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intervalHoursField = new JSpinner();
        intervalPanel.add(intervalHoursField, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("hours");
        intervalPanel.add(label7, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intervalMinutesField = new JSpinner();
        intervalPanel.add(intervalMinutesField, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("minutes");
        intervalPanel.add(label8, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}

package com.view;

import com.model.Model;
import com.tasks.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class MainForm extends SwingMainView {
    private static final String FRAME_TITLE = "Task Manager";
    private static final int FRAME_HEIGHT = 450;
    private static final int FRAME_WIDTH = 900;

    private JButton addNewTaskButton;
    private JPanel panelMain;
    private DefaultListModel listModel = new DefaultListModel();
    private DefaultListModel listCalendarModel = new DefaultListModel();
    private JList allTasksList;
    private JButton allTasksButton;
    private JButton quitButton;
    private JButton removeButton;
    private JButton detailsButton;
    private JButton tasksCalendarButton;
    private JTabbedPane tabbedPane;
    private JList tasksCalendarList;

    public MainForm() {
        //создеам окно
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setTitle(FRAME_TITLE);
        frame.setLocationRelativeTo(null);
        frame.add(panelMain);

        frame.setVisible(true);

        allTasksList.setModel(listModel);
        tasksCalendarList.setModel(listCalendarModel);

        this.allTasksButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_UPDATE);
                tabbedPane.setSelectedIndex(0);
            }
        });

        tasksCalendarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //fireAction(ACTION_CALENDAR);
                tabbedPane.setSelectedIndex(1);
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fireAction(ACTION_CLOSE);
            }
        });

        this.addNewTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("add");
                fireAction(ACTION_ADD_TASK);

            }
        });

        this.detailsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_EDIT_TASK);
            }
        });

        this.removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_REMOVE_TASK);
            }
        });

        this.quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fireAction(ACTION_CLOSE);
            }
        });


    }

    public JFrame getFrame() {
        return frame;
    }

    public int getSelectedIndex() {
        return allTasksList.getSelectedIndex();
    }

    //Обновить вид
    public void update(Model model) {
        //Обновить поле с тасками
        listModel.removeAllElements();
        if (model.size() == 0) {
            listModel.addElement("Task list is empty...");
        } else {
            for (Task task : model.getModel()) {
                listModel.addElement(task.toString());
            }
        }

        //Обновить календарь с тасками (период - 1 неделя)
        SortedMap<Date, Set<Task>> map = model.calendar(new Date(), new Date(new Date().getTime() + 7 * View.TIME_MS_DAY));

        listCalendarModel.removeAllElements();
        if (map.size() == 0) {
            listCalendarModel.addElement("You have no tasks on this week");
        } else {
            for (Map.Entry<Date, Set<Task>> item : map.entrySet()) {
                for (Task task : item.getValue()) {
                    listCalendarModel.addElement(task.toString());
                }
            }
        }
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
        panelMain = new JPanel();
        panelMain.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(7, 2, new Insets(0, 0, 0, 0), -1, -1));
        allTasksButton = new JButton();
        allTasksButton.setText("All tasks list");
        panelMain.add(allTasksButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panelMain.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tasksCalendarButton = new JButton();
        tasksCalendarButton.setText("Tasks on week");
        panelMain.add(tasksCalendarButton, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addNewTaskButton = new JButton();
        addNewTaskButton.setText("Add new task");
        panelMain.add(addNewTaskButton, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        detailsButton = new JButton();
        detailsButton.setText("Details");
        panelMain.add(detailsButton, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        removeButton = new JButton();
        removeButton.setText("Remove");
        panelMain.add(removeButton, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        quitButton = new JButton();
        quitButton.setText("Quit");
        panelMain.add(quitButton, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane = new JTabbedPane();
        panelMain.add(tabbedPane, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 6, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(750, 200), null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("All Tasks", panel1);
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        allTasksList = new JList();
        scrollPane1.setViewportView(allTasksList);
        final JScrollPane scrollPane2 = new JScrollPane();
        tabbedPane.addTab("Tasks on week", scrollPane2);
        tasksCalendarList = new JList();
        scrollPane2.setViewportView(tasksCalendarList);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }
}

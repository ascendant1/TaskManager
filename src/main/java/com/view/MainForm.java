package com.view;

import com.model.Model;
import com.tasks.Task;
import javax.swing.*;
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

    private JPanel panelMain;
    private DefaultListModel listModel = new DefaultListModel();
    private DefaultListModel listCalendarModel = new DefaultListModel();
    private JList allTasksList;
    private JButton allTasksButton;
    private JButton quitButton;
    private JButton removeButton;
    private JButton detailsButton;
    private JButton tasksCalendarButton;
    private JButton addNewTaskButton;
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

}

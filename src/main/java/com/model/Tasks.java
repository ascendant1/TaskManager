package com.model;

import java.util.*;

public class Tasks {
    public static Iterable <Task> incoming (Iterable <Task> tasks, Date from, Date to) {
        if (tasks == null || from == null || to == null) {
            throw new IllegalArgumentException("Null argument");
        }
        if (from.after(to)) {
            throw new IllegalArgumentException("From is greater than to");
        }

        TaskList list = null;

        if (tasks instanceof ArrayTaskList) {
            list = new ArrayTaskList ();
        } else {
            list = new LinkedTaskList();
        }


        for (Task task : tasks) {
            if (task.isActive()) {
                Date data = task.nextTimeAfter(from);
                if ( data != null && data.compareTo(to) <= 0 ) {
                    list.add(task);
                }
            }
        }

        return list;
    }

    public static SortedMap<Date, Set<Task>> calendar (Iterable<Task> tasks, Date from, Date to) {
        if (tasks == null || from == null || to == null) {
            System.out.println ("null argument");
        }

        if (from.after(to)) {
            System.out.println ("From is greater than to");
        }

        SortedMap <Date, Set <Task>> map = new TreeMap();

        for (Task task : tasks) {

            if (task.isActive()) {
                Date nextDate = task.nextTimeAfter(from);

                while ( nextDate != null && ( nextDate.compareTo(to) <= 0) ) {

                    boolean newAdd = true;

                    for (Map.Entry<Date, Set <Task>> item : map.entrySet()) {
                        if (item.getKey().equals(nextDate)) {
                            item.getValue().add(task);
                            newAdd = false;
                        }
                    }

                    if (newAdd) {
                        Set <Task> set = new LinkedHashSet ();
                        set.add(task);
                        map.put (nextDate, set);
                    }

                    nextDate = task.nextTimeAfter(nextDate);
                }
            }

        }

        return map;
    }
}

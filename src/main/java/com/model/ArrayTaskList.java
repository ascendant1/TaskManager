package com.model;

import java.lang.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTaskList extends TaskList {

    private Task [] taskList = new Task[20];

    @Override
    public void add (Task task) {
        if(task == null) {
            throw new NullPointerException ("You try to add null-task");
        }
        if (size() < taskList.length) {
            taskList[size()] = task;
        } else {
            taskList = Arrays.copyOf(taskList, size() + (int)(size() * 0.25));

            taskList[size()] = task;
        }

    }

    @Override
    public boolean remove (Task task) {
        if(task == null) {
            throw new NullPointerException ("You try to remove null-task");
        }
        boolean result = false;
        int index = 0;
        for (Task temp : taskList) {

            if (temp != null && temp.equals (task) ) {
                for (int i = index; i < size() ; i++) {
                    taskList[i] = taskList[i + 1];
                }

                result = true;
                break;
            }

            index++;
        }

        return result;
    }

    @Override
    public int size () {
        int count = 0;
        for (Task temp : taskList) {
            if (temp != null)
                count++;
        }
        return count;
    }

    @Override
    public Task getTask (int index)  {
        if (index < 0 || index >= size()) {
            throw new  ArrayIndexOutOfBoundsException("index: " + index + ", size: " + size());
        }
        if (index < size() && index >= 0)
            return taskList[index];
        else
            return null;
    }

    public ArrayTaskListIterator iterator (){
        return new ArrayTaskListIterator();
    }


    public class ArrayTaskListIterator implements Iterator<Task> {
        private int count;

        public Task next () {
            if (!hasNext ()) {
                throw new NoSuchElementException("Out of list");
            }

            Task result = getTask (count);
            count++;

            return result;
        }

        public void remove () {
            if (count == 0){
                throw new IllegalStateException("Use remove() before next()!");
            }

            for (int i = count - 1; i < size() ; i++) {
                taskList[i] = taskList[i + 1];
            }
            count--;

        }

        public boolean hasNext () {
            if (size() > count) {
                return true;
            } else {
                return false;
            }
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArrayTaskList tasks = (ArrayTaskList) o;

        return Arrays.equals(this.taskList, tasks.taskList);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(taskList);
    }

    @Override
    public String toString() {
        return "ArrayTaskList{" +
                "taskList=" + Arrays.toString(taskList) +
                '}';
    }

    @Override
    public ArrayTaskList clone () throws CloneNotSupportedException {
        ArrayTaskList result = (ArrayTaskList) super.clone();
        result.taskList = (Task []) taskList.clone();

        for (int i = 0; i < size(); i++) {
            result.taskList[i] = (this.taskList[i].clone());
        }

        return result;
    }
}


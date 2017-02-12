package com.model;

import com.tasks.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.SortedMap;

public class Model {
    private TaskList model;

    private Task task;

    public Model() {
        this.model = new ArrayTaskList();
    }

    public Task getTask () {
        return this.task;
    }

    public void setTask (Task task) {
        this.task = task;
    }

    public void setTitle (String title) throws ModelException {
        if (title.equals("")) {
            throw new ModelException ("Title field is empty");
        }
        this.task.setTitle(title);
    }

    public void setTime (Date date) throws ModelException {
        if(date.getTime() < System.currentTimeMillis()) {
            throw new ModelException ("This task will never start!\nEnter another time please.");
        }
        this.task.setTime (date);
    }

    public void setTime (Date start, Date end, int interval) throws ModelException {
        if (interval == 0) {
            throw new ModelException ("You don`t enter interval!\nPlease, do it.");
        }

        if (start.compareTo(end) >= 0 || (end.getTime() - start.getTime()) < interval) {
            throw new ModelException("End time should be larger than start time!");
        }

        this.task.setTime (start, end, interval);
    }

    public void setActive (boolean active) {
        this.task.setActive(active);
    }

    public int size () {
        return this.model.size();
    }

    public void add () {

        this.model.add(this.task);
    }

    public boolean remove (Task task) {
        return this.model.remove(task);
    }

    public Task getTask (int index) throws ModelException{
        if (index < 0 || index >= size())
            throw new ModelException("You don`t choose the task!");
        return this.model.getTask(index);
    }

    public TaskList getModel () {
        return this.model;
    }

    public SortedMap<Date, Set<Task>> calendar (Date from, Date to) {
        return Tasks.calendar(this.model, from, to);
    }

    public void write (File file) throws IOException{
        TaskIO.writeBinary (this.model, file);
    }

    public void read (File file) throws IOException, ClassNotFoundException {
        TaskIO.readBinary (this.model, file);
    }
}

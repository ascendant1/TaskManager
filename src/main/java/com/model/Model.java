package com.model;

import com.tasks.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.SortedMap;

public class Model {
    private TaskList model;

    public Model() {
        this.model = new ArrayTaskList();
    }

    public int size () {
        return this.model.size();
    }

    public void add (Task task) {

        this.model.add(task);
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

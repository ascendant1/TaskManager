package com.model;

import java.io.Serializable;
import java.lang.*;
import java.lang.Iterable;
import java.util.Observable;

public abstract class TaskList extends Observable implements Iterable<Task> , Cloneable, Serializable {

    public abstract void add (Task task);

    public abstract boolean remove (Task task);

    public abstract int size ();

    public abstract Task getTask (int index);

}

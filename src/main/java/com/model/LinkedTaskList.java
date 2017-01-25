package com.model;

import java.lang.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedTaskList extends TaskList {
    private class Node {
        private Task task;
        private Node next;
        private Node prev;

        public Node() { };

        public Node (Task task, Node next, Node prev) {
            this.task = task;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node header;

    private int size;

    public LinkedTaskList () {
        header = new Node ();
        header.next = header.prev = header;
    }

    @Override
    public void add (Task task) {
        if (task == null) {
            throw new NullPointerException("You try to add null-task");
        }

        if (header == null) {
            header = new Node ();
        }
        Node newNode = new Node (task, header, header.prev);

        if (newNode.prev == null) {
            System.out.println("Null task");
        }
        else {

            newNode.prev.next = newNode;
            newNode.next.prev = newNode;
            size++;
        }

    }

    @Override
    public boolean remove (Task task) {
        if (task == null) {
            throw new NullPointerException("You try to remove null-task");
        }
        boolean result = false;

        Node temp = header;
        for (int i = 0; i < size(); i++) {
            temp = temp.next;
            if (task.equals(temp.task)){

                temp.prev.next = temp.next;
                temp.next.prev = temp.prev;

                temp.next = temp.prev = null;
                temp.task = null;
                size--;

                result = true;
                break;
            }
        }

        return result;
    }

    @Override
    public int size () {
        return size;
    }

    @Override
    public Task getTask (int index)     {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size());
        }

        Node element = this.header;

        if (index < (size() >> 1)) {
            for (int i = 0; i <= index; i++) {
                element = element.next;

            }
        } else {
            for (int i = size(); i > index; i--) {
                element = element.prev;

            }
        }

        return element.task;
    }

    public LinkedTaskListIterator iterator (){
        return new LinkedTaskListIterator();
    }

    public class LinkedTaskListIterator implements Iterator<Task> {
        private Node current = header;

        public Task next () {
            if (!hasNext ()) {
                throw new NoSuchElementException("Out of list");
            }

            Task result = new Task();
            result = current.next.task;
            current = current.next;
            return result;
        }

        public void remove () {
            if (current == header) {
                throw new IllegalStateException("Use remove() before next()!");
            }

            Node temp = current;

            temp.prev.next = temp.next;
            temp.next.prev = temp.prev;

            current = temp.prev;

            temp.next = temp.prev = null;
            temp.task = null;
            size--;

        }

        public boolean hasNext () {
            if (current.next.task != null) {
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

        LinkedTaskList tasks = (LinkedTaskList) o;

        if (size() != tasks.size) return false;

        boolean result = true;
        Node t1 = this.header.next;
        Node t2 = tasks.header.next;
        for (int i = 0; i < size(); i++) {
            if ( !(t1.task.equals(t2.task)) ) {
                result = false;
                break;
            }
            t1 = t1.next;
            t2 = t2.next;
        }

        return result;
    }

    @Override
    public int hashCode() {
        int result = 0;

        Node t = header.next;
        for (int i = 0; i < size(); i++) {
            result += t.task.hashCode() + size();
            t = t.next;
        }

        return result;
    }

    @Override
    public String toString() {
        String result = "";

         for (Task task : this) {
            result = result.concat(task.toString());
         }

        return result;
    }

    @Override
    public LinkedTaskList clone () throws CloneNotSupportedException {
        LinkedTaskList result = (LinkedTaskList) super.clone();

        result.header = new Node();
        result.header.next = result.header.prev = result.header;
        result.size = 0;

        Node temp = this.header.next;
        for (int i = 0; i < size(); i++) {
            result.add (temp.task.clone());
            temp = temp.next;
        }

        return result;
    }
}



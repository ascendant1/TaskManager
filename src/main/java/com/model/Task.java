package com.model;

import java.lang.*;
import java.io.Serializable;
import java.util.Date;


public class Task implements Cloneable, Serializable {
    private String title;

    private Date time;

    private Date start;

    private Date end;

    private int interval;

    private boolean active;

    private boolean repeat;

    public Task () {}

    public Task (String title, Date time) {
        if (time == null || title.equals("")) {
            throw new IllegalArgumentException ("time is null title is empty, time = " + time + ", title : " + title);
        }
        this.title = title;
        this.time = time;
        setRepeated(false);
    }

    public Task (String title, Date start, Date end, int interval) {
        if (start == null || end == null || interval <= 0 || title.equals("")) {
            throw new IllegalArgumentException ("null argument or interval less than 0 or title is empty:\n " +
                    "start = " + start + ", end = " + end + ", interval = " + interval + ", title : " + title);
        }

        if (start.compareTo(end) >= 0 || (end.getTime() - start.getTime()) < interval)
            throw new IllegalArgumentException("End time should be larger than start time!");

        this.time = null;
        this.title = title;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.active = false;
        setRepeated(true);
    }

    public String getTitle () {
        return this.title;
    }

    public void setTitle (String title) {
        if (title.equals("")) {
            throw new IllegalArgumentException ("title is empty");
        }
        this.title = title;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public boolean isActive () {
        return this.active;
    }

    public void setActive (boolean active) {
        this.active = active;
    }

    public Date getTime () {
        if (isRepeated()){
            return this.start;
        } else {
            return this.time;
        }
    }

    public void setTime (Date time) {
        if (time == null) {
            throw new IllegalArgumentException("null argument: time = " + time);
        }

        if (isRepeated()) {
            this.time = time;
            this.repeat = false;
            this.start = null;
            this.end = null;
            this.interval = 0;
        } else {
            this.time = time;
        }
    }

    public Date getStartTime () {
        if (isRepeated()) {
            return this.start;
        } else {
            return this.time;
        }
    }

    public Date getEndTime () {
        if (isRepeated()) {
            return this.end;
        } else {
            return this.time;
        }
    }

    public int getRepeatInterval () {
        if (isRepeated()) {
            return this.interval;
        } else {
            return 0;
        }
    }

    public void setTime (Date start, Date end, int interval) {
        if (start == null || end == null || interval <= 0) {
            throw new IllegalArgumentException("null argument or interval less than 0: " +
                    "start = " + start + ", end = " + end + ", interval = " + interval);
        }

        if (start.compareTo(end) >= 0 || (end.getTime() - start.getTime()) < interval)
            throw new IllegalArgumentException("End time should be larger than start time!");

        if (isRepeated()) {
            this.start = start;
            this.end = end;
            this.interval = interval;
        } else {
            this.repeat = true;
            this.start = start;
            this.end = end;
            this.interval = interval;
            this.time = null;
        }
    }

    public void setRepeated (boolean repeat) {
        this.repeat = repeat;
        if (repeat) {
            this.time = new Date(0);
        } else {
            this.start = new Date(0);
            this.end = new Date(0);
            this.interval = 0;
        }
    }

    public boolean isRepeated () {
        return this.repeat;
    }

    public Date nextTimeAfter (Date current)  {
        if (current == null) {
            throw new IllegalArgumentException("null argument: current = " + current);
        }

        if (isActive()) {
            if (isRepeated() ) {
                if (current.getTime() < this.end.getTime()) {
                    Date temp = (Date) this.start.clone();
                    while (temp.compareTo(current) <= 0) {
                        temp.setTime(temp.getTime() + getRepeatInterval() * 1000);  // 1s = 1000ms
                    }
                    if (temp.getTime() <= this.end.getTime()) {

                        return temp;
                    } else {
                        return null;
                    }
                }   else {
                    return null;
                }

            } else if (current.getTime() < this.time.getTime()) {
                return time;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;

        Task task = (Task) o;
        if (repeat == task.repeat && repeat ) {

            if ( !(this.start.equals(task.start)) ) return false;
            if ( !(this.end.equals(task.end)) ) return false;
            if ( this.getRepeatInterval() != task.getRepeatInterval() ) return false;
            if ( active != task.active) return false;

        } else if (repeat == task.repeat && repeat == false) {

            if ( !(this.time.equals(task.time)) ) return false;
            if ( active != task.active) return false;

        }else if ( this.repeat != task.repeat)
            return false;

        return title.equals(task.title);

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        if ( !isRepeated()) {
            result = 31 * result + (int) this.time.getTime();
        }
        else {
            result = 31 * result + (int) this.start.getTime();
            result = 31 * result + (int) this.end.getTime();
            result = 31 * result + this.interval;
        }

        result = 31 * result + (active ? 1 : 0);

        return result;
    }

    @Override
    public String toString() {
        if (isRepeated()) {
            return "\" " + getTitle() + "\" " + "start time = " + getStartTime() +
                    " end time = " + getEndTime() +
                    " interval = " +getRepeatInterval() +
                    " active = " + isActive();

        } else {
            return "\" " + getTitle() + "\" " + getTime() + " active = " + isActive() + "\n";
        }
    }

    @Override
    public Task clone () throws CloneNotSupportedException {
        return (Task) super.clone();
    }
}


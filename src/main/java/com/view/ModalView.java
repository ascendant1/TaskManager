package com.view;

import java.awt.event.ActionListener;
import java.util.Date;

public interface ModalView {
    String ACTION_CLOSE = "close";
    String ACTION_SAVE_TASK = "save";
    String ACTION_REPEAT = "repeat";
    String DATE_FORMAT = "yyyy.MM.dd HH:mm";
    int MS = 1000;
    int MINUTE = 60;
    int HOUR = 60 * MINUTE;
    int DAY = 24 * HOUR;

    String getTitle ();

    void setTitle (String title);

    boolean getRepeated ();

    void setRepeated (boolean flag);

    boolean getActive ();

    void setActive (boolean flag);

    Date getTime ();

    void setTime (Date date);

    Date getStartTime ();

    Date getEndTime ();

    int getInterval ();

    void setInterval (int interval);

    void setTime(Date startTime, Date endTime, int interval);

    void addActionListener (ActionListener al);

    void removeActionListener (ActionListener al);

    void close ();

    void showError (String message);
}

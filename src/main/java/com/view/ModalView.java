package com.view;

import java.awt.event.ActionListener;
import java.util.Date;

public interface ModalView extends View{
    String ACTION_SAVE_TASK = "save";
    String DATE_FORMAT = "yyyy.MM.dd HH:mm";
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

    void createDialog ();

    void setVisible (boolean flag);
}

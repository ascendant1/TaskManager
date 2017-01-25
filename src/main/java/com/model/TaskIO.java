package com.model;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskIO {

    private static final int MINUTE = 60;
    private static final int HOUR = MINUTE * 60;
    private static final int DAY  = HOUR * 24;

    private static final String FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    // binary IO

    public static void write (TaskList tasks, OutputStream out) throws IOException {

        DataOutputStream dos = new DataOutputStream (out);

        dos.writeInt (tasks.size());
        for (Task task : tasks) {
            dos.writeUTF (task.getTitle());
            dos.writeBoolean (task.isActive());
            dos.writeInt (task.getRepeatInterval());

            dos.writeLong (task.getStartTime().getTime());
            if (task.isRepeated()) {
                dos.writeLong (task.getEndTime().getTime());
            }
        }
        dos.flush ();

        dos.close();
    }

    public static void read (TaskList tasks, InputStream in) throws IOException {

        DataInputStream dis = new DataInputStream (in);

        int size = dis.readInt ();

        for (int i = 0; i < size; i++) {
            String title = dis.readUTF ();
            boolean active = dis.readBoolean ();
            int interval = dis.readInt ();
            long startTime = dis.readLong ();

            if (interval > 0) {
                long endTime = dis.readLong ();
                Task task = new Task (title, new Date(startTime), new Date (endTime), interval);
                task.setActive (active);
                tasks.add (task);
            }
            else {
                Task task = new Task (title, new Date (startTime));
                task.setActive (active);
                tasks.add (task);
            }
        }

        dis.close();
    }

    public static void writeBinary (TaskList tasks, File file) throws IOException {

        ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (file));
        oos.writeObject (tasks);
        oos.close();

    }

    public static void readBinary (TaskList tasks, File file) throws IOException, ClassNotFoundException {

        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (file));
        tasks = (TaskList) ois.readObject();
        ois.close();

    }

    // text IO

    private static void time (int seconds, PrintWriter pw) {
        int day = seconds / DAY;
        int hour = (seconds % DAY) / HOUR;
        int minute = (seconds % HOUR) / MINUTE;
        int second = seconds % MINUTE;

        if (day != 0)
            if (day > 1)
                pw.print (day + " days");
            else
                pw.print (day + " day");

        if (hour != 0) {
            if (day > 0) pw.print(" ");
            if (hour > 1)
                pw.print(hour + " hours");
            else
                pw.print(hour + " hour");
        }

        if (minute != 0) {
            if (day > 0 || hour > 0) pw.print(" ");
            if (minute > 1)
                pw.print(minute + " minutes");
            else
                pw.print(minute + " minute");
        }

        if (second != 0) {
            if (day > 0 || hour > 0 || minute > 0) pw.print (" ");
            if (second > 1)
                pw.print (second + " seconds");
            else
                pw.print (second + " second");
        }
    }

    public static void write (TaskList tasks, Writer out) throws IOException {

        PrintWriter pw = new PrintWriter (new BufferedWriter (out));

        SimpleDateFormat dateFormat = new SimpleDateFormat (FORMAT);

        int count = 1;

        for (Task task : tasks) {

            String title = task.getTitle ();
            pw.print ("\"" + title + "\"");

            if (task.isRepeated ()) {
                pw.print (" from [");
                pw.print (dateFormat.format (task.getStartTime ()));
                pw.print ("] to [");
                pw.print (dateFormat.format (task.getEndTime ()));
                pw.print ("] every [");

                time (task.getRepeatInterval(), pw);
            } else {
                pw.print (" at [");
                pw.print (dateFormat.format (task.getTime ()));
            }

            if (task.isActive ())
                pw.print ("]");
            else
                pw.print ("] inactive");

            if (count == tasks.size ())
                pw.print (".\n");
            else
                pw.print (";\n");

            count++;

        }
        pw.println ("");
        pw.flush ();
    }

    private static Date date (String line, String s, int index) throws ParseException {
        int startIndex = line.indexOf (s, index);
        int endIndex = line.indexOf ("]", startIndex);

        SimpleDateFormat dateFormat = new SimpleDateFormat (FORMAT);

        String sDate = line.substring (endIndex - FORMAT.length (), endIndex);

        Date date = dateFormat.parse(sDate);

        return date;
    }

    private static int interval (String moment, String line) {
        if (line.indexOf (moment) <= 0) return 0;

        int startIndex = line.indexOf (moment) - 2;

        String result = "";
        for (int i = startIndex; i >= 0; i--) {
            char ch = line.charAt(i);
            if (Character.isDigit (ch)) {
                String temp = Character.toString (ch);
                result = temp.concat (result);
            } else {
                break;
            }
        }
        System.out.println(result);
        return Integer.parseInt(result);
    }

    public static void read (TaskList tasks, Reader in) throws IOException, ParseException {
        BufferedReader br = new BufferedReader (in);

        String line = "";

        while ( (line = br.readLine ()) != null && br.ready ()) {
            int startIndex = line.indexOf ("\"");
            int endIndex = line.lastIndexOf ("\"");

            String title = line.substring (startIndex + 1, endIndex);

            boolean active = (line.indexOf ("inactive", endIndex) > 0 ? false : true);

            if (line.indexOf ("at [", endIndex) > 0) {
                Date time = date (line, "at [", endIndex + 1);

                Task task = new Task (title, new Date (time.getTime ()));
                task.setActive (active);
                tasks.add (task);

            } else {
                Date startTime = date (line, "from [", endIndex +1);
                Date endTime = date (line, "to [", endIndex +1);

                int startInterval = line.indexOf("every", endIndex);
                int endInterval = line.indexOf ("]", startInterval);
                String lineInterval = line.substring (startInterval , endInterval);

                int repeatInterval = interval ("day", lineInterval) * DAY;
                repeatInterval += interval ("hour", lineInterval) * HOUR;
                repeatInterval += interval ("minute", lineInterval) * MINUTE;
                repeatInterval += interval ("second", lineInterval);

                Task task = new Task (title, new Date (startTime.getTime ()), new Date (endTime.getTime ()), repeatInterval);

                task.setActive (active);

                tasks.add (task);
            }
        }

    }

    public static void writeText (TaskList tasks, File file) throws IOException {
        PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (file)));

        write (tasks, pw);

        pw.close ();

    }

    public static void readText (TaskList tasks, File file) throws IOException, ParseException {
        BufferedReader br = new BufferedReader (new FileReader (file));

        read (tasks, br);

        br.close ();
    }

}


package com.david.timecard.bean;

public class ScheduleBean {
    private int id; //id
    private int weekday;    //周几
    private int type;   //类型：阅读、运动、休息......
    private String title;   //标题
    private String task;    //内容
    private int hintType;   //提醒类型：0不提醒 1闹铃 2通知
    private String time;    //时间

    public ScheduleBean(int id, int weekday, int type, String title, String task, int hintType, String time) {
        this.id = id;
        this.weekday = weekday;
        this.type = type;
        this.title = title;
        this.task = task;
        this.hintType = hintType;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getHintType() {
        return hintType;
    }

    public void setHintType(int hintType) {
        this.hintType = hintType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

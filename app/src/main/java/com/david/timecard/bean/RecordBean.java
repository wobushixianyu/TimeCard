package com.david.timecard.bean;

public class RecordBean {
    private int id; //id
    private int type;   //类型：1:生日，2:恋爱纪念日，3:结婚纪念日......
    private String detail;    //内容
    private String time;    //日期

    public RecordBean(int id, int type, String detail, String time) {
        this.id = id;
        this.type = type;
        this.detail = detail;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

package com.project.model;

public class UpdateDailyReport {
    private int sno;
    private String startDate;
    private String userid;
    private String sub;
    private String topic;
    private String topicDetails;
    private String completed;

    public int getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = Integer.parseInt(sno);
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTopicDetails() {
        return topicDetails;
    }

    public void setTopicDetails(String topicDetails) {
        this.topicDetails = topicDetails;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "UpdateDailyReport{" +
                "sno=" + sno +
                ", startDate='" + startDate + '\'' +
                ", userid='" + userid + '\'' +
                ", sub='" + sub + '\'' +
                ", topic='" + topic + '\'' +
                ", topicDetails='" + topicDetails + '\'' +
                ", completed='" + completed + '\'' +
                '}';
    }
}

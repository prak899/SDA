package com.example.testing.model;

import com.example.testing.enumartion.TaskStatus;

public class DataModel {
    String data;
    TaskStatus taskStatus;

    public DataModel(String data, TaskStatus taskStatus) {
        this.data = data;
        this.taskStatus = taskStatus;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}

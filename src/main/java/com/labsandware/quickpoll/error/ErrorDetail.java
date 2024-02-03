package com.labsandware.quickpoll.error;

import com.labsandware.quickpoll.dto.error.ValidationError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorDetail {

    private String title;
    private int status;
    private String detail;
    private long timeStamp;
    private String path;
    private String developerMessage;

    Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail() {
        return detail;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public Map<String, List<ValidationError>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<ValidationError>> errors) {
        this.errors = errors;
    }
}

package com.example.android.transportprocess2.deliver;

public class Request {

    private String requestTitle;
    private String requestLine;
    private String requestPlace;
    private String requestNoOrder;
    private String requestState;
    private String time;
    private String reason;
    private int requestImageState;

    public Request() {
    }

    public Request(String requestTitle, String requestLine, String requestPlace, String requestNoOrder, String requestState, String time, int requestImageState, String reason) {
        this.requestTitle = requestTitle;
        this.requestLine = requestLine;
        this.requestPlace = requestPlace;
        this.requestNoOrder=requestNoOrder;
        this.requestState=requestState;
        this.reason = reason;
        this.time=time;
        this.requestImageState = requestImageState;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRequestTitle() {
        return requestTitle;
    }

    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getRequestLine() {
        return requestLine;
    }

    public void setRequestLine(String requestLine) {
        this.requestLine = requestLine;
    }

    public String getRequestPlace() {
        return requestPlace;
    }

    public void setRequestPlace(String requestPlace) {
        this.requestPlace = requestPlace;
    }

    public String getRequestNoOrder(){return requestNoOrder;}

    public void setRequestNoOrder(String requestOrder){this.requestNoOrder=requestOrder;}

    public String getRequestState(){return requestState;}

    public void setRequestState(String requestState){this.requestState=requestState;}

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getRequestImageState() {
        return requestImageState;
    }

    public void setRequestImageState(int requestImageState) {
        this.requestImageState = requestImageState;
    }
}
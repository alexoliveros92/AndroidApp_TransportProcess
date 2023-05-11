package com.example.android.transportprocess2.report;

public class Report {

    private String titleR;
    private String timeOR;
    private String timeDR;
    private String dateR;
    private String lineR;

    public Report() {
    }

    public Report(String titleR, String timeOR, String timeDR, String dateR, String lineR) {
        this.titleR = titleR;
        this.timeOR = timeOR;
        this.timeDR = timeDR;
        this.dateR = dateR;
        this.lineR = lineR;
    }

    public String getLineR() {
        return lineR;
    }

    public void setLineR(String lineR) {
        this.lineR = lineR;
    }

    public String getTimeOR() {
        return timeOR;
    }

    public void setTimeOR(String timeOR) {
        this.timeOR = timeOR;
    }

    public String getTimeDR() {
        return timeDR;
    }

    public void setTimeDR(String timeDR) {
        this.timeDR = timeDR;
    }

    public String getDateR() {
        return dateR;
    }

    public void setDateR(String dateR) {
        this.dateR = dateR;
    }

    public String getTitleR() {
        return titleR;
    }

    public void setTitleR(String titleR) {
        this.titleR = titleR;
    }
}

package com.example.android.transportprocess2;

public class core {

    private String itemTitle;
    private String description;
    private int keyPosition;

    public core(String itemTitle, String description, int keyPosition){
        this.itemTitle=itemTitle;
        this.description=description;
        this.keyPosition=keyPosition;
    }

    public core() { }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setItemTitle(String title) {
        this.itemTitle = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getKeyPosition() {
        return keyPosition;
    }

    public void setKeyPosition(int keyPosition) {
        this.keyPosition = keyPosition;
    }
}

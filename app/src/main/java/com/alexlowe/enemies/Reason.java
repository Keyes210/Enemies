package com.alexlowe.enemies;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Alex on 8/26/2015.
 */
public class Reason implements Serializable{
    private String description;
    private Date time;

    public Reason(String description){
        this.description = description;
        this.time = new Date();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        SimpleDateFormat s = new SimpleDateFormat("d MMM, yyyy", Locale.getDefault());
        return s.format(time);
    }

    public void setTime(Date time) {
        this.time = time;
    }
}

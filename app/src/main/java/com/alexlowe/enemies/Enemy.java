package com.alexlowe.enemies;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Alex on 7/12/2015.
 */
public class Enemy implements Serializable {
    String name;
    ArrayList<Reason> reasons = new ArrayList<>();

    public Enemy(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(ArrayList<Reason> reasons) {
        this.reasons = reasons;
    }



}

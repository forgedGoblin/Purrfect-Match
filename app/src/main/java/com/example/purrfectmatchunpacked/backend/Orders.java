package com.example.purrfectmatchunpacked.backend;

import java.io.Serializable;

public class Orders implements Serializable {
    public String email;
    public String fname;
    public String lname;
    public String time;
    public String status;
    public String type;

    public Orders() {
    }

    public Orders (String email, String fname, String lname, String time, String status, String type) {
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.time = time;
        this.status = status;
        this.type = type;
    }



}

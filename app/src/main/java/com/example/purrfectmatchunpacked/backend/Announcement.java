package com.example.purrfectmatchunpacked.backend;

import java.io.Serializable;

public class Announcement implements Serializable {
    public String header;
    public String body;
    public String organization;

    public Announcement(String header, String body, String organization) {
        this.header = header;
        this.body = body;
        this.organization = organization;
    }
}

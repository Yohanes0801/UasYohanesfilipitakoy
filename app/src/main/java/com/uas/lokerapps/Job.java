package com.uas.lokerapps;

public class Job {
    private int logo;
    private String position;
    private String company;
    private String description;

    public Job(int logo, String position, String company, String description) {
        this.logo = logo;
        this.position = position;
        this.company = company;
        this.description = description;
    }

    public int getLogo() {
        return logo;
    }

    public String getPosition() {
        return position;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }
}


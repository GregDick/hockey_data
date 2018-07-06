package com.example.mercury.hockey_data;

import java.util.Objects;

public class Team {

    private String name;
    private String websiteLink;
    private String division;

    public Team(String name, String websiteLink, String division) {
        this.name = name;
        this.websiteLink = websiteLink;
        this.division = division;
    }

    public String getName() {
        return name;
    }

    public String getWebsiteLink() {
        return websiteLink;
    }

    public String getDivision() {
        return division;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name, team.name) &&
                Objects.equals(websiteLink, team.websiteLink) &&
                Objects.equals(division, team.division);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, websiteLink, division);
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", websiteLink='" + websiteLink + '\'' +
                ", division='" + division + '\'' +
                '}';
    }
}

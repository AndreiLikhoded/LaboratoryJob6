package com.java.labjob;

public class Candidate {
    private transient Integer id;
    private String name;
    private String photo;
    private transient int votes;

    private transient double percent;




    public Candidate(Integer id, String firstName, String photo, int votes) {
        this.id = id;
        this.name = firstName;
        this.photo = photo;
        this.votes = votes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", photo='" + photo + '\'' +
                ", countVotes=" + votes +
                '}';
    }
}

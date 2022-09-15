package com.java.labjob;

import readService.ReadFileService;

import java.util.List;

public class Candidates {

    private List<Candidate> candidates;



    public Candidates() {
        this.candidates = ReadFileService.readFileString();
        for (int i = 0; i < candidates.size(); i++) {
            candidates.get(i).setId(i);
        }
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}

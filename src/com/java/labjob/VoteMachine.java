package com.java.labjob;

import com.sun.net.httpserver.HttpExchange;
import readService.ReadFileService;
import server.BasicServer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static server.Utils.parseUrlEncoded;

public class VoteMachine extends BasicServer {

    private boolean canVote = true;


    private final Candidates candidates = new Candidates();

    public VoteMachine(String host, int port) throws IOException {
        super(host, port);

        registerGet("/", this::mainHandler);

        registerPost("/vote", this::vote);

        registerGet("/doubleVote", this::doubleVoteHandler);
        registerGet("/votes", this::votesHandler);
//        registerGet("/thanks", this::);
    }

    private void votesHandler(HttpExchange exchange) {
        List<Candidate> candidates1=new ArrayList<>(candidates.getCandidates());
        candidates1.sort(Comparator.comparingDouble(Candidate::getPercent).reversed());
        candidates.setCandidates(candidates1);
        renderTemplate(exchange, "votes.html",candidates);
    }


    private void mainHandler(HttpExchange exchange) {
        renderTemplate(exchange, "/candidates.html", candidates);
    }

    private void doubleVoteHandler(HttpExchange exchange) {
        renderTemplate(exchange, "/doubleVote.html", candidates);
    }

    private void vote(HttpExchange exchange) {

        if (canVote) {

            String raw = getBody(exchange);
            Map<String, String> parsed = parseUrlEncoded(raw, "&");

            Candidate candidate = null;

            for (Candidate candidate1 : candidates.getCandidates()) {
                if (candidate1.getId() == Integer.parseInt(parsed.get("id"))) {
                    candidate = candidate1;
                    int vote = candidate1.getVotes();
                    candidate1.setVotes(vote +1);
                    break;
                }
            }

            if (candidate != null) {
                int votesPercent = countVotes(candidate.getVotes());

                candidate.setPercent(votesPercent);
//            redirect303(exchange, "/thanks");
            }
            canVote = false;
            renderTemplate(exchange, "thanks.html", candidate);


        } else {
            renderTemplate(exchange, "doubleVote.html", candidates);
        }
    }


    private int countVotes(int candidateVotes) {

        int totalVotes = 0;

        for (Candidate candidate : candidates.getCandidates()) {

            totalVotes = totalVotes + candidate.getVotes();
        }

        return (100 * candidateVotes) / totalVotes;
    }
}



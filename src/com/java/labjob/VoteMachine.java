package com.java.labjob;

import com.sun.net.httpserver.HttpExchange;
import readService.ReadFileService;
import server.BasicServer;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static server.Utils.parseUrlEncoded;

public class VoteMachine extends BasicServer {

    private boolean canVote = true;


    private final Candidates candidates = new Candidates();

    public VoteMachine(String host, int port) throws IOException {
        super(host, port);

        registerGet("/", this::mainHandler);

        registerPost("/votes", this::vote);

        registerGet("/doubleVote", this::doubleVoteHandler);
    }


    private void mainHandler(HttpExchange exchange) {
        renderTemplate(exchange, "candidates.html", candidates);
    }

    private void doubleVoteHandler(HttpExchange exchange) {
        renderTemplate(exchange, "/doubleVote.html", candidates);
    }

    private void vote(HttpExchange exchange) {

        if (canVote) {

            String raw = getBody(exchange);
            Map<String, String> parsed = parseUrlEncoded(raw, "&");

            Candidate candidate = null;

            for (Candidate candidates : candidates.getCandidates()) {
                if (candidates.getId() == Integer.parseInt(parsed.get("id"))) {
                    candidate = candidates;
                    int vote = candidates.getVotes();
                    candidates.setVotes(vote +1);
                    break;
                }
            }

            assert candidate != null;
            int votesPercent = countVotes(candidate.getVotes());

            candidate.setPercent(votesPercent);

            renderTemplate(exchange, "votes.html", candidate);

            canVote = false;


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



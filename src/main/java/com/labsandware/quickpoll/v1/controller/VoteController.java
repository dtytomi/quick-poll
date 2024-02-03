package com.labsandware.quickpoll.v1.controller;

import com.labsandware.quickpoll.domain.Vote;
import com.labsandware.quickpoll.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;

import io.swagger.v3.oas.annotations.tags.Tag;


@RestController()
@RequestMapping("/v1")
public class VoteController {
    @Autowired
    private VoteRepository voteRepository;

    @PostMapping("/polls/{pollId}/votes")
    public ResponseEntity<?> createVote (@PathVariable Long pollId, @RequestBody Vote vote) {
        vote = voteRepository.save(vote);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(vote.getId()).toUri());
        return new ResponseEntity<>(null, responseHeaders,HttpStatus.CREATED);
    }

    @GetMapping("/polls/{pollId}/votes")
    public Iterable<Vote> getAllVotes (@PathVariable Long pollId) {
        return  voteRepository.findByPoll(pollId);
    }

}

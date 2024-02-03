package com.labsandware.quickpoll.v1.controller;

import javax.inject.Inject;
import com.labsandware.quickpoll.domain.Poll;
import com.labsandware.quickpoll.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.labsandware.quickpoll.repository.PollRepository;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController("pollControllerV1")
@RequestMapping("/v1")
@Tag(name = "Polls", description = "Poll API")
public class PollController {

    @Inject
    private PollRepository pollRepository;

    @GetMapping("/polls")
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @PostMapping("/polls")
    public ResponseEntity<?> createPoll(@Valid  @RequestBody Poll poll) {
        poll = pollRepository.save(poll);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPOllUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id")
                .buildAndExpand(poll.getId())
                .toUri();
        responseHeaders.setLocation(newPOllUri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/polls/{pollId}")
    public ResponseEntity<?> getPoll(@PathVariable Long pollId) {
        return new ResponseEntity<>(verifyPoll(pollId), HttpStatus.OK);
    }

    @PutMapping("/polls/{pollId}")
    public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId) {
        verifyPoll(pollId);
        pollRepository.save(poll);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/polls/{pollId}")
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId) {
        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected Poll verifyPoll(Long pollId) throws ResourceNotFoundException {
        Optional <Poll> poll = pollRepository.findById(pollId);
        if (!poll.isPresent()) {
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }
        return poll.get();
    }
}

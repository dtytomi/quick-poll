package com.labsandware.quickpoll.v2.controller;

import com.labsandware.quickpoll.domain.Poll;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.labsandware.quickpoll.service.PollService;
import com.labsandware.quickpoll.repository.PollRepository;
import com.labsandware.quickpoll.exception.ResourceNotFoundException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.inject.Inject;
import java.net.URI;
import java.util.*;

@RestController("pollControllerV2")
@RequestMapping("/v2")
@Tag(name = "Polls", description = "Poll API")
public class PollController {

    private final PollService pollService;

    public PollController(PollService pollService) {
        this.pollService = pollService;
    }

    @Inject
    public PollRepository pollRepository;

    @GetMapping("/polls")
    public ResponseEntity<Map<String, Object>> getAllPolls(@RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "10") Integer pageSize) {

        Page<Poll> pagePolls = pollService.getPollsByPagination(pageNo, pageSize);

        List<Poll> polls = new ArrayList<Poll>();

        polls = pagePolls.getContent();

        if (polls.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();

        response.put("polls", polls);
        response.put("currentPage", pagePolls.getNumber());
        response.put("totalItems", pagePolls.getTotalElements());
        response.put("totalPages", pagePolls.getTotalPages());
        response.put("totalElements", pagePolls.getTotalElements());
        response.put("size", pagePolls.getSize());
        response.put("number", pagePolls.getNumber());
        response.put("numberOfElements", pagePolls.getNumberOfElements());
        response.put("first", pagePolls.isFirst());
        response.put("last", pagePolls.isLast());

        return new ResponseEntity<>(response, HttpStatus.OK);
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

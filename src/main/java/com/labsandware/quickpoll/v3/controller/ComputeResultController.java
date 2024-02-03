package com.labsandware.quickpoll.v3.controller;

import com.labsandware.quickpoll.domain.Vote;
import com.labsandware.quickpoll.dto.OptionCount;
import com.labsandware.quickpoll.dto.VoteResult;
import com.labsandware.quickpoll.repository.VoteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@RestController("computeResultControllerV3")
@RequestMapping("/v3")
@Tag(name = "ComputeResult", description = "ComputeResult APIs")
public class ComputeResultController {

    @Inject
    private VoteRepository voteRepository;

    @Operation(
            summary = "Retrieve Poll Compute result by pollId",
            description = "Get a Compute result object by specifying its pollId. " +
                    "The response is Compute result object with vote result")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = VoteResult.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/computeresult")
    public ResponseEntity<?> computeResult (@RequestParam Long pollId) {
        VoteResult voteResult =  new VoteResult();
        Iterable<Vote> allVotes = voteRepository.findByPoll(pollId);

        int totalVotes = 0;
        Map<Long, OptionCount> tempMap = new HashMap<Long, OptionCount>();
        for (Vote v: allVotes) {
            totalVotes++;
            // Get the OptionCount corresponding to this Option
            OptionCount optionCount = tempMap.get(v.getOption().getId());
            if (optionCount ==  null) {
                optionCount = new OptionCount();
                optionCount.setOptionId(v.getOption().getId());
                tempMap.put(v.getOption().getId(), optionCount);
            }
            optionCount.setCount(optionCount.getCount()+1);
        }

        voteResult.setTotalVotes(totalVotes);
        voteResult.setResults(tempMap.values());

        return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);
    }
}

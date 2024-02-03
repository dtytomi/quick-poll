package com.labsandware.quickpoll.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.labsandware.quickpoll.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long>{
    @Query(value="SELECT v.* " +
            " FROM Vote v " +
            " INNER JOIN Option o ON v.OPTION_ID = o.OPTION_ID " +
            " WHERE o.POLL_ID = ?1 ", nativeQuery = true)
    public Iterable<Vote> findByPoll(Long pollId);
}
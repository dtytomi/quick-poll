package com.labsandware.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;
import com.labsandware.quickpoll.domain.Poll;

public interface PollRepository extends CrudRepository<Poll, Long> {
}
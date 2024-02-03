package com.labsandware.quickpoll.repository;

import com.labsandware.quickpoll.domain.Poll;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PollPaginationRepository extends PagingAndSortingRepository<Poll, Integer> {
}

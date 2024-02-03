package com.labsandware.quickpoll.service;

import com.labsandware.quickpoll.domain.Poll;
import com.labsandware.quickpoll.repository.PollPaginationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.ArrayList;

@Service
public class PollService {
    PollPaginationRepository pollPaginationRepository;

    @Autowired
    public PollService(PollPaginationRepository pollPaginationRepository) {
        this.pollPaginationRepository = pollPaginationRepository;
    }
    public Page<Poll> getPollsByPagination (Integer pageNo, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return  pollPaginationRepository.findAll(pageable);
    }
}

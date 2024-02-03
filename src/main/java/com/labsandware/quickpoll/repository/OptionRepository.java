package com.labsandware.quickpoll.repository;

import org.springframework.data.repository.CrudRepository;
import com.labsandware.quickpoll.domain.Option;

public interface OptionRepository extends CrudRepository<Option, Long>{
}
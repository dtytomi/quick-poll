package com.labsandware.quickpoll.repository;

import com.labsandware.quickpoll.domain.User;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(String username);
}

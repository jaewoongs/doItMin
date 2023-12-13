package com.doitmin.webapp.api.repository;

import com.doitmin.webapp.api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);
    User findByEmail(String email);
//    User saveById(long id, User user);
}

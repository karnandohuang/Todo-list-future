package com.blibli.ProjectTodoList.repository;

import com.blibli.ProjectTodoList.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    public User findByUserId(Long userId);

}

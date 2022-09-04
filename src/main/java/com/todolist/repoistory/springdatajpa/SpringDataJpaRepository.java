package com.todolist.repoistory.springdatajpa;

import com.todolist.domain.Todolist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface SpringDataJpaRepository extends JpaRepository<Todolist, Long> {

    List<Todolist> findByTodoLike(String todo);

    @Query("select t from Todolist t where t.isFinished = :isFinished")
    List<Todolist> findByStatus(Boolean isFinished);

    @Query("select t from Todolist t where t.todo like :todo and t.isFinished = :isFinished")
    List<Todolist> findTodos(@Param("todo") String todo, @Param("isFinished") Boolean isFinished);


}

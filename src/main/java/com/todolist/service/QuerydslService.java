package com.todolist.service;

import com.todolist.domain.Todolist;
import com.todolist.repoistory.TodolistSearchCond;
import com.todolist.repoistory.TodolistUpdateDto;
import com.todolist.repoistory.querydsl.QuerydslRepository;
import com.todolist.repoistory.springdatajpa.SpringDataJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class QuerydslService {
    private final SpringDataJpaRepository repository;
    private final QuerydslRepository query;

    public Todolist save(Todolist todolist) {
        return repository.save(todolist);
    }

    public void update(Long id, TodolistUpdateDto updateDto) {
        Todolist todolist = repository.findById(id).orElseThrow();
        todolist.setTodo(updateDto.getTodo());
        todolist.setContent(updateDto.getContent());
        todolist.setIsFinished(updateDto.getIsFinished());
    }

    public Optional<Todolist> findById(Long id) {
        return repository.findById(id);
    }

    public List<Todolist> findAll(TodolistSearchCond cond) {
        return query.findAll(cond);
    }
}

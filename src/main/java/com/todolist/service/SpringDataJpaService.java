package com.todolist.service;

import com.todolist.domain.Todolist;
import com.todolist.repoistory.TodolistSearchCond;
import com.todolist.repoistory.TodolistUpdateDto;
import com.todolist.repoistory.springdatajpa.SpringDataJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SpringDataJpaService {

    private final SpringDataJpaRepository repository;

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
        Boolean isFinished = cond.getIsFinished();
        String todo = cond.getTodo();
        if (StringUtils.hasText(todo) && isFinished != null) {
            return repository.findTodos(cond.getTodo(), cond.getIsFinished());
        } else if (StringUtils.hasText(todo)) {
            return repository.findByTodoLike(todo);
        } else if (isFinished != null) {
            return repository.findByStatus(isFinished);
        } else {
            return repository.findAll();
        }
    }
}

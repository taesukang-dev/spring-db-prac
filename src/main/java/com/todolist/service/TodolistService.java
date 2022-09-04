package com.todolist.service;

import com.todolist.domain.Todolist;
import com.todolist.repoistory.TodolistRepository;
import com.todolist.repoistory.TodolistSearchCond;
import com.todolist.repoistory.TodolistUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodolistService {

    private final TodolistRepository repository;

    public Todolist save(Todolist todolist) {
        return repository.save(todolist);
    }

    public void update(Long id, TodolistUpdateDto updateDto) {
        repository.update(id, updateDto);
    }

    public Optional<Todolist> findById(Long id) {
        return repository.findById(id);
    }

    public List<Todolist> findAll(TodolistSearchCond cond) {
        return repository.findAll(cond);
    }
}

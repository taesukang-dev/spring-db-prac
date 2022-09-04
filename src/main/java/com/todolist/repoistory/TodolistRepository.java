package com.todolist.repoistory;

import com.todolist.domain.Todolist;

import java.util.List;
import java.util.Optional;

public interface TodolistRepository {
    Todolist save(Todolist todolist);

    void update(Long id, TodolistUpdateDto updateDto);

    Optional<Todolist> findById(Long id);

    List<Todolist> findAll(TodolistSearchCond cond);
}

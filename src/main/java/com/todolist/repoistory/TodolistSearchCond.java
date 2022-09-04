package com.todolist.repoistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodolistSearchCond {
    private String todo;
    private Boolean isFinished;
}

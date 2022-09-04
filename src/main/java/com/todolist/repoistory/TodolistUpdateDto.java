package com.todolist.repoistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodolistUpdateDto {
    private String todo;
    private String content;
    private Boolean isFinished;
}

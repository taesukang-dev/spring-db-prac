package com.todolist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todolist {
    private Long id;
    private String todo;
    private String content;
    private Boolean isFinished;
}

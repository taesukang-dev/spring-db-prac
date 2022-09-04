package com.todolist.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todolist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String todo;
    private String content;
    private Boolean isFinished;
}

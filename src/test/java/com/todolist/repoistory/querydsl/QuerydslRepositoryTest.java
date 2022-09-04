package com.todolist.repoistory.querydsl;

import com.todolist.domain.Todolist;
import com.todolist.repoistory.TodolistSearchCond;
import com.todolist.repoistory.TodolistUpdateDto;
import com.todolist.repoistory.springdatajpa.SpringDataJpaRepository;
import com.todolist.service.QuerydslService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class QuerydslRepositoryTest {

    @Autowired
    QuerydslService todolistService;

    @Test
    void save() {
        Todolist builtItem = Todolist
                .builder()
                .todo("할일")
                .content("내용")
                .isFinished(Boolean.FALSE)
                .build();
        todolistService.save(builtItem);
        Todolist foundedItem = todolistService.findById(builtItem.getId()).get();
        Assertions.assertThat(builtItem).isEqualTo(foundedItem);
    }

    @Test
    void update() {
        Todolist builtItem = Todolist
                .builder()
                .todo("할일")
                .content("내용")
                .isFinished(Boolean.FALSE)
                .build();
        TodolistUpdateDto updateParam = TodolistUpdateDto
                .builder()
                .todo("없음")
                .content("없음")
                .isFinished(Boolean.TRUE)
                .build();
        todolistService.save(builtItem);
        todolistService.update(builtItem.getId(), updateParam);
        Todolist updatedItem = todolistService.findById(builtItem.getId()).get();

        Assertions.assertThat(updatedItem.getContent()).isEqualTo(updateParam.getContent());
        Assertions.assertThat(updatedItem.getTodo()).isEqualTo(updateParam.getTodo());
        Assertions.assertThat(updatedItem.getIsFinished()).isEqualTo(updateParam.getIsFinished());
    }

    @Test
    void findAll() {
        Todolist builtItem1 = Todolist
                .builder()
                .todo("청소")
                .content("내용")
                .isFinished(Boolean.FALSE)
                .build();

        Todolist builtItem2 = Todolist
                .builder()
                .todo("밥")
                .content("내용")
                .isFinished(Boolean.TRUE)
                .build();

        Todolist builtItem3 = Todolist
                .builder()
                .todo("빨래")
                .content("내용")
                .isFinished(Boolean.TRUE)
                .build();
        todolistService.save(builtItem1);
        todolistService.save(builtItem2);
        todolistService.save(builtItem3);

        TodolistSearchCond searchCond = TodolistSearchCond.builder().todo("청소").isFinished(Boolean.FALSE).build();

        List<Todolist> all = todolistService.findAll(searchCond);
        Assertions.assertThat(all.size()).isEqualTo(1);
    }


}
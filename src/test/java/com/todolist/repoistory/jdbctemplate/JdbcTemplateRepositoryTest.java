package com.todolist.repoistory.jdbctemplate;

import com.todolist.domain.Todolist;
import com.todolist.repoistory.TodolistRepository;
import com.todolist.repoistory.TodolistSearchCond;
import com.todolist.repoistory.TodolistUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class JdbcTemplateRepositoryTest {

    @Autowired
    TodolistRepository todolistRepository;

    @TestConfiguration
    @RequiredArgsConstructor
    static class JdbcTemplateTestConfig {
        private final DataSource dataSource;

        @Bean
        public TodolistRepository todolistRepository() {
            return new JdbcTemplateRepository(dataSource);
        }
    }

    @Test
    void save() {
        Todolist builtItem = Todolist
                .builder()
                .todo("할일")
                .content("내용")
                .isFinished(Boolean.FALSE)
                .build();
        todolistRepository.save(builtItem);
        Todolist foundedItem = todolistRepository.findById(builtItem.getId()).get();
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
        todolistRepository.save(builtItem);
        todolistRepository.update(builtItem.getId(), updateParam);
        Todolist updatedItem = todolistRepository.findById(builtItem.getId()).get();

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
        todolistRepository.save(builtItem1);
        todolistRepository.save(builtItem2);
        todolistRepository.save(builtItem3);

        TodolistSearchCond searchCond = TodolistSearchCond.builder().todo("청소").isFinished(Boolean.FALSE).build();

        List<Todolist> all = todolistRepository.findAll(searchCond);
        Assertions.assertThat(all.size()).isEqualTo(1);
    }

}
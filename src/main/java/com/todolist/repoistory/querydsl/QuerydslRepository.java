package com.todolist.repoistory.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.todolist.domain.QTodolist;
import com.todolist.domain.Todolist;
import com.todolist.repoistory.TodolistRepository;
import com.todolist.repoistory.TodolistSearchCond;
import com.todolist.repoistory.TodolistUpdateDto;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.todolist.domain.QTodolist.todolist;

@Repository
@Transactional
public class QuerydslRepository {

    private final JPAQueryFactory query;

    public QuerydslRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Todolist> findAll(TodolistSearchCond cond) {
        return query.select(todolist)
                .from(todolist)
                .where(
                        likeTodo(cond.getTodo()),
                        isFinished(cond.getIsFinished())
                )
                .fetch();

    }

    private BooleanExpression likeTodo(String todo) {
        if (StringUtils.hasText(todo)) {
            return todolist.todo.like("%" + todo + "%");
        }
        return null;
    }

    private BooleanExpression isFinished(Boolean isFinished) {
        if (isFinished != null) {
            return todolist.isFinished.eq(isFinished);
        }
        return null;
    }

}

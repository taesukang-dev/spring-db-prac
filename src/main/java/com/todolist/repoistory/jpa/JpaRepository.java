package com.todolist.repoistory.jpa;

import com.todolist.domain.Todolist;
import com.todolist.repoistory.TodolistRepository;
import com.todolist.repoistory.TodolistSearchCond;
import com.todolist.repoistory.TodolistUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@RequiredArgsConstructor
public class JpaRepository implements TodolistRepository {

    private final EntityManager em;

    @Override
    public Todolist save(Todolist todolist) {
        em.persist(todolist);
        return todolist;
    }

    @Override
    public void update(Long id, TodolistUpdateDto updateDto) {
        Todolist founded = em.find(Todolist.class, id);
        founded.setTodo(updateDto.getTodo());
        founded.setContent(updateDto.getContent());
        founded.setIsFinished(updateDto.getIsFinished());
    }

    @Override
    public Optional<Todolist> findById(Long id) {
        return Optional.ofNullable(em.find(Todolist.class, id));
    }

    @Override
    public List<Todolist> findAll(TodolistSearchCond cond) {
        String jpql = "select t from Todolist t";
        String todo = cond.getTodo();
        Boolean isFinished = cond.getIsFinished();

        if (StringUtils.hasText(todo) || isFinished != null) {
            jpql += " where";
        }
        boolean andFlag = false;
        ArrayList<Object> param = new ArrayList<>();
        if (StringUtils.hasText(todo)) {
            jpql += " t.todo like concat('%',:todo,'%')";
            param.add(todo);
            andFlag = true;
        }

        if (isFinished != null) {
            if (andFlag) {
                jpql += " and";
            }
            jpql += " t.isFinished = :isFinished";
            param.add(isFinished);
        }
        TypedQuery<Todolist> query = em.createQuery(jpql, Todolist.class);
        if (StringUtils.hasText(todo)) {
            query.setParameter("todo", todo);
        }
        if (isFinished != null) {
            query.setParameter("isFinished", isFinished);
        }
        return query.getResultList();
    }
}

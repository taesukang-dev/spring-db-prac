package com.todolist.repoistory.jdbctemplate;

import com.todolist.domain.Todolist;
import com.todolist.repoistory.TodolistRepository;
import com.todolist.repoistory.TodolistSearchCond;
import com.todolist.repoistory.TodolistUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class JdbcTemplateRepository implements TodolistRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Todolist save(Todolist todolist) {
        String sql = "insert into todolist(todo, content, is_finished) values(:todo, :content, :isFinished)";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(todolist);
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);
        long key = keyHolder.getKey().longValue();
        todolist.setId(key);
        return todolist;
    }

    @Override
    public void update(Long id, TodolistUpdateDto updateDto) {
        String sql = "update todolist set todo=:todo, content=:content, is_finished=:isFinished where id=:id";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("todo", updateDto.getTodo())
                .addValue("content", updateDto.getContent())
                .addValue("isFinished", updateDto.getIsFinished())
                .addValue("id", id);
        template.update(sql, param);
    }

    @Override
    public Optional<Todolist> findById(Long id) {
        String sql = "select * from todolist where id = :id";
        try {
            Map<String, Object> param = Map.of("id", id);
            Todolist todolist = template.queryForObject(sql, param, todolistRowMapper());
            return Optional.of(todolist);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Todolist> findAll(TodolistSearchCond cond) {
        String sql = "select * from todolist";

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(cond);

        if (StringUtils.hasText(cond.getTodo()) || cond.getIsFinished() != null) {
            sql += " where";
        }
        boolean andFlag = false;
        if (StringUtils.hasText(cond.getTodo())) {
            sql += " todo like concat('%',:todo,'%')";
            andFlag = true;
        }
        if (cond.getIsFinished() != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " is_finished = :isFinished";
        }
        return template.query(sql, param, todolistRowMapper());
    }

    private RowMapper<Todolist> todolistRowMapper() {
        return BeanPropertyRowMapper.newInstance(Todolist.class);
    }
}

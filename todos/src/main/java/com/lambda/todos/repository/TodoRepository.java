package com.lambda.todos.repository;

import com.lambda.todos.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long>
{
}

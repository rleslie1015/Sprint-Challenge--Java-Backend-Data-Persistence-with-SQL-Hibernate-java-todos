package com.lambda.todos.services;

import com.lambda.todos.model.Todo;
import com.lambda.todos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "todoservice")
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todos;

	@Override
	public List<Todo> findAllById(long id)
	{
		List<Todo> list = new ArrayList<>();
		todos.getAllById(id).iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public Todo findTodoById(long id) {
		return todos.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public Todo save(Todo todo) {
			return todos.save(todo);
	}

	@Override
	public void delete(long id) {
		if (todos.findById(id).isPresent()){
			todos.deleteById(id);
		} else {
			throw new EntityNotFoundException();
		}

	}

	@Override
	public Todo update(Todo todo, long id) {
		Todo currentTodo = todos.findById(id).orElseThrow(EntityNotFoundException::new);

		if (todo.getDate() != null){
			currentTodo.setDate(todo.getDate());
		}

		if (todo.getDate() != null){
			currentTodo.setDate(todo.getDate());
		}

		if (todo.isCompleted()){
			currentTodo.setCompleted(true);
		} else {
			currentTodo.setCompleted(false);
		}

		todos.save(currentTodo);
		return currentTodo;
	}
}



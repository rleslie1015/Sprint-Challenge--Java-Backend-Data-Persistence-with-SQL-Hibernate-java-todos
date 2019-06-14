package com.lambda.todos.services;

import com.lambda.todos.model.Todo;
import com.lambda.todos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "todoService")
public class TodoServiceImpl implements TodoService
{
	@Autowired
	private TodoRepository todosrepos;

	@Override
	public List<Todo> findAllById(long id)
	{
		List<Todo> list = new ArrayList<>();
		todosrepos.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	@Override
	public Todo findTodoById(long id)
	{
		return todosrepos.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
	}

	@Override
	public Todo save(Todo todo)
	{
		return todosrepos.save(todo);
	}

	@Override
	public void delete(long id)
	{
		if (todosrepos.findById(id).isPresent())
		{
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (todosrepos.findById(id).get().getUser().getUsername().equalsIgnoreCase(authentication.getName()))
			{
				todosrepos.deleteById(id);
			}
			else
			{
				throw new EntityNotFoundException(Long.toString(id) + " " + authentication.getName());
			}
		}
		else
		{
			throw new EntityNotFoundException(Long.toString(id));
		}
	}


}

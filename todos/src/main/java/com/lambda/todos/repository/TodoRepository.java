package com.lambda.todos.repository;

import com.lambda.todos.model.Todo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long> {

	@Query(value = "SELECT * FROM todos WHERE userid = :userid", nativeQuery = true)
	List<Todo> getAllById(long userid);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM todos WHERE userid = :userid", nativeQuery = true)
	void deleteAllByUserId(long userid);
}


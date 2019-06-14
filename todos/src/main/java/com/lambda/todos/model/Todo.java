package com.lambda.todos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "todos")
public class Todo
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long todoid;

	@Column(nullable = false)
	private String description;

	private String date;
	private boolean completed;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", nullable = false)
	@JsonIgnoreProperties({"todos", "hibernateLazyInitializer"})
	private User user;

	public Todo()
	{
	}

	public Todo(String description, String date, User user)
	{
		this.description = description;
		this.date = date;
		this.user = user;
	}

	public long getTodoid()
	{
		return todoid;
	}

	public void setTodoid(long todoid)
	{
		this.todoid = todoid;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public boolean isCompleted()
	{
		return completed;
	}

	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
}

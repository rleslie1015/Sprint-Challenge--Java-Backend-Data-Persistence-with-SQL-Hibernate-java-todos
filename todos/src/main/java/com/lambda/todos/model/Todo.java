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

	private Date datestarted;
	private Date datetime;
	private boolean completed;

	@ManyToOne
	@Column(nullable = false)
	@JoinColumn(name = "userid")
	@JsonIgnoreProperties("todos")
	private User user;


}

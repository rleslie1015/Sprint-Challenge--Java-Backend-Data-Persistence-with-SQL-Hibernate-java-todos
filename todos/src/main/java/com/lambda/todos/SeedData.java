
package com.lambda.todos;


import com.lambda.todos.model.Role;
import com.lambda.todos.model.User;
import com.lambda.todos.model.UserRoles;
import com.lambda.todos.repository.RoleRepository;
import com.lambda.todos.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Component
public class SeedData implements CommandLineRunner
{
	RoleRepository rolerepos;
	UserRepository userrepos;

	public SeedData(RoleRepository rolerepos, UserRepository userrepos)
	{
		this.rolerepos = rolerepos;
		this.userrepos = userrepos;
	}

	@Override
	public void run(String[] args) throws Exception
	{
		Role r1 = new Role("admin");
		Role r2 = new Role("user");

		ArrayList<UserRoles> admins = new ArrayList<>();
		admins.add(new UserRoles(new User(), r1));
		admins.add(new UserRoles(new User(), r2));

		ArrayList<UserRoles> users = new ArrayList<>();
		users.add(new UserRoles(new User(), r2));

		rolerepos.save(r1);
		rolerepos.save(r2);
		User u1 = new User("barnbarn", "ILuvM4th!", users);

		User u2 = new User("admin", "password", admins);

		userrepos.save(u1);
		userrepos.save(u2);
	}
}
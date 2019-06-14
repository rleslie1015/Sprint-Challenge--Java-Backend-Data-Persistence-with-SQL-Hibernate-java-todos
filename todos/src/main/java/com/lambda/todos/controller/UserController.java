package com.lambda.todos.controller;

import com.lambda.todos.model.Todo;
import com.lambda.todos.model.User;
import com.lambda.todos.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity<?> listAllUsers(HttpServletRequest request)
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers, HttpStatus.OK);
    }

    @GetMapping(value = "/mine", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> getCurrentUserName(Authentication authentication)
    {
        User u = userService.findUserByName(authentication.getName());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/user/{userId}", produces = {"application/json"})
    public ResponseEntity<?> getUser(HttpServletRequest request, @PathVariable Long userId)
    {
        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u, HttpStatus.OK);
    }


    @GetMapping(value = "/getusername", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> getCurrentUserName(HttpServletRequest request, Authentication authentication)
    {
        return new ResponseEntity<>(authentication.getPrincipal(), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/user", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewUser(HttpServletRequest request, @Valid @RequestBody User newuser) throws URISyntaxException
    {
        newuser =  userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userid}")
                .buildAndExpand(newuser.getUserid())
                .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(HttpServletRequest request, @RequestBody User updateUser, @PathVariable long id)
    {
        userService.update(updateUser, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    GET /users/mine- return the user based off user
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUserById(HttpServletRequest request, @PathVariable long id)
    {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    POST /users adds a user can only be done by admin
    @PostMapping(value = "/user", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewUser(@Valid
                                        @RequestBody User newUser)
    {
        newUser = UserService.save(newUser);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newRestaurantURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{restaurantid}").buildAndExpand(newUser.getUserid()).toUri();
        responseHeaders.setLocation(newRestaurantURI);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

//    POST adds to a specific user's todos anyone can do it
    @PostMapping(value = "todo/{userid}", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addTodo(
            @Valid
            @RequestBody Todo newTodo,
            @PathVariable long userid)
    {
        User currentUser = userService.findUserById(userid);
        currentUser.getTodos().add(new Todo(newTodo.getDescription(), new Date().toString(), currentUser));
        UserService.update(currentUser, userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
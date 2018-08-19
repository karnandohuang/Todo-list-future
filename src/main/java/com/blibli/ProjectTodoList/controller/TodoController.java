package com.blibli.ProjectTodoList.controller;

import com.blibli.ProjectTodoList.exception.ResourceNotFoundException;
import com.blibli.ProjectTodoList.model.Todo;
import com.blibli.ProjectTodoList.repository.TodoRepository;
import com.blibli.ProjectTodoList.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/user/{userId}/todos")
    public List<Todo> getTodoByUserId(@PathVariable Long userId){
        return todoRepository.findByUserId(userId);
    }

    @PostMapping("/api/user/{userId}/todos")
    public Todo addTodo(@PathVariable Long userId, @Valid @RequestBody Todo todo){
        return userRepository.findById(userId)
                .map(user -> {
                    todo.setUser(user);
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new ResourceNotFoundException("user not found with id " + userId));
    }

    @PutMapping("/api/user/{userId}/todos/{todoId}")
    public Todo updateTodo(@PathVariable Long userId, @PathVariable Long todoId, @Valid @RequestBody Todo todoRequest){
        if(!todoRepository.existsById(todoId)){
            throw new ResourceNotFoundException("Todo not found with id " + todoId);
        }

        return todoRepository.findById(todoId)
                .map(todo -> {
                    todo.setDescription(todoRequest.getDescription());
                    return todoRepository.save(todo);
                }).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + todoId));
    }

    @DeleteMapping("/api/user/{userId}/todos/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long userId, @PathVariable Long todoId){
        return todoRepository.findById(todoId)
                .map(todo -> {
                    todoRepository.delete(todo);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + todoId));

    }






}

package com.example.TouristSystem.Controllers;

import org.springframework.http.ResponseEntity;
import com.example.TouristSystem.Models.User;
import com.example.TouristSystem.Services.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {
    
    private final UserService service;

    public UserController(UserService service){
        this.service = service;

    }

    @GetMapping
    public List<User> getAllUsers(){
        return service.getAllUsers();
    }

    @GetMapping("/{id}") 
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = service.getUserById(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public User createUser(@RequestBody User user){
       return service.createUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
      service.deleteUser(id);
      return ResponseEntity.ok().build();
    }

}

package com.example.demo;

import com.example.demo.users.model.Users;
import com.example.demo.users.repo.UsersRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

  @Autowired
  UsersRepository usersRepository;

  @GetMapping(value = "/users")
  public List<Users> getAllusers(){
    return usersRepository.findAll();

  }

  @PostMapping(value = "/users/register")
  public Users saveUser(@RequestBody Users  user){
    return usersRepository.save(user);

  }

}

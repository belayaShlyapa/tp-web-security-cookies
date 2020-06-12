package etu.lic.tpsecuritywebcookies.controller;

import etu.lic.tpsecuritywebcookies.entity.user.User;
import etu.lic.tpsecuritywebcookies.repository.UserRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(path = "temp", method = RequestMethod.GET)
    List<User> temp() {
        return repository.findAll();
    }
}
package com.ciandt;

import com.ciandt.model.User;
import com.ciandt.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


@RestController
public class Main {

    Logger logger = Logger.getLogger(Main.class.getName());

    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHelloWorld() {
        return "Hello world!!";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listUsers() {
        Gson gson = new Gson();
        List<User> usersList = userService.listUsers();
        return new ResponseEntity<String>(gson.toJson(usersList), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        Gson gson = new Gson();
        User user = userService.getUser(id);
        if (user != null) {
            return new ResponseEntity<String>(gson.toJson(user), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postUser(@RequestBody String data) {
        Gson gson = new Gson();
        User user = gson.fromJson(data, User.class);
        userService.createUser(user);
        return new ResponseEntity<String>(gson.toJson(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        User user = userService.getUser(id);
        if (user != null) {
            userService.deleteUser(user);
            return new ResponseEntity<String>("OK", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }
    }

}

package com.intuit.cg.backendtechassessment.controller;

import com.intuit.cg.backendtechassessment.Entity.User;
import com.intuit.cg.backendtechassessment.Service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(RequestMappings.MARKETPLACE)
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /*
     *  Get all users details [Users can be both buyer and Seller]
     *  URI = localhost:8080/marketplace/sellers,localhost:8080/marketplace/buyers
     *  Method = GET
     *  Http Status: 200 OK (Success), 204 No Content(No Users available)
     */
    @RequestMapping(value= {RequestMappings.BUYERS, RequestMappings.SELLERS}, method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            logger.info("No users are created yet");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>> (users, HttpStatus.OK);
    }

    /*
     *  Get user details by userName [Users can be both buyer and Seller]
     *  URI = [localhost:8080/marketplace/sellers/1,localhost:8080/marketplace/buyers/1]
     *  Method = GET
     *  Http Status: 200 OK (Success), 204 No Content(User not available)
     */
    @RequestMapping(value ={RequestMappings.BUYERS+"/{userName}"
            , RequestMappings.SELLERS+"/{userName}"}, method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("userName") String userName) {
        logger.info("Fetching User details for UserName: {} " + userName );
        User user = userService.findUserById(userName);
        if (user == null) {
            logger.error("User with Id {} not found" +userName);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /*
     *  Create users [Users can be both buyer and Seller]
     *  URI = [localhost:8080/marketplace/sellers,localhost:8080/marketplace/buyers]
     *  Method = POST
     *  Http Status: 200 OK (Success), 204 No Content(No Users available), 409 Conflict (Duplicate User)
     */
    @RequestMapping(value={RequestMappings.BUYERS, RequestMappings.SELLERS}, method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        logger.info("Creating User: {}" + user );

        if (userService.findUserById(user.getUserName()) != null) {
            logger.error("Error creating User. UserName {} already Exists", user.getUserName());
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        userService.createUser(user);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    /*
     *  Update users [Users can be both buyer and Seller]
     *  URI = [localhost:8080/marketplace/sellers/username,localhost:8080/marketplace/buyers/username]
     *  Method = PUT
     *  Http Status: 200 OK (Success), 204 No Content(User not found)
     */
    @RequestMapping(value={RequestMappings.BUYERS+"/{userName}"
            , RequestMappings.SELLERS+"/{userName}"},method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("userName") String userName
            , @RequestBody User user) {
        logger.info("Updating User with Id: "+ userName);

        User updUser = userService.findUserById(userName);
        if (updUser == null) {
            logger.error("Cannot update the User. User with Id {} not found" + userName);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        updUser.setFirstName(user.getFirstName());
        updUser.setLastName(user.getLastName());
        updUser.setEmail(user.getEmail());
        updUser.setPhone(user.getPhone());

        userService.updateUser(updUser);
        return new ResponseEntity<User>(updUser,HttpStatus.OK);
    }

    /*
     *  Delete user based on username [Users can be both buyer and Seller]
     *  URI = [localhost:8080/marketplace/sellers/username,localhost:8080/marketplace/buyers/username]
     *  Method = DELETE
     *  Http Status: 200 OK (Success), 204 No Content(User not found)
     */
    @RequestMapping(value ={RequestMappings.BUYERS+"/{userName}"
            , RequestMappings.SELLERS+"/{userName}"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("userName") String userName) {
        logger.info("Retrieving and Deleting User with userName " + userName);

        User user = userService.findUserById(userName);
        if (user == null) {
            logger.error("User id not present. Cannot delete User with id {}" + userName);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        userService.deleteByUserId(userName);
        return new ResponseEntity<User>(HttpStatus.OK);
    }
}
package com.intuit.cg.backendtechassessment.Service;

import com.intuit.cg.backendtechassessment.Entity.User;
import java.util.List;

public interface UserService {
    User findUserById(String userName);
    void createUser(User user);
    void updateUser(User user);
    void deleteByUserId(String userName);
    List<User> getAllUsers();
}

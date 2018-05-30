package com.intuit.cg.backendtechassessment.Service;

import com.intuit.cg.backendtechassessment.Entity.User;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    private List<User> users = new ArrayList<User>();

    /* Get all the Users created */
    public List<User> getAllUsers() {
        return users;
    }

    /* Find the User by userName */
    public User findUserById(String userName) {
        for (User user: users) {
            if(user.getUserName().equalsIgnoreCase(userName)) {
                return user;
            }
        }
        return null;
    }

    /* Create User */
    public void createUser(User user) {
        users.add(user);
    }

    /* Delete the User by username */
    public void deleteByUserId(String userName) {
        for (Iterator<User> iterator = users.iterator(); ((Iterator) iterator).hasNext(); ) {
            User user = (User) ((Iterator) iterator).next();
            if (user.getUserName().equalsIgnoreCase(userName)) {
                iterator.remove();
            }
        }
    }

    /* Update User */
    public void updateUser(User user) {
        int index = users.indexOf(user);
        users.set(index, user);
    }
}

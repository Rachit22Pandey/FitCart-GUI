package com.fitcart.app.service;

import com.fitcart.app.dao.UserDAO;
import com.fitcart.app.model.User;
import com.fitcart.app.exception.FitCartException;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = new UserDAO();
    }

    @Override
    public boolean registerUser(User user) {
        return userDAO.addUser(user);
    }

    @Override
    public User loginUser(String email, String password) throws FitCartException {

        User user = userDAO.findByEmail(email);

        if (user == null) {
            throw new FitCartException("User not found.");
        }

        if (!user.getPassword().equals(password)) {
            throw new FitCartException("Invalid password.");
        }

        return user;
    }
}

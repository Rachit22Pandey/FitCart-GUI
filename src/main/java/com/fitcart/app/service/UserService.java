package com.fitcart.app.service;

import com.fitcart.app.model.User;
import com.fitcart.app.exception.FitCartException;

public interface UserService {

    boolean registerUser(User user);

    User loginUser(String email, String password) throws FitCartException;
}
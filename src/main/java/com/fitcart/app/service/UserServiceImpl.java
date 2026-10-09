
package com.fitcart.app.service;

import com.fitcart.app.dao.UserDAO;
import com.fitcart.app.model.User;
import com.fitcart.app.exception.FitCartException;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = new UserDAO();
    }

    @Override
    public boolean registerUser(User user) {

        if (user == null
                || user.getPassword() == null
                || user.getPassword().isBlank()) {
            return false;
        }

        // Hash new passwords before saving them.
        String hashedPassword = BCrypt.hashpw(
            user.getPassword(),
            BCrypt.gensalt(12)
        );

        user.setPassword(hashedPassword);

        return userDAO.addUser(user);
    }

    @Override
    public User loginUser(String email, String password)
            throws FitCartException {

        User user = userDAO.findByEmail(email);

        if (user == null) {
            throw new FitCartException("Invalid email or password.");
        }

        String storedPassword = user.getPassword();
        boolean passwordMatches = false;

        if (storedPassword != null && storedPassword.startsWith("$2a$")) {
            // Verify an existing BCrypt hash.
            try {
                passwordMatches = BCrypt.checkpw(
                    password,
                    storedPassword
                );
            } catch (IllegalArgumentException ex) {
                passwordMatches = false;
            }
        } else {
            // Temporary backward compatibility for old plaintext accounts.
            passwordMatches = storedPassword != null
                    && storedPassword.equals(password);
        }

        if (!passwordMatches) {
            throw new FitCartException("Invalid email or password.");
        }

        return user;
    }
}

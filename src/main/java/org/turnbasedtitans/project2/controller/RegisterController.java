package org.turnbasedtitans.project2.controller;

import org.turnbasedtitans.project2.database.DatabaseManager;
import org.turnbasedtitans.project2.database.UserDAO;

public class RegisterController {
    private final UserDAO userDAO;

    public RegisterController(DatabaseManager db) {
        this.userDAO = new UserDAO(db);
    }

    public String register(String username, String password) {
        String user = username.trim();
        String pass = password.trim();

        if (user.isEmpty() || pass.isEmpty()) {
            return "Enter username and password.";
        }

        if (userDAO.createUser(user, pass)) {
            return "SUCCESS";
        }

        return "Username already exists.";
    }
}
package org.turnbasedtitans.project2.controller;

import org.turnbasedtitans.project2.database.DatabaseManager;
import org.turnbasedtitans.project2.database.UserDAO;

public class LogInController {
    private final UserDAO userDAO;

    public LogInController(DatabaseManager db) {
        this.userDAO = new UserDAO(db);
    }

    public String login(String username, String password) {
        String user = username.trim();
        String pass = password.trim();

        if (user.isEmpty() || pass.isEmpty()) {
            return "Enter username and password.";
        }

        if (userDAO.loginUser(user, pass)) {
            return "SUCCESS";
        }

        return "Invalid username or password.";
    }
}
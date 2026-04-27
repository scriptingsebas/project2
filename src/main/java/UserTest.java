import database.DatabaseManager;
import database.UserDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testLoginSuccess() {

        DatabaseManager db = new DatabaseManager();
        UserDAO dao = new UserDAO(db);

        dao.createUser("testUser", "123");
        boolean result = dao.loginUser("testUser", "123");
        assertTrue(result);
        db.close();
    }
}
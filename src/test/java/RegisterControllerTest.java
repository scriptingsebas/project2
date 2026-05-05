import org.junit.jupiter.api.Test;
import org.turnbasedtitans.project2.controller.RegisterController;
import org.turnbasedtitans.project2.database.DatabaseManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterControllerTest {

    @Test
    void registerTest() {
        DatabaseManager db = new DatabaseManager();
        RegisterController controller = new RegisterController(db);
        String result = controller.register("   ", "password123");
        assertEquals("Enter username and password.", result);
    }
}
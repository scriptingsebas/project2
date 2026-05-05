import org.junit.jupiter.api.Test;
import org.turnbasedtitans.project2.controller.LogInController;
import org.turnbasedtitans.project2.database.DatabaseManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogInControllerTest {

    @Test
    void loginTest() {
        DatabaseManager db = new DatabaseManager();
        LogInController controller = new LogInController(db);

        String result = controller.login("player1", "   ");

        assertEquals("Enter username and password.", result);
    }
}
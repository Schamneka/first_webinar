import org.junit.jupiter.api.Test;

import java.lang.annotation.Target;

import static org.example.Methods.transformName;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransformTests {

    @Test
    public void firstTest() {
        String expectedName = "Кирилл transformed";

        assertEquals(expectedName, transformName("Кирилл"));
    }
}

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RepeatedTestExample {

    @RepeatedTest(5)
    void testRepeatedExecution(RepetitionInfo repetitionInfo) {
        int randomValue = (int) (Math.random() * 10);
        System.out.println("Test executed with value: " + repetitionInfo.getCurrentRepetition());
        assertTrue(randomValue < 10); // Simple assertion
    }
}

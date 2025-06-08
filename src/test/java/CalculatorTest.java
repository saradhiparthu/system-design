import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculatorTest {
    //@BeforeEach
    void beforeEach() {
        System.out.println("BeforeEach method called");
    }

    //@AfterEach
    void afterEach() {
        System.out.println("AfterEach method called");
    }

    //@BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll method called");
    }

    //@AfterAll
    static void afterAll() {
        System.out.println("AfterAll method called");
    }

    @Test
    @DisplayName("Provides a custom display name for the test.")
    @Disabled("temporarily Disabled")
    @Tag("Calculator")
    void test1() {
        System.out.println("Test method called");
    }

    @RepeatedTest(value = 2)
    @DisplayName("Repeats a test multiple times.")
    void test2() {
        System.out.println("Repeated Testing method called");
    }

}

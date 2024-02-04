package reflection;

import reflection.annotation.*;

public class TestClass {


    @BeforeSuite
    public static void beforeSuite() {
        System.out.println("Before Suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test");
    }

    @Test(priority = 3)
    @CsvSource("10, Java, 20, true")
    public void testMethod(int a, String b, int c, boolean d) {
        System.out.println("Test Method 1: " + a + " " + b + " " + c + " " + d);
    }

    @Test(priority = 7)
    @CsvSource("100, Python, 200, false")
    public void testMethod2(int a, String b, int c, boolean d) {
        System.out.println("Test Method 2: " + a + " " + b + " " + c + " " + d);
    }

    @AfterTest
    @BeforeTest
    public void afterTest() {
        System.out.println("After Test");
    }

    @AfterSuite
    public static void afterSuite() {
        System.out.println("After Suite");
    }


}

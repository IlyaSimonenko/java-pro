package reflection;

import reflection.annotation.*;

public class TestClass {




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

    @Test(priority = 1)
    @CsvSource("100, Python, 200, false")
    public void testMethod21(int a, String b, int c, boolean d) {
        System.out.println("Test Method 2: " + a + " " + b + " " + c + " " + d);
    }

    @Test(priority = 10)
    public void testMethod211() {
        System.out.println("Test Method 111111 " );
    }



}

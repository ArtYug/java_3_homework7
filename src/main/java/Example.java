import annotations.AfterSuite;
import annotations.BeforeSuite;
import annotations.Test;

public class Example {
    @BeforeSuite
    public void prepare() {
        System.out.println("Annotated method called @BeforeSuite");
    }

    @Test()
    public void test1() {
        System.out.println("Annotated method called @Test with priority 1");
    }

    @Test(val = 2)
    public void test2() {
        System.out.println("Annotated method called @Test with priority 2");
    }

    @Test(val = 3)
    public void test3() {
        System.out.println("Annotated method called @Test with priority 3");
    }

    @AfterSuite
    public void end() {
        System.out.println("Annotated method called @AfterSuite");
    }
}

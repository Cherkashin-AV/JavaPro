package ru.vtb.javapro.test_engine.tests;


import java.util.logging.Logger;
import ru.vtb.javapro.test_engine.annotation.AfterSuite;
import ru.vtb.javapro.test_engine.annotation.AfterTest;
import ru.vtb.javapro.test_engine.annotation.BeforeSuite;
import ru.vtb.javapro.test_engine.annotation.BeforeTest;
import ru.vtb.javapro.test_engine.annotation.CsvSource;
import ru.vtb.javapro.test_engine.annotation.Test;
import ru.vtb.javapro.test_engine.annotation.Test.Priority;


public class TestClass {

    static Logger logger;

    static {
        logger = Logger.getLogger(TestClass.class.getName());
    }

    @BeforeSuite
    public static void beforeSuite() {
        logger.info("beforeSuite");
    }

    @BeforeTest
    public void beforeTest() {
        logger.info("beforeTest");
    }

    @CsvSource("1, ddd")
    @Test
    public void test(Integer d, String s) throws NoSuchMethodException {
        Priority priority = this.getClass()
                                .getDeclaredMethod("test", Integer.class, String.class)
                                .getAnnotation(Test.class)
                                .priority();
        logger.info("test(%d, %s) priority=%d".formatted(d, s, priority.getI()));
    }

    @Test(priority = Priority.P1)
    @CsvSource("2, sss")
    public void test1(Integer d, String s) throws NoSuchMethodException {
        Priority priority = this.getClass()
                                .getDeclaredMethod("test1", Integer.class, String.class)
                                .getAnnotation(Test.class)
                                .priority();
        logger.info("test(%d, %s) priority=%d".formatted(d, s, priority.getI()));
    }

    @AfterTest
    public void afterTest() {
        logger.info("afterTest");
    }

    @AfterSuite
    public static void afterSuite() {
        logger.info("afterSuite");
    }
}

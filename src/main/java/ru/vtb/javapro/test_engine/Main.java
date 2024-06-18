package ru.vtb.javapro.test_engine;


import ru.vtb.javapro.test_engine.runner.TestRunner;
import ru.vtb.javapro.test_engine.tests.TestClass;


public class Main {

    public static void main(String[] args) throws Exception {
        TestRunner.runTests(TestClass.class);
    }
}

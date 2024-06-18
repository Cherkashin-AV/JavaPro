package ru.vtb.javapro.test_engine.runner;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.vtb.javapro.test_engine.annotation.AfterSuite;
import ru.vtb.javapro.test_engine.annotation.AfterTest;
import ru.vtb.javapro.test_engine.annotation.BeforeSuite;
import ru.vtb.javapro.test_engine.annotation.BeforeTest;
import ru.vtb.javapro.test_engine.annotation.CsvSource;
import ru.vtb.javapro.test_engine.annotation.Test;


public class TestRunner {

    private static final Map<Class<?>, List<Method>> methodMap = new HashMap<>();

    private TestRunner() {
        throw new TestRunnerException("Используются статические методы. Создавать экземпляр класса не требуется.");
    }

    public static void runTests(Class<?> c)
        throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        Object testObject;
        fillMethodMap(c);

        if (methodMap.containsKey(BeforeSuite.class)) {
            methodInvoke(null, methodMap.get(BeforeSuite.class)
                                   .get(0));
        }

        try {
            testObject = c.getConstructor()
                             .newInstance();
        } catch (Exception e) {
            throw new TestRunnerException("Не удалось создать экземпляр тестового класса. " + e.getMessage());
        }

        if (methodMap.containsKey(Test.class)) {
            for (Method method : methodMap.get(Test.class)
                                     .stream()
                                     .sorted(Comparator.comparingInt(m -> m.getAnnotation(Test.class)
                                                                              .priority()
                                                                              .getI()))
                                     .toList()) {
                if (methodMap.containsKey(BeforeTest.class)) {
                    methodInvoke(testObject, methodMap.get(BeforeTest.class)
                                                 .get(0));
                }
                methodInvoke(testObject, method);
                if (methodMap.containsKey(AfterTest.class)) {
                    methodInvoke(testObject, methodMap.get(AfterTest.class)
                                                 .get(0));
                }
            }
        }
        if (methodMap.containsKey(AfterSuite.class)) {
            methodInvoke(null, methodMap.get(AfterSuite.class)
                                   .get(0));
        }
    }


    private static String[] getParameters(Method method) {
        String[] result = new String[0];
        if (method.isAnnotationPresent(CsvSource.class)) {
            result = method.getAnnotation(CsvSource.class)
                         .value()
                         .split(",");
        }
        return result;
    }

    private static void methodInvoke(Object obj, Method method)
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String[] parameterValues = getParameters(method);
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] params = null;
        if (parameterTypes.length > 0) {
            params = new Object[parameterTypes.length];
            for (int i = 0; i < Math.min(parameterTypes.length, parameterValues.length); i++) {
                params[i] = parameterTypes[i].getConstructor(String.class)
                                .newInstance(parameterValues[i]);
            }
        }
        //
        if (!Modifier.isPublic(method.getModifiers())) {
            method.setAccessible(true);
        }
        method.invoke(obj, params);
    }


    private static void fillMethodMap(Class<?> c) {
        methodMap.clear();
        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                fillBeforeSuite(method);
            }
            if (method.isAnnotationPresent(BeforeTest.class)) {
                fillBeforeTest(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                fillTest(method);
            }
            if (method.isAnnotationPresent(AfterTest.class)) {
                fillAfterTest(method);
            }
            if (method.isAnnotationPresent(AfterSuite.class)) {
                fillAfterSuite(method);
            }
        }
    }

    private static void fillAfterSuite(Method method) {
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new TestRunnerException("@AfterSuite метод должен быть статическим");
        }
        if (methodMap.containsKey(AfterSuite.class)) {
            throw new TestRunnerException("Метод @AfterSuite должен быть один");
        }
        if (method.getReturnType() != void.class) {
            throw new TestRunnerException("@AfterSuite метод не должен возвращать значение");
        }
        methodMap.put(AfterSuite.class, List.of(method));
    }

    private static void fillAfterTest(Method method) {
        if (methodMap.containsKey(AfterTest.class)) {
            throw new TestRunnerException("Метод @AfterTest должен быть один");
        }
        if (method.getReturnType() != void.class) {
            throw new TestRunnerException("@AfterTest метод не должен возвращать значение");
        }
        methodMap.put(AfterTest.class, List.of(method));
    }

    private static void fillTest(Method method) {
        List<Method> methods;
        if (method.getParameterTypes().length > 0 && !method.isAnnotationPresent(CsvSource.class)) {
            throw new TestRunnerException(
                "Метод @Test должен быть без параметров, либо значение параметров должно быть указано в аннотации @CsvSource");
        }
        if (methodMap.containsKey(Test.class)) {
            methods = methodMap.get(Test.class);
        } else {
            methods = new ArrayList<>();
            methodMap.put(Test.class, methods);
        }
        methods.add(method);
    }

    private static void fillBeforeTest(Method method) {
        if (methodMap.containsKey(BeforeTest.class)) {
            throw new TestRunnerException("Метод @BeforeTest должен быть один");
        }
        if (method.getReturnType() != void.class) {
            throw new TestRunnerException("@BeforeTest метод не должен возвращать значение");
        }
        methodMap.put(BeforeTest.class, List.of(method));
    }

    private static void fillBeforeSuite(Method method) {
        if (!Modifier.isStatic(method.getModifiers())) {
            throw new TestRunnerException("@BeforeSuite метод должен быть статическим");
        }
        if (methodMap.containsKey(BeforeSuite.class)) {
            throw new TestRunnerException("Метод @BeforeSuite должен быть один");
        }
        if (method.getReturnType() != void.class) {
            throw new TestRunnerException("@BeforeSuite метод не должен возвращать значение");
        }
        methodMap.put(BeforeSuite.class, List.of(method));
    }

    public static class TestRunnerException extends RuntimeException {

        public TestRunnerException(String message) {
            super(message);
        }
    }
}

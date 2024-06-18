package ru.vtb.javapro.test_engine.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {

    Priority priority() default Priority.P5;

    enum Priority {
        P1(1),
        P2(2),
        P3(3),
        P4(4),
        P5(5),
        P6(6),
        P7(7),
        P8(8),
        P9(9),
        P10(10);

        private final int i;

        Priority(int i) {
            this.i = i;
        }

        public int getI() {
            return i;
        }
    }
}



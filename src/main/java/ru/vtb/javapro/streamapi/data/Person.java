package ru.vtb.javapro.streamapi.data;


import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class Person {
    private final String name;
    private final Integer age;
    private final Position position;

    public enum Position{
        MANAGER, ENGINEER, DEVELOPER
    }
}

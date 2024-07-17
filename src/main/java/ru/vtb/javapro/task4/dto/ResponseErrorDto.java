package ru.vtb.javapro.task4.dto;


import org.springframework.http.HttpStatus;


public record ResponseErrorDto(HttpStatus status, String message) {}

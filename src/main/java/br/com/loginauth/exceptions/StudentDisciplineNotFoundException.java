package br.com.loginauth.exceptions;

public class StudentDisciplineNotFoundException extends RuntimeException {
    public StudentDisciplineNotFoundException(String message) {
        super(message);
    }
}

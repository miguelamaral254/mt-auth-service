package br.com.loginauth.exceptions;

public class DisciplineNotFoundException extends RuntimeException {
    public DisciplineNotFoundException(String message) {
        super(message);
    }
}

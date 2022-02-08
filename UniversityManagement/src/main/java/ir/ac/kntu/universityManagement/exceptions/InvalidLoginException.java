package ir.ac.kntu.universityManagement.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.security.auth.login.LoginException;

@AllArgsConstructor
public class InvalidLoginException extends LoginException {
    public InvalidLoginException(String message) {
        super(message);
    }
}

package no.donator.users.model;

import lombok.Getter;

@Getter
public class AuthResult {
    private final Session session;
    private final String errorMessage;

    private AuthResult(Session session, String errorMessage) {
        if (session != null && errorMessage != null)
            throw new RuntimeException("Authentication cannot both be failed and successful");
        else if (session == null && errorMessage == null)
            throw new RuntimeException("Authentication must be either failed or successful");
        this.session = session;
        this.errorMessage = errorMessage;
    }

    public static AuthResult success(Session session) {
        return new AuthResult(session, null);
    }

    public static AuthResult failed(String error) {
        return new AuthResult(null, error);
    }

    public boolean isSuccess() {
        return session != null;
    }

}

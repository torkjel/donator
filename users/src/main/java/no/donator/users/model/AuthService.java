package no.donator.users.model;

public class AuthService {

    private UserDao userDao;
    private SessionService sessionService;

    public AuthService(UserDao userDao, SessionService sessionService) {
        this.userDao = userDao;
        this.sessionService = sessionService;
    }

    public AuthResult authenticate(String username, String password) {
        User user = userDao.read(username);
        if (user == null)
            return AuthResult.failed("Unknown user: " + username);
        if (password.equals(user.getPassword())) {
            return AuthResult.success(sessionService.createSession(user.withoutMongoId()));
        } else {
            return AuthResult.failed("Incorrect password");
        }
    }
}

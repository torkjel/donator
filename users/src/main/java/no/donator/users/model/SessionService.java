package no.donator.users.model;

public class SessionService {

    public SessionService() {
    }

    public Session createSession(User user) {
        Session s = new Session();
        s.setUser(user);
        return s;
    }
}

package no.donator.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private static final long TIMEOUT = 1000 * 60 * 60 * 8;
    private static final long INACTIVITY_TIMEOUT = 1000 * 60 * 60;

    private long created = System.currentTimeMillis();
    private long lastVisit = created;
    private User user;

    public void touch() {
        lastVisit = System.currentTimeMillis();
    }

    public boolean valid() {
        long now = System.currentTimeMillis();
        return created + TIMEOUT > now && lastVisit + INACTIVITY_TIMEOUT > now;
    }
}
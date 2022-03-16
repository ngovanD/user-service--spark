package vn.cmc.du21.business.model;

public class UserSession {
    private User user;
    public  Session session;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public UserSession(User user, Session session) {
        this.user = user;
        this.session = session;
    }
}

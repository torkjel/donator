package no.donator.users;

import static spark.Spark.*;

import javax.servlet.http.HttpServletResponse;

import no.donator.users.model.User;
import no.donator.users.model.UserDao;
import spark.Request;
import spark.Response;
import spark.ResponseTransformer;

public class CrudController {

    private UserDao userDao;

    private ResponseTransformer jsonResponseTransformer =
            (payload) -> payload == null ? "" : ServiceFactory.OM.writeValueAsString(payload);

    public CrudController(UserDao userDao) {
        this.userDao = userDao;
    }

    public void go() {
        port(8080);

        post("/auth", this::authenticate);

        put("/users", "application/json", this::createOrUpdate);

        get("/users/:email", this::read, jsonResponseTransformer);

        post("/users/:email", this::authenticate, jsonResponseTransformer);

        delete("/users/:email", this::remove);

    }

    private String createOrUpdate(Request req, Response resp) {
        System.out.println(req.body());
        User newUser = User.parse(req.body());
        User existingUser = userDao.read(newUser.getEmail());
        if (existingUser != null)
            newUser.set_id(existingUser.get_id());
        userDao.store(newUser);
        resp.status(HttpServletResponse.SC_NO_CONTENT);
        return "";
    }

    private User read(Request req, Response resp) {
        String email = req.params("email");
        if (email == null) {
            resp.status(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        User existingUser = userDao.read(email);
        if (existingUser == null) {
            resp.status(HttpServletResponse.SC_NOT_FOUND);
        } else {
            existingUser.set_id(null);
            resp.type("application/json");
        }
        return existingUser;
    }

    private String remove(Request req, Response resp) {
        String email = req.params("email");
        if (email == null) {
            resp.status(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        boolean deleted = userDao.delete(email);
        if (!deleted)
            resp.status(HttpServletResponse.SC_NOT_FOUND);
        else
            resp.status(HttpServletResponse.SC_NO_CONTENT);
        return "";
    }

    private String authenticate(Request req, Response resp) {
        return "";
    }

}

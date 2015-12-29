package no.donator.users;

import static spark.Spark.*;
import javax.servlet.http.HttpServletResponse;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import no.donator.users.model.AuthResult;
import no.donator.users.model.AuthService;
import spark.Request;
import spark.Response;
import spark.Session;

@Log4j
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public void go() {
        before(this::logRequestStart);
        before(this::logRequestEnd);

        post("/login", "application/x-www-form-urlencoded", this::authenticate);

        post("/session/validate", this::validate);

        delete("/logout", this::logout);
    }

    @SneakyThrows
    private String authenticate(Request req, Response resp) {
        log.info(req.queryParams());
        Session session = req.session(true);
        String username = req.queryParams("email");
        String pw = req.queryParams("password");
        AuthResult result = authService.authenticate(username, pw);
        if (result.isSuccess()) {
            session.attribute("session", result);
            resp.status(HttpServletResponse.SC_OK);
            return ServiceFactory.OM.writeValueAsString(result);
        } else {
            session.invalidate();
            resp.status(HttpServletResponse.SC_FORBIDDEN);
            resp.type("text/plain");
            return result.getErrorMessage();
        }
    }

    private String validate(Request req, Response resp) {
        log.info(req.queryParams());
        return "";
    }

    private String logout(Request req, Response resp) {
        log.info(req.queryParams());
        return "";
    }


    private void logRequestStart(Request req, Response resp) {
        log.info(req.requestMethod() + " " + req.url());
        for (String header : req.headers())
            log.info(header + " = " + req.headers(header));
    }

    private void logRequestEnd(Request req, Response resp) {
        log.info(req.requestMethod() + req.url());
    }


}

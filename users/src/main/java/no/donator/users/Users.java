package no.donator.users;

import static spark.Spark.awaitInitialization;
import static spark.Spark.port;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Users {
    public static void main(String[] args) {
        new Users().go();
    }

    private void go() {
        log.info("Starting up");
        ServiceFactory sf = new ServiceFactory();

        port(8080);

        sf.getCrudController().go();
        sf.getAuthController().go();

        awaitInitialization();


    }
}

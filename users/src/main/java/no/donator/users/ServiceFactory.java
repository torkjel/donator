package no.donator.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;

import donator.support.Singleton;
import lombok.SneakyThrows;
import no.donator.users.model.AuthService;
import no.donator.users.model.SessionService;
import no.donator.users.model.UserDao;

public class ServiceFactory {

    public static final ObjectMapper OM = new ObjectMapper();

    private final Singleton<MongoClient> mongoClientSingleton = new Singleton<>();
    private final Singleton<UserDao> userDaoSingleton = new Singleton<>();
    private final Singleton<AuthService> authServiceSingleton = new Singleton<>();
    private final Singleton<CrudController> crudControllerSingleton = new Singleton<>();
    private final Singleton<AuthController> authControllerSingleton = new Singleton<>();

    public MongoClient getMongoClient() {
        return mongoClientSingleton.get(() -> createMongoClient());
    }

    @SuppressWarnings("deprecation")
    public UserDao getUserDao()  {
        return userDaoSingleton.get(() -> new UserDao(getMongoClient().getDB("users")));
    }

    public AuthService getAuthService() {
        return authServiceSingleton.get(() -> new AuthService(getUserDao(), new SessionService()));
    }

    public CrudController getCrudController() {
        return crudControllerSingleton.get(() -> new CrudController(getUserDao()));
    }

    public AuthController getAuthController() {
        return authControllerSingleton.get(() -> new AuthController(getAuthService()));
    }

    public void dispose() {
        mongoClientSingleton.get().ifPresent(MongoClient::close);
    }

    @SneakyThrows
    private MongoClient createMongoClient() {
        return new MongoClient();
    }

}

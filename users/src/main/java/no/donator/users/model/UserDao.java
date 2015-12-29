package no.donator.users.model;

import com.mongodb.DB;

import donator.support.mongo.AbstractMongoDao;

public class UserDao extends AbstractMongoDao<User> {

    public UserDao(DB db) {
        super(db, User.class, "users", "email");
    }
}

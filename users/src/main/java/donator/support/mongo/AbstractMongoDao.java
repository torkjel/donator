package donator.support.mongo;

import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class AbstractMongoDao<T> {

    private final DB db;
    private final String collection;
    private final String keyField;
    private final Class<T> type;

    protected AbstractMongoDao(DB db, Class<T> type, String collection, String keyField) {
        this.db = db;
        this.collection = collection;
        this.keyField = keyField;
        this.type = type;
    }

    public T read(String key) {
        try (DBCursor<T> result = getCollection().find(key(key))) {
            return result.hasNext() ? result.next() : null;
        }
    }

    public void store(T item) {
        getCollection().save(item);
    }

    public boolean delete(String key) {
        T deleted = getCollection().findAndRemove(key(key));
        return deleted != null;
    }

    protected JacksonDBCollection<T, String> getCollection() {
        DBCollection coll = db.getCollection(collection);
        coll.createIndex(new BasicDBObject().append(keyField, 1), new BasicDBObject().append("unique", true));
        return JacksonDBCollection.wrap(coll, type, String.class);
    }

    protected BasicDBObject key(String email) {
        return new BasicDBObject(keyField, email);
    }

}

package no.donator.users.model;

import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import no.donator.users.ServiceFactory;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class User {

    @ObjectId
    private org.bson.types.ObjectId _id;
    private String email;
    private String name;
    private String password;


    @SneakyThrows
    public static User parse(String data) {
        return ServiceFactory.OM.readValue(data, new TypeReference<User>() {});
    }

    public User withoutMongoId() {
        return new User(null, email, name, password);
    }

}

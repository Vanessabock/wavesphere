package de.vanessabock.backend.user.repository;

import de.vanessabock.backend.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<User, String> {
    Boolean existsUserByGithubId(int id);
    User findUserByGithubId(int id);
}

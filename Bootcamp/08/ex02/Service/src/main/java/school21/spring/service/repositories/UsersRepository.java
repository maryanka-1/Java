package school21.spring.service.repositories;

import school21.spring.service.models.User;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User>{
    public Optional<User> findByEmail(String email);
}

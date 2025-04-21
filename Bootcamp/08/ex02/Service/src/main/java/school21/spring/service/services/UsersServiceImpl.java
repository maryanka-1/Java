package school21.spring.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.UUID;


@Component("userServiceBean")
public class UsersServiceImpl implements UsersService {
    private UsersRepository usersRepository;

    @Autowired
    public UsersServiceImpl(@Qualifier("jdbcTemplateBean") UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override // написать реализацию
    public String signUp(String email) {
        if (usersRepository.findByEmail(email).isPresent()) {
//            System.out.println("Email already exists");
            return null;
        } else {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setPassword(String.valueOf(UUID.randomUUID()));
            usersRepository.save(newUser);
            return newUser.getPassword();
        }
    }
}

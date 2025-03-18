package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) {
        User user = usersRepository.findByLogin(login);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                if (!user.isStatusAuthenticate()) {
                    user.setStatusAuthenticate(true);
                    usersRepository.update(user);
                    return true;
                } else throw new AlreadyAuthenticatedException("User already authenticated");
            } else throw new EntityNotFoundException("invalid password");
        } else throw new EntityNotFoundException("invalid login");
    }
}

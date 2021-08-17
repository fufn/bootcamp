package bootcamp.fufnProject.services;

import bootcamp.fufnProject.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    Users getUser(String email);
    Users addUser(Users user);

}

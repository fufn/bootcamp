package bootcamp.fufnProject.services;

import bootcamp.fufnProject.entities.Users;
import bootcamp.fufnProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Users user = userRepository.findByEmail(s);

        if (user!=null){
            return user;
        } else {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }

    }

    @Override
    public Users getUser(String email) {
        return null;
    }

    @Override
    public Users addUser(Users user) {
        return null;
    }
}

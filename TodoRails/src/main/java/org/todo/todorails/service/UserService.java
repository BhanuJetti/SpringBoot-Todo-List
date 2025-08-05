package org.todo.todorails.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.todo.todorails.model.User;
import org.todo.todorails.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }
    public User registerUser(User user) throws Exception{
        if(userRepository.existsByUsername(user.getUsername())){
            throw new Exception("User name already Exists");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setTermsAccepted(true);

        return userRepository.save(user);

    }
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public void save(User user){
        userRepository.save(user);
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = findByUsername(username);
        if(user == null){
            user = findByEmail(username);
            if(user == null){
                throw new UsernameNotFoundException("Username not found");
            }
        }
        return user;
    }

}

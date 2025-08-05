package org.todo.todorails.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.todo.todorails.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    //Method to find a user by their username
    User findByUsername(String username);

    //Method to find a user by their email
    User findByEmail(String email);

    //Method to check a user exists by their username
    boolean existsByUsername(String username);

    //Method to check a user exists by their email
    boolean existsByEmail(String email);
}

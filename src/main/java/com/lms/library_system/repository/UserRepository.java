package com.lms.library_system.repository;

import com.lms.library_system.enums.Status;
import com.lms.library_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            select u
            from User u
            where u.userName=?1 AND u.status = ?2
            """)
    User loadUserByUsername(String username, Status status);

    User findByUserName(String librarianUsername);
}

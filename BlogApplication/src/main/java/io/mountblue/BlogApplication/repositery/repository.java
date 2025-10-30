package io.mountblue.BlogApplication.repositery;

import io.mountblue.BlogApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface repository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

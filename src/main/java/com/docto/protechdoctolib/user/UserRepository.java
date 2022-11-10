package com.docto.protechdoctolib.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Qualifier("users")
@Repository
@Transactional(readOnly=true)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u " +
            "SET u.enabled = TRUE WHERE u.email = ?1")
    int enableUser(String email);

    @Transactional
    @Modifying
    @Query(value = "SELECT u FROM User u where u.email = ?1 and u.password = ?2 ")
    Optional<User> login(String email, String password);
    Optional<User> findByToken(String token);
}

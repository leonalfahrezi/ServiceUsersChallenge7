package com.binar.chapter7.repository;

import com.binar.chapter7.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    @Query(value = "select username from users", nativeQuery = true)
    List<Users> getUsers();

    @Query(value = "select id from users", nativeQuery = true)
    Users getUsersById(Integer id);

    @Modifying
    @Query(value = "update users set username, email, password where username=:username, email=:email, password=:password", nativeQuery = true)
    void updateUser(@Param("username") String username, @Param("email") String email, @Param("password") String password);

    Optional<Users> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}

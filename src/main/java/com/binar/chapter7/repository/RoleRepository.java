package com.binar.chapter7.repository;

import com.binar.chapter7.model.Role;
import com.binar.chapter7.model.enumerations.ERoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERoles name);
}

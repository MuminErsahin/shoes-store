package com.workintech.shoes_store.repository;

import com.workintech.shoes_store.entity.ERole;
import com.workintech.shoes_store.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
package com.examplepersonalprj.secure_banking_backend.repository;

import com.examplepersonalprj.secure_banking_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {

    Boolean existsByEmail(String email);

}

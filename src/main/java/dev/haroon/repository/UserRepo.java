package dev.haroon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.haroon.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

}

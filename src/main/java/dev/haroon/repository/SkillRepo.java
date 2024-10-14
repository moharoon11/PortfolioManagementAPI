package dev.haroon.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.haroon.entities.Skill;

@Repository
public interface SkillRepo extends JpaRepository<Skill, Integer> {

	   Set<Skill> findByUserUserId(Integer userId);
}

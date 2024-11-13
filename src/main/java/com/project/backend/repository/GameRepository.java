package com.project.backend.repository;

import com.project.backend.model.Game;
import com.project.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}

package com.claz.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claz.models.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Integer> {
}

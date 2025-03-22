package com.clubeevantagens.authmicroservice.repository;

import com.clubeevantagens.authmicroservice.model.data.Category;
import com.clubeevantagens.authmicroservice.model.enums.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryType name);
    Set<Category> findByNameIn(Collection<CategoryType> name);
}

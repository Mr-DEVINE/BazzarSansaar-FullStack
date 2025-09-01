package com.example.BazzarSansaar.repository;

import com.example.BazzarSansaar.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}


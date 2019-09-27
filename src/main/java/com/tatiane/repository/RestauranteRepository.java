package com.tatiane.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tatiane.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {

}

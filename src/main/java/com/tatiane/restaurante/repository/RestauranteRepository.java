package com.tatiane.restaurante.repository;

import com.tatiane.restaurante.model.RestauranteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<RestauranteEntity, Integer> {

}

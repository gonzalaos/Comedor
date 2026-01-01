package ar.uba.fi.comedor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.uba.fi.comedor.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    public boolean existsByName(String name);

    public Optional<Ingredient> findByName(String name);
}

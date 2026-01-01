package ar.uba.fi.comedor.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.uba.fi.comedor.dto.IngredientRequest;
import ar.uba.fi.comedor.exception.ResourceAlreadyExistsException;
import ar.uba.fi.comedor.exception.ResourceNotFoundException;
import ar.uba.fi.comedor.model.Ingredient;
import ar.uba.fi.comedor.model.UnitOfMeasure;
import ar.uba.fi.comedor.repository.IngredientRepository;

@Service
public class IngredientService {
   @Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient getById(Long id) {
        return ingredientRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id: " + id));
    }
    
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient createIngredient(IngredientRequest ingredientRequest) {
        if(ingredientRepository.existsByName(ingredientRequest.name())) {
            throw new ResourceAlreadyExistsException("Ingredient " + ingredientRequest.name() + " already exists");
        }
        Ingredient ingredient = ingredientRequest.toEntity();
        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateIngredient(Long id, IngredientRequest ingredientRequest) {
        Ingredient existingIngredient = getById(id);

        if(!existingIngredient.getName().equals(ingredientRequest.name()) && 
            ingredientRepository.existsByName(ingredientRequest.name())) {
            throw new ResourceAlreadyExistsException("Ingredient " + ingredientRequest.name() + " already exists");
        }
        
        existingIngredient.setName(ingredientRequest.name());
        existingIngredient.setMinQuantity(ingredientRequest.minQuantity());
        existingIngredient.setUnitOfMeasure(ingredientRequest.unitOfMeasure());

        return ingredientRepository.save(existingIngredient);
    }

    public Ingredient addStock(Long id, BigDecimal amount, UnitOfMeasure unit) {
        Ingredient ingredient = getById(id);
        ingredient.addStock(amount, unit);
        return ingredientRepository.save(ingredient);
    }

    public Ingredient consumeStock(Long id, BigDecimal amount, UnitOfMeasure unit) {
        Ingredient ingredient = getById(id);
        ingredient.consumeStock(amount, unit);
        return ingredientRepository.save(ingredient);
    }
}

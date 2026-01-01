package ar.uba.fi.comedor.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ar.uba.fi.comedor.dto.IngredientRequest;
import ar.uba.fi.comedor.dto.IngredientResponse;
import ar.uba.fi.comedor.model.Ingredient;
import ar.uba.fi.comedor.model.UnitOfMeasure;
import ar.uba.fi.comedor.service.IngredientService;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/ingredients")
@AllArgsConstructor
@NoArgsConstructor
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<List<IngredientResponse>> getAll() {
        List<IngredientResponse> ingredientResponses = ingredientService.getAllIngredients()
            .stream().map(IngredientResponse::fromEntity).toList();
        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponse> getIngredient(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.getById(id);
        IngredientResponse ingredientResponse = IngredientResponse.fromEntity(ingredient);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponse);
    }

    @PostMapping
    public ResponseEntity<IngredientResponse> createIngredient(@Valid @RequestBody IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientService.createIngredient(ingredientRequest);
        IngredientResponse ingredientResponse = IngredientResponse.fromEntity(ingredient);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IngredientResponse> updateIngredient(@PathVariable Long id, @Valid @RequestBody IngredientRequest ingredientRequest) {
        Ingredient ingredient = ingredientService.updateIngredient(id, ingredientRequest);
        IngredientResponse ingredientResponse = IngredientResponse.fromEntity(ingredient);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponse);
    }

    @PatchMapping("/{id}/add-stock")
    public ResponseEntity<IngredientResponse> addStock(@PathVariable Long id, @RequestParam BigDecimal amount, @RequestParam UnitOfMeasure unit) {
        Ingredient ingredient = ingredientService.addStock(id, amount, unit);
        IngredientResponse ingredientResponse = IngredientResponse.fromEntity(ingredient);
        return ResponseEntity.status(HttpStatus.OK).body(ingredientResponse);
    }
}

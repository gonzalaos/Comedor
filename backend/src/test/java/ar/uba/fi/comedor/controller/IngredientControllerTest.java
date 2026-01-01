package ar.uba.fi.comedor.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import ar.uba.fi.comedor.dto.IngredientRequest;
import ar.uba.fi.comedor.model.Ingredient;
import ar.uba.fi.comedor.model.UnitOfMeasure;
import ar.uba.fi.comedor.service.IngredientService;

@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IngredientService ingredientService;

    @Test
    void createIngredientShouldReturnCreatedStatus() throws Exception {
        Ingredient ingredient = Ingredient.builder()
            .name("Leche")
            .quantity(BigDecimal.TEN)
            .minQuantity(BigDecimal.ONE)
            .unitOfMeasure(UnitOfMeasure.L)
            .build();
        
        when(ingredientService.createIngredient(any(IngredientRequest.class))).thenReturn(ingredient);

        String jsonContent = """
                {
                    "name": "Leche",
                    "quantity": 10,
                    "minQuantity": 1,
                    "unitOfMeasure": "L"
                }
                """;

        mockMvc.perform(post("/ingredients")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonContent))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name").value("Leche"))
            .andExpect(jsonPath("$.quantity").value(10L))
            .andExpect(jsonPath("$.minQuantity").value(1L))
            .andExpect(jsonPath("$.unitOfMeasure").value("Litros"));
    }
}

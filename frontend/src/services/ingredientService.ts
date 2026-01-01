import { type Ingredient } from '../types/ingredient';
import { type IngredientCreateFormData } from '../schemas/ingredientSchema';

const API_URL = 'http://localhost:8080/api/ingredients'; // Ajusta tu puerto

export const fetchIngredients = async (): Promise<Ingredient[]> => {
  const res = await fetch(API_URL);
  if (!res.ok) throw new Error('Error al cargar ingredientes');
  return res.json();
};

export const createIngredient = async (data: IngredientCreateFormData): Promise<Ingredient> => {
  const res = await fetch(API_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data),
  });

  const responseBody = await res.json();

  if (!res.ok) {
    // Lanzamos el error con la estructura que definimos en Java (ApiError)
    throw new Error(responseBody.message || 'Error al crear ingrediente');
  }
  return responseBody;
};
import { z } from 'zod';
import { UnitOfMeasure } from '../types/ingredient';

export const ingredientCreateSchema = z.object({
  name: z.string().min(1, 'El nombre es obligatorio').max(100, 'Máximo 100 caracteres'),
  quantity: z.number({ invalid_type_error: 'Debe ser un número' }).min(0, 'No puede ser negativo'),
  minQuantity: z.number({ invalid_type_error: 'Debe ser un número' }).min(0, 'No puede ser negativo'),
  unitOfMeasure: z.enum(Object.values(UnitOfMeasure) as [string, ...string[]], {
    errorMap: () => ({ message: 'Selecciona una unidad válida' }),
  }),
});

export type IngredientCreateFormData = z.infer<typeof ingredientCreateSchema>;
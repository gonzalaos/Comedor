export const UnitOfMeasure = {
  GRAMOS: 'Gramos',
  KILOGRAMOS: 'Kilogramos',
  LITROS: 'Litros',
  MILILITROS: 'Mililitros',
  UNIDAD: 'Unidad'
} as const;

export type UnitOfMeasure = typeof UnitOfMeasure[keyof typeof UnitOfMeasure];

export interface Ingredient {
  ingredientId?: number;
  name: string;
  quantity: number;
  minQuantity: number;
  unitOfMeasure: UnitOfMeasure;
  lowStock?: boolean; 
}

export interface ApiError {
  message: string;
  code: string;
  timestamp: string;
}
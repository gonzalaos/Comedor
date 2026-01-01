import React from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { ingredientCreateSchema, type IngredientCreateFormData } from '../schemas/ingredientSchema';
import { Input } from '../components/ui/Input';
import { Select } from '../components/ui/Select';
import { Button } from '../components/ui/Button';
import { UnitOfMeasure } from '../types/ingredient';
import './IngredientForm.css';

// Convertimos el objeto const a array para el Select
const unitOptions = Object.values(UnitOfMeasure).map(unit => ({
  value: unit,
  label: unit
}));

export const IngredientForm: React.FC = () => {
  const { 
    register, 
    handleSubmit, 
    formState: { errors, isSubmitting } 
  } = useForm<IngredientCreateFormData>({
    resolver: zodResolver(ingredientCreateSchema)
  });

  const onSubmit = (data: IngredientCreateFormData) => {
    // Aquí conectaremos el servicio más adelante
    console.log("Datos del formulario:", data);
  };

  return (
    <div className="form-container">
      <div className="form-header">
        <h3>Gestionar Stock</h3>
        <p>Añade o edita ingredientes del inventario</p>
      </div>

      <form onSubmit={handleSubmit(onSubmit)} className="form-grid">
        {/* Fila 1: Nombre (ocupa todo el ancho en móvil, parte en desktop) */}
        <div className="col-span-2">
          <Input
            label="Nombre del Ingrediente"
            placeholder="Ej. Salsa de Tomate"
            error={errors.name?.message}
            {...register('name')}
          />
        </div>

        {/* Fila 2: Cantidad y Unidad */}
        <div className="col-span-1">
          <Input
            label="Cantidad Actual"
            type="number"
            step="0.01"
            error={errors.quantity?.message}
            {...register('quantity', { valueAsNumber: true })}
          />
        </div>

        <div className="col-span-1">
          <Select
            label="Unidad de Medida"
            options={unitOptions}
            error={errors.unitOfMeasure?.message}
            {...register('unitOfMeasure')}
          />
        </div>

        {/* Fila 3: Stock Mínimo y Botón */}
        <div className="col-span-1">
          <Input
            label="Alerta Stock Mínimo"
            type="number"
            step="0.01"
            error={errors.minQuantity?.message}
            {...register('minQuantity', { valueAsNumber: true })}
          />
        </div>

        <div className="col-span-1 flex-end">
          <Button 
            type="submit" 
            isLoading={isSubmitting}
            className="w-full"
          >
            Guardar Ingrediente
          </Button>
        </div>
      </form>
    </div>
  );
};
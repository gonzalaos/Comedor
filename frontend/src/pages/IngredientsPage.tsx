import React from 'react';
import { IngredientForm } from '../components/IngredientForm';
import '../styles/global.css';

const IngredientsPage: React.FC = () => {
  return (
    <div style={{ padding: '2rem', maxWidth: '800px', margin: '0 auto' }}>
      <h1>Inventario</h1>
      <IngredientForm />
    </div>
  );
};

export default IngredientsPage;
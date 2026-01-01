import React, { type ButtonHTMLAttributes } from 'react';
import './Button.css';

interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: 'primary' | 'secondary' | 'danger';
  isLoading?: boolean;
}

export const Button: React.FC<ButtonProps> = ({ 
  children, 
  variant = 'primary', 
  isLoading, 
  className,
  disabled,
  ...props 
}) => {
  return (
    <button
      className={`btn btn-${variant} ${className || ''}`}
      disabled={isLoading || disabled}
      {...props}
    >
      {isLoading ? <span className="loader">...</span> : children}
    </button>
  );
};
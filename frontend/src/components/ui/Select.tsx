import { type SelectHTMLAttributes, forwardRef } from 'react';
import './Select.css';

interface Option {
  value: string;
  label: string;
}

interface SelectProps extends SelectHTMLAttributes<HTMLSelectElement> {
  label: string;
  options: Option[];
  error?: string;
  placeholder?: string;
}

export const Select = forwardRef<HTMLSelectElement, SelectProps>(
  ({ label, options, error, placeholder = "Seleccionar...", ...props }, ref) => {
    return (
      <div className="select-wrapper">
        <label className="select-label">{label}</label>
        <select
          ref={ref}
          className={`select-field ${error ? 'select-error' : ''}`}
          {...props}
        >
          <option value="" disabled selected>{placeholder}</option>
          {options.map((opt) => (
            <option key={opt.value} value={opt.value}>
              {opt.label}
            </option>
          ))}
        </select>
        {error && <span className="select-error-msg">{error}</span>}
      </div>
    );
  }
);
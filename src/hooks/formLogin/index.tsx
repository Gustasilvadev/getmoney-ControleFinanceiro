import { useState } from 'react';

export const useFormLogin = () => {
    const [errors, setErrors] = useState({ email: '', senha: '' });

    const validate = (email: string, senha: string) => {
        const newErrors = { email: '', senha: '' };

        if (!email.trim() && !senha.trim()) {
            newErrors.email = 'O campos não pode estar em branco*';
            newErrors.senha = 'O campos não pode estar em branco*';
        } 

        else if (!email.trim() || !senha.trim() || !email.includes('@') || senha.length < 3) {
            newErrors.email = 'E-mail ou senha inválidos*';
            newErrors.senha = 'E-mail ou senha inválidos*';
        }

        setErrors(newErrors);
        return !newErrors.email && !newErrors.senha;
    };

    const clearError = (field: keyof typeof errors) => {
        setErrors(prev => ({ ...prev, [field]: '' }));
    };

    return { errors, validate, clearError };
};
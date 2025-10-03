import { useState } from 'react';

export const useFormLogin =()=>{
    const [errors, setErrors] = useState({ email: '', senha: '' });

    const validate = (email: string, senha: string) => {
        const newErrors = { email: '', senha: '' };

        if (!email) newErrors.email = 'Email é obrigatório*';
        else if (!email.includes('@')) newErrors.email = 'Email inválido*';

        if (!senha) newErrors.senha = 'Senha é obrigatória*';
        else if (senha.length < 3) newErrors.senha = 'Senha muito curta*';

        setErrors(newErrors);
        return !newErrors.email && !newErrors.senha;
    };

    const clearError = (field: keyof typeof errors) => {
        setErrors(prev => ({ ...prev, [field]: '' }));
    };

    return { errors, validate, clearError };
};
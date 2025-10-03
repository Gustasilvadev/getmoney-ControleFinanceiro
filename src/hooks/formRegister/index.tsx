import { useState } from 'react';

export const useFormRegister =()=>{
    const [errors, setErrors] = useState({ nome:'', email: '', senha: '' });

    const validate = (nome:string,email: string, senha: string) => {
        const newErrors = { nome:'', email: '', senha: '' };

        if (!nome) newErrors.nome = 'Nome é obrigatório*';
        else if (nome.length < 5  ) newErrors.nome = 'Nome muito curto*';
        else if (nome.length > 20 ) newErrors.nome = 'Nome muito longo*';

        if (!email) newErrors.email = 'Email é obrigatório*';
        else if (!email.includes('@')) newErrors.email = 'Email inválido*';

        if (!senha) newErrors.senha = 'Senha é obrigatória*';
        else if (senha.length < 3) newErrors.senha = 'Senha muito curta*';

        setErrors(newErrors);
        return !newErrors.nome && !newErrors.email && !newErrors.senha;
    };

    const clearError = (field: keyof typeof errors) => {
        setErrors(prev => ({ ...prev, [field]: '' }));
    };

    return { errors, validate, clearError };
};
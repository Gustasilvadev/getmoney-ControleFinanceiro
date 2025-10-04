import { useState, useEffect } from 'react';

export const useApi = <T>(serviceCall: () => Promise<T>) => {

  const [data, setData] = useState<T | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    
    const buscarDados = async () => {
      try {
        const resultado = await serviceCall();
        setData(resultado);
      } catch (error) {
        console.error('Erro:', error);
      } finally {
        setLoading(false);
      }
    };

    buscarDados();
  }, []);

  return { data, loading };
};
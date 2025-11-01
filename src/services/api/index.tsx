import axios from 'axios';
import { AuthService } from './storage';

export const api = axios.create({
  baseURL: 'http://192.168.0.4:8082/api',
  // baseURL: 'http://10.136.36.162:8080/api',
  timeout: 10000,

  headers:{
    'Content-Type':'application/json',
  },
});


const ENDPOINTS_PUBLICOS = [
  '/autenticacao/autenticarUsuario',
  '/autenticacao/registrarUsuario'
];

api.interceptors.request.use(async (config) => {
  const isEndpointPublico = ENDPOINTS_PUBLICOS.some(endpoint => 
    config.url?.includes(endpoint)
  );

  if (!isEndpointPublico) {
    const token = await AuthService.getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }

  return config;
});

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      await AuthService.logout();
      throw new Error('Sessão expirada');
    }
    throw error;
  }
);

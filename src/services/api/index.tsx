import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://192.168.0.2:8080/api',
  timeout: 10000,
});


api.interceptors.request.use(
  (config) => {
    console.log('Fazendo request para:', config.url);
    return config;
  },
  (error) => {
    console.error('Erro no request:', error);
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  (response) => {
    console.log('Response recebido:', response.status);
    return response;
  },
  (error) => {
    console.error('Erro na response:', error.message);
    return Promise.reject(error);
  }
);
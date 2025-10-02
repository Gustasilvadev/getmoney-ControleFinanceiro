import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://10.136.36.73:8080/api',
  timeout: 10000,

  headers:{
    'Content-Type':'application/json',
  },
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
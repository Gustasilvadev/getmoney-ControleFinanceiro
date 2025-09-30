import {api} from "../../index";
import { LoginResponse } from "@/src/interfaces/response/loginResponse";
import { LoginRequest } from "@/src/interfaces/request/loginRequest";

export const loginService ={
    login: async (email: string, senha: string): Promise<LoginResponse> =>{
        try{
            const credentials: LoginRequest = { email, senha };
            const response = await api.post<LoginResponse>('/autenticacao/autenticarUsuario', credentials);
            return response.data;
        }catch{
            throw new Error('Falha no login. Tente novamente.');
        }
    }
};
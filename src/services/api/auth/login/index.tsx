import {api} from "../../index";
import { LoginResponse } from "@/src/interfaces/auth/response";
import { LoginRequest } from "@/src/interfaces/auth/request";
import { AuthService } from "../../storage";

export const LoginService ={
    
    login: async (email: string, senha: string): Promise<LoginResponse> =>{
        try{
            const credentials: LoginRequest = { email, senha };
            const response = await api.post<LoginResponse>('/autenticacao/autenticarUsuario', credentials);

            const { usuario, token } = response.data;
            await AuthService.login(usuario, token);
            return response.data;
            
        }catch{
            throw new Error('Falha no login. Tente novamente.');
        }
    }
};
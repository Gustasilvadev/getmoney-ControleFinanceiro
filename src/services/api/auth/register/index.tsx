import { api } from "../../index";
import { RegisterRequest } from "@/src/interfaces/request/registerRequest";


export const registerService = {
    register: async (nome:string, email:string, senha:string): Promise<void>=>{
        try{
            const credentials: RegisterRequest = { nome, email, senha };
            const response = await api.post<void>('/autenticacao/registrarUsuario', credentials);
            return response.data;
        }catch{
            throw new Error('Falha no login. Tente novamente.');
        }
    }
};
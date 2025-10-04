import { UsuarioLoginResponse } from "@/src/interfaces/usuario/response";
import { api } from "../index";
import { AuthService } from "../storage";

export const HomeService ={

    listarUsuario: async(): Promise<UsuarioLoginResponse> =>{
        try{
            const token = await AuthService.getToken();
            const response = await api.get<UsuarioLoginResponse>('/usuario/listarUsuarioLogado');
            return response.data;

        } catch (error) {
            console.error('Erro ao listar usuário:', error);
            throw error;
        }
    }

};
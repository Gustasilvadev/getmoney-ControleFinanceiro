import { api } from "../index";
import { MetaResponse } from "@/src/interfaces/meta/response";


export const MetaService = {

    listarMetas: async(): Promise<MetaResponse[]> =>{
        try{

            const response = await api.get<MetaResponse[]>('/meta/listar');
            return response.data;

        }catch(error){
            throw error;
        }
    }

};
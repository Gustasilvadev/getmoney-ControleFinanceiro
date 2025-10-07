import { MetaRequest } from "@/src/interfaces/meta/request";
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
    },

    criarMeta: async(nome:string,valorAlvo:number,status:number,data:string): Promise<MetaRequest>=>{
        try{
            const dados: MetaRequest = {nome,valorAlvo,status,data};
            const response = await api.post<MetaRequest>('/meta/criar',dados);
            return response.data;
        }catch(error){
            throw error;
        }
    }

};



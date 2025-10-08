import { TransacaoRequest } from "@/src/interfaces/transacao/request";
import { api } from "../index";
import { TransacaoBasicaResponse, TransacaoCategoriaMetaResponse, TransacaoResponse } from "@/src/interfaces/transacao/response";


export const TransacaoService ={

    listarTransacao: async(): Promise<TransacaoBasicaResponse[]> =>{
        try{
            const response = await api.get<TransacaoBasicaResponse[]>('/transacao/listar');
            return response.data;
        }catch(error){
            throw error;
        }
    },

    listarPorTransacaoId: async(transacaoId:number): Promise<TransacaoCategoriaMetaResponse> =>{
        try{
            const response = await api.get<TransacaoCategoriaMetaResponse>(`/transacao/listarPorTransacaoId/${transacaoId}`);
            return response.data;
        }catch(error){
            throw error;
        }

    },

    criarTransacao: async(valor:number,descricao:string,data:string,categoriaId:number,metasId:number[]): Promise<void>=>{
        try{
            const dados: TransacaoRequest = {valor, descricao, data, categoriaId, metasId};
            const response = await api.post<void>('/transacao/criar',dados);
            return response.data;
        }catch(error){
            throw error;
        }
    }
};
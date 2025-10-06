import { api } from "../index";
import { ProgressoMetaResponse, ResumoFinanceiroResponse, ValorTotalResponse } from "@/src/interfaces/resumo/response";


export const ResumoService = {

    listarProgressoDaMeta: async(): Promise<ProgressoMetaResponse> =>{
        try{
            const response = await api.get<ProgressoMetaResponse>('/estatistica/progressoDaMeta');
            return response.data;
        }catch (error){
            throw error;        
        }
    },

    listarValorTotalCategoria: async(): Promise<ValorTotalResponse> =>{
        try{
            const response = await api.get<ValorTotalResponse>('/estatistica/listarValorTotal/categoria');
            return response.data;
        }catch(error){
            throw error;

        }
    },

    listarLucro: async(): Promise<ResumoFinanceiroResponse> =>{
        try{
            const response = await api.get<ResumoFinanceiroResponse>('/estatistica/listarLucro');
            return response.data;
        } catch(error){
            throw error;
        }
    }

};
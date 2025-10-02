import { Status } from "@/src/enums/status";
import { MetaBasicaResponse } from "../meta/response";

export interface TransacaoResponse{
    id: number;
    valor: number;
    descricao: string;
    data: string;
    status: Status;
    usuarioId: number;
    categoriaId: number;
    metasId: MetaBasicaResponse[];
}

export interface TransacaoBasicaResponse{
    id: number;
    valor: number;
    descricao: string;
    data: string;
    categoriaId:number,
    categoriaNome:string,
    categoriaTipo:string,
}

export interface TransacaoPorCategoriaResponse{
    id: number,
    valor: number,
    descricao: string,
    data: string,
    status:Status,
    usuarioId:number,
}


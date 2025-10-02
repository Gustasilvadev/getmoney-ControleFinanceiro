import { Status } from "@/src/enums/status";

export interface EvolucaoMensalResponse{
    periodo:string,
    totalDespesas:number,
    totalReceitas:number,
    saldo:number,
}

export interface ProgressoMeta{
    metaId:number,
    metaNome:string,
    valorAlvo:number,
    valorAtual:number,
    percentualConcluido:number,
    status:Status,
}

export interface ResumoFinanceiro{
    receitas:number,
    despesas:number,
    lucro:number,
}


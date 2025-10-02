import { Status } from "@/src/enums/status";

export interface MetaRequest{
    nome:string,
    valorAlvo:number,
    status:Status,
    data:string,
}

export interface MetaUpdateRequest{
    nome:string,
    valorAlvo:number,
    status:Status,
    data:string,
}


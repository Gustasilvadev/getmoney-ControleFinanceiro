import { Status } from "@/src/enums/status";

export interface Usuario{
    id:number,
    nome:string,
    email:string,
    senha:string,
    dataCriacao:string,
    status:Status,
    
}
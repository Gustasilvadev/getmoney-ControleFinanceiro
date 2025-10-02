import { Status } from "@/src/enums/status";

export interface TransacaoRequest {
  valor: number;
  descricao: string;
  data: string;
  categoriaId: number;
  metasId: number[];
}

export interface TransacaoUpdateRequest{
    valor:number,
    descricao:string,
    data:string,
    status:Status,
    categoriaId:number,
    metasId:number[],
}
import { EvolucaoMensalResponse } from "@/src/interfaces/estatistica/response";
import { useState } from "react";

export interface GraficoLinhaData extends Record<string, unknown> { //Garante compatibilidade
  x: string;
  receitas: number;
  despesas: number;
  saldo: number;
}

export const useGraficoLinha = () => {

  const [data, setData] = useState<GraficoLinhaData[]>([]);

  const transformData = (apiData: EvolucaoMensalResponse | EvolucaoMensalResponse[]): void => {
    // Converte para array sempre
    const dataArray = Array.isArray(apiData) ? apiData : [apiData];

    if (!dataArray || dataArray.length === 0) {
      setData([]);
      return;
    }

    // Ordena do mais antigo para o mais recente
    const sortedData = [...dataArray].sort((a, b) => 
      a.periodo.localeCompare(b.periodo)
    );

    const transformed = sortedData.map(item => ({
      x: formatPeriodo(item.periodo),
      receitas: item.totalReceitas,
      despesas: item.totalDespesas,
      saldo: item.saldo
    }));

    setData(transformed);
  };

  // Formata a data 2025-09-01 para Set/25
  const formatPeriodo = (periodo: string): string => {
    try {
      // Extrair partes diretamente da string "YYYY-MM-DD"
      const [ano, mes, dia] = periodo.split('-').map(Number);
      
      const monthNames = [
        "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", 
        "Jul", "Ago", "Set", "Out", "Nov", "Dez"
      ];
      
      // Usar o mês diretamente da string
      const month = monthNames[mes - 1]; // -1 porque o array é 0-based
      const year = ano.toString().slice(2);
      
      return `${month}/${year}`;
    } catch (error) {
      return periodo;
    }
  };

  return {
    data,
    transformData
  };
}
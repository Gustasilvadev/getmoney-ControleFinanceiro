import { useState, useMemo } from 'react';
import { useApi } from '../useApi';
import {TransacaoService} from '../../services/api/transacao';

export const useTransactionHistory = () => {

  const [mes, setMes] = useState(new Date().getMonth() + 1);
  const [ano, setAno] = useState(new Date().getFullYear());
  
  const { data: transacoes, loading } = useApi(TransacaoService.listarTransacao);

  const obterMeses = () => ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];

    // Calcula anos disponíveis baseado nas transações
    const obterAnosDisponiveis = (transacoes: any[]) => {
        const anoAtual = new Date().getFullYear();
        
        // Se não tem transações, retorna anos padrão
        if (!transacoes || transacoes.length === 0) {
            return [anoAtual - 1, anoAtual, anoAtual + 1];
        }

        // Pega anos únicos das transações
        const anosDasTransacoes = [...new Set(transacoes.map(t => new Date(t.data).getFullYear()))];
        const anosRecentes = [anoAtual - 1, anoAtual, anoAtual + 1];

        // Combina e remove duplicados
        const todosAnos = [...new Set([...anosDasTransacoes, ...anosRecentes])];
        
        // Ordena do mais recente para o mais antigo
        return todosAnos.sort((a, b) => b - a);
    };

    // Filtra transações por mês e ano específicos
    const filtrarTransacoesPorMes = (transacoes: any[], mes: number, ano: number) => {
        if (!transacoes) return [];
        
        return transacoes.filter(transacao => {
            const data = new Date(transacao.data);
            return data.getMonth() + 1 === mes && data.getFullYear() === ano;
        });
    };

    // Soma todos os valores positivos (receitas)
    const calcularReceitas = (transacoes: any[]) => {
        return transacoes
            .filter(transacao => transacao.valor > 0)
            .reduce((total, transacao) => total + transacao.valor, 0);
    };

    // Soma todos os valores negativos (despesas)
    const calcularDespesas = (transacoes: any[]) => {
        return transacoes
            .filter(transacao => transacao.valor < 0)
            .reduce((total, transacao) => total + Math.abs(transacao.valor), 0);
    };

    // Calcula saldo (receitas - despesas)
    const calcularSaldo = (receitas: number, despesas: number) => {
        return receitas - despesas;
    };


    // Usando as funções separadas
    const meses = obterMeses();
    const anos = obterAnosDisponiveis(transacoes || []);
    const transacoesFiltradas = filtrarTransacoesPorMes(transacoes || [], mes, ano);
    const receitas = calcularReceitas(transacoesFiltradas);
    const despesas = calcularDespesas(transacoesFiltradas);
    const saldo = calcularSaldo(receitas, despesas);

    return {
        mes, 
        setMes,
        ano, 
        setAno,
        meses,
        anos,
        transacoesFiltradas,
        receitas,
        despesas,
        saldo,
        carregando: loading,
    };

};
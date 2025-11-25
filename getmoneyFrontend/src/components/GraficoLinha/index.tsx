import { CartesianChart, Line, useChartPressState} from "victory-native";
import { useEffect, useState } from "react";
import { useGraficoLinha, GraficoLinhaData  } from "@/src/hooks/GraficoLinha/useGraficoLinha";
import { ActivityIndicator, View,Text, TouchableOpacity } from "react-native";

import { useApi } from "@/src/hooks/useApi";
import { EstatisticaService } from "@/src/services/api/estatisticas";

import { styles } from "./style";
import { EvolucaoMensalResponse } from "@/src/interfaces/estatistica/response";

interface GraficoLinhaProps {
  refreshKey?: number;
}

export const GraficoLinha = ({ refreshKey = 0 }: GraficoLinhaProps) => {
    
  const { data: apiData, loading: apiLoading } = useApi<EvolucaoMensalResponse[]>(() => EstatisticaService.listarAnaliseEvolucaoMensal(),[],[refreshKey]);

  const { data: chartData, transformData } = useGraficoLinha();

  const [tooltipData, setTooltipData] = useState<GraficoLinhaData | null>(null);
  const [tooltipSource, setTooltipSource] = useState<'chart' | 'table' | null>(null);

  // Estado para tooltips
   const pressState = useChartPressState({ 
    x: "" as string, 
    y: { receitas: 0, despesas: 0 } 
  });

  // Transforma os dados quando a API retornar
  useEffect(() => {
    if (apiData) {
      transformData(apiData);
    }
  }, [apiData, transformData]);

  // Tooltip do gráfico (pontos)
  useEffect(() => {
    if (pressState.isActive) {
      // Acessar o valor dentro do objeto SharedValue
      const sharedValueObj = (pressState.state.x as any).value;
      
       // Extrair o valor real que está dentro do .value
      const xValue = sharedValueObj.value;
      const found = chartData.find(item => item.x === xValue);
      
      setTooltipData(found || null);
      setTooltipSource('chart');
    } else if (tooltipSource === 'chart') {
      // Só limpa se a tooltip atual veio do gráfico
      setTooltipData(null);
      setTooltipSource(null);
    }
  }, [pressState.isActive, pressState.state.x.value, chartData, tooltipSource]);

  // Estados de carregamento
  if (apiLoading) {
    return (
      <View style={styles.container}>
        <ActivityIndicator size="large" color="#4ECDC4" />
        <Text style={styles.loadingText}>Carregando evolução mensal...</Text>
      </View>
    );
  }

  // Função para mostrar tooltip quando pressionar na linha da tabela
  const handleTableRowPress = (item: GraficoLinhaData) => {
    setTooltipData(item);
    setTooltipSource('table');
  };

  // Função para fechar tooltip manualmente
  const handleCloseTooltip = () => {
    setTooltipData(null);
    setTooltipSource(null);
  };

  // Quando nao encontrar nenhum dado
  if (!chartData || chartData.length === 0) {
    return (
      <View style={styles.container}>
        <Text style={styles.emptyText}>Nenhum dado encontrado para evolução mensal</Text>
      </View>
    );
  }

  return (
      <View style={styles.container}>
        <Text style={styles.title}>Evolução Mensal - Receitas vs Despesas</Text>
      
        {tooltipData && (
          <TouchableOpacity 
            style={styles.tooltip} 
            onPress={handleCloseTooltip}
            activeOpacity={0.9}
          >

            <Text style={styles.tooltipText}>{tooltipData.x}</Text>
            <Text style={[styles.tooltipText, { color: '#4ECDC4' }]}>
              Receitas: R$ {tooltipData.receitas.toFixed(2)}
            </Text>
            <Text style={[styles.tooltipText, { color: '#FF6B6B' }]}>
              Despesas: R$ {tooltipData.despesas.toFixed(2)}
            </Text>

            <Text style={[styles.tooltipText, {color: tooltipData.saldo >= 0 ? '#4ECDC4' : '#FF6B6B'}]}>
              Saldo: R$ {tooltipData.saldo.toFixed(2)}
            </Text>

            <Text style={[styles.tooltipText, { fontSize: 12, color: '#999', marginTop: 5 }]}>
              {tooltipSource === 'chart' ? 'Toque para fechar' : 'Clique para fechar'}
            </Text>

          </TouchableOpacity>
        )}

        {/* Gráfico com interação */}
        <View style={{ height: 280 }}>
          <CartesianChart
            data={chartData}
            xKey="x"
            yKeys={["receitas", "despesas"]}
            domainPadding={{ left: 40, right: 40, top: 50 }}
            chartPressState={pressState.state}
            axisOptions={{
              labelColor: '#666',
              formatYLabel: (value) => `R$ ${Number(value).toFixed(0)}`
            }}
          >

            {({ points }) => (
              <>
                <Line 
                  points={points.receitas}
                  color="#4ECDC4"
                  strokeWidth={3}
                  curveType="linear"
                />
                <Line 
                  points={points.despesas}
                  color="#FF6B6B"
                  strokeWidth={3}
                  curveType="linear"
                />
              </>
            )}
          </CartesianChart>
        </View>

        {/* Legenda */}
        <View style={styles.legend}>
          <View style={styles.legendItem}>
            <View style={[styles.legendColor, { backgroundColor: '#4ECDC4' }]} />
            <Text style={styles.legendText}>
              Receitas: R$ {chartData.reduce((sum, item) => sum + item.receitas, 0).toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
            </Text>
          </View>
          
          <View style={styles.legendItem}>
            <View style={[styles.legendColor, { backgroundColor: '#FF6B6B' }]} />
            <Text style={styles.legendText}>
              Despesas: R$ {chartData.reduce((sum, item) => sum + item.despesas, 0).toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
            </Text>
          </View>
        </View>

        {/* Tabela */}
        <View style={styles.table}>
          <Text style={styles.tableTitle}>Detalhamento por Mês</Text>
          
          <View style={styles.tableHeader}>
            <Text style={styles.headerCell}>Mês</Text>
            <Text style={styles.headerCell}>Receitas</Text>
            <Text style={styles.headerCell}>Despesas</Text>
            <Text style={styles.headerCell}>Saldo</Text>
          </View>

          {chartData.map((item, index) => (
            <TouchableOpacity 
              key={index} 
              onPress={() => handleTableRowPress(item)}
              style={[
                styles.tableRow,
                index % 2 === 0 ? styles.evenRow : styles.oddRow,
                tooltipData && item.x === tooltipData.x && styles.highlightedRow
              ]}
            >
              <Text style={styles.cellMonth}>{item.x}</Text>
              <Text style={styles.cellReceita}>
                R$ {item.receitas.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
              </Text>

              <Text style={styles.cellDespesa}>
                R$ {item.despesas.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
              </Text>

              <Text style={[
                styles.cellSaldo,
                item.saldo >= 0 ? styles.saldoPositivo : styles.saldoNegativo
              ]}>
                R$ {item.saldo.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
              </Text>
              
            </TouchableOpacity>
          ))}
        </View>
      </View>
  );
}
import { VictoryPie } from "victory-pie";
import { useEffect } from "react";
import { View, Text } from "react-native";
import {styles} from "./style";

import { useChartData } from "@/src/hooks/donutChart/useDonutChart";
import { useApi } from "@/src/hooks/useApi";

import { EstatisticaService } from "@/src/services/api/estatisticas";

export const DonutChart = ()=>{

    // Hook para a API
    const { data: apiData, loading: apiLoading } = useApi(() => EstatisticaService.listarGastosPorCategoria());

    // Hook para transformar dados do gráfico
    const { data: chartData, total, transformData } = useChartData();

    // Transforma os dados quando a API retornar
    useEffect(() => {
        if (apiData) {
            const dataArray = Array.isArray(apiData) ? apiData : [apiData];
            transformData(dataArray);
        }
    }, [apiData, transformData]);

    // Cores para o gráfico
    const colorScale = [
        '#FF6B6B', '#4ECDC4', '#45B7D1', '#FFEAA7', 
        '#A29BFE', '#FD79A8', '#00CEC9', '#FDCB6E'
    ];

    if (chartData.length === 0) {
        return (
        <View style={styles.container}>
            <Text style={styles.emptyText}>Nenhum gasto encontrado</Text>
        </View>
        );
    }

    return(

        <View style={styles.container}>
            <Text style={styles.title}>Gastos por Categoria</Text>
      
            <Text style={styles.totalText}>
                Total: R$ {total.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
            </Text>
      
                    {/* 👇 AGORA USA VictoryPie (funciona!) */}
            <VictoryPie
                data={chartData}
                innerRadius={80}
                colorScale={colorScale}
                height={300}
                width={300}
                style={{
                    labels: { 
                        fill: "white", 
                        fontSize: 11, 
                        fontWeight: "bold",
                    }
                }}
                labelRadius={({ innerRadius }) => {
                    const radius = typeof innerRadius === 'number' ? innerRadius : 80;
                    return radius + 50;
                }}
                labels={({ datum }) => `R$ ${datum.y.toLocaleString('pt-BR')}`}
            />

            {/* Legenda das categorias */}
            <View style={styles.legend}>
                {chartData.map((item, index) => (
                    <View key={index} style={styles.legendItem}>
                        <View 
                            style={[
                                styles.legendColor, 
                                { backgroundColor: colorScale[index % colorScale.length] }
                            ]} 
                        />
                        <Text style={styles.legendText}>
                            {item.x}: R$ {item.y.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
                        </Text>
                    </View>
                ))}
            </View>
        </View>
  );
};
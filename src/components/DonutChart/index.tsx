import { Pie, PolarChart } from "victory-native";

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

    // Debug logs
    console.log("API Loading:", apiLoading);
    console.log("API Data:", apiData);
    console.log("Chart Data:", chartData);
    console.log("Total:", total);

    // Converter os dados do hook para o formato do Victory Native
    const victoryData = chartData.map((item, index) => ({
        value: item.y, // valor numérico (valorTotal)
        label: item.x, // categoria (categoriaNome)
        color: colorScale[index % colorScale.length] // cor correspondente
    }));

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
      
              {/* Nova API do Victory Native */}
            <View style={{ height: 300, width:80 }}>
                <PolarChart
                    data={victoryData}
                    colorKey="color"
                    valueKey="value"
                    labelKey="label"
                >
                    <Pie.Chart innerRadius={80} />
                </PolarChart>
            </View>

            {/* Legenda */}
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
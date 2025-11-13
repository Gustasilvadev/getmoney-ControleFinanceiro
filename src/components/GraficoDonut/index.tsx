import { Pie, PolarChart } from "victory-native";

import { useEffect, useState } from "react";
import { View, Text, ActivityIndicator, TouchableWithoutFeedback } from "react-native";
import {styles} from "./style";

import { useGraficoDonut } from "@/src/hooks/GraficoDonut/useGraficoDonut";
import { useApi } from "@/src/hooks/useApi";

import { EstatisticaService } from "@/src/services/api/estatisticas";
import { ValorTotalResponse } from "@/src/interfaces/estatistica/response";


interface GraficoDonutProps {
  refreshKey?: number;
}

export const GraficoDonut = ({ refreshKey = 0 }: GraficoDonutProps) =>{

    const { data: apiData, loading: apiLoading } = useApi<ValorTotalResponse[]>(
        () => EstatisticaService.listarGastosPorCategoria(),
        [], // initialData como array vazio
        [refreshKey]
    );

    // Hook para transformar dados do gráfico
    const { data: chartData, total, transformData } = useGraficoDonut();

    useEffect(() => {
        if (apiData) {
        const arrayData = Array.isArray(apiData) ? apiData : [apiData];
        transformData(arrayData);
        }
    }, [apiData, transformData]);

    // Estado para tooltip
    const [selectedSlice, setSelectedSlice] = useState<{ 
        label: string; 
        value: number; 
        percentage: number;
        color: string;
    } | null>(null);


    // Cores para o gráfico
    const colorScale = [
        '#FF6B6B', '#4ECDC4', '#45B7D1', '#FFEAA7', 
        '#A29BFE', '#FD79A8', '#00CEC9', '#FDCB6E'
    ];

    // Função para selecionar uma fatia
    const selectSlice = (index: number) => {
        if (chartData && chartData[index]) {
        const item = chartData[index];
        const percentage = total > 0 ? (item.y / total) * 100 : 0;
        const itemColor = colorScale[index % colorScale.length];
        
        setSelectedSlice({
            label: item.x,
            value: item.y,
            percentage: percentage,
            color: itemColor
        });
        }
    };


    // Estados de carregamento
    if (apiLoading) {
        return (
        <View style={styles.container}>
            <ActivityIndicator size="large" color="#4ECDC4" />
            <Text style={styles.loadingText}>Carregando gastos por categoria...</Text>
        </View>
        );
    }

    if (chartData.length === 0) {
        return (
        <View style={styles.container}>
            <Text style={styles.emptyText}>Nenhum gasto encontrado</Text>
        </View>
        );
    }


     return (
        <View style={styles.container}>
            <Text style={styles.title}>Gastos por Categoria</Text>
      
        <Text style={styles.totalText}>
            Total: R$ {total.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}
        </Text>

        <View style={styles.chartContainer}>
        
            {/* Gráfico */}
            <View style={styles.chartWrapper}>
            <PolarChart
                data={chartData.map((item, index) => ({
                value: item.y,
                label: item.x,
                color: colorScale[index % colorScale.length]
                }))}
                colorKey="color"
                valueKey="value"
                labelKey="label"
            >
                <Pie.Chart innerRadius={80} />
            </PolarChart>
            </View>

            {/* Tooltip */}
            {selectedSlice && (
            <View style={styles.tooltipCentro}>
                <View style={[styles.tooltipColor, { backgroundColor: selectedSlice.color }]} />
                    <Text style={styles.tooltipTitle} numberOfLines={1}>
                        {selectedSlice.label}
                    </Text>
                    <Text style={styles.tooltipTextSmall}>
                        R$ {selectedSlice.value.toFixed(0)}
                    </Text>
                    <Text style={styles.tooltipTextSmall}>
                        {selectedSlice.percentage.toFixed(0)}%
                    </Text>
                </View>
            )}
        </View>

        {/* Instrução */}
        <Text style={styles.instructionText}>
            Toque em um item da legenda para ver detalhes
        </Text>

        {/* Legenda */}
        <View style={styles.legend}>
            {chartData.map((item, index) => {
                const percentage = total > 0 ? (item.y / total) * 100 : 0;
                const itemColor = colorScale[index % colorScale.length];
          
                return (
                    <View 
                    key={index} 
                    style={[
                        styles.legendItem,
                        selectedSlice && item.x === selectedSlice.label && styles.highlightedLegendItem
                    ]}
                    >
                    <View 
                        style={[
                        styles.legendColor, 
                        { backgroundColor: itemColor }
                        ]} 
                    />
                    <Text 
                        style={styles.legendText}
                        onPress={() => selectSlice(index)}
                    >
                        {item.x}: R$ {item.y.toLocaleString('pt-BR', { minimumFractionDigits: 2 })} ({percentage.toFixed(1)}%)
                    </Text>
                    </View>
                );
                })}
        </View>
    </View>
  );
};
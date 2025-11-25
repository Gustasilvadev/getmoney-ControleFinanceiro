import { View,Text, ScrollView } from "react-native";
import {GraficoDonut} from "../../components/GraficoDonut"
import { styles } from "./style";
import { GraficoLinha } from "@/src/components/GraficoLinha";
import { useCallback, useState } from "react";
import { useFocusEffect } from "expo-router";


export const GraficosScreen = () => {

    const [refreshKey, setRefreshKey] = useState(0);

    useFocusEffect(
        useCallback(() => {
            setRefreshKey(prev => prev + 1);
        }, [])
    );

    return(

        <ScrollView
            contentContainerStyle={styles.scrollContainer}
            showsVerticalScrollIndicator={false}>

            <View style={styles.header}> 
                <View style={styles.titleHeader}>
                    <Text style={styles.titleText}>
                        Resumo Financeiro
                    </Text>
                </View>
            </View>

            <View style={styles.containerCharts}>
                <GraficoDonut refreshKey={refreshKey} />
                <GraficoLinha refreshKey={refreshKey} />
            </View>

        </ScrollView>
    );
}
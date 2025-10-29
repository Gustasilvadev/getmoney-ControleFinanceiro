import { View,Text, ScrollView } from "react-native";
import {GraficoDonut} from "../../components/GraficoDonut"
import { styles } from "./style";
import { GraficoLinha } from "@/src/components/GraficoLinha";


export const GraficosScreen = () => {

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
                <GraficoDonut/>

                <GraficoLinha/>
            </View>

        </ScrollView>
    );
}
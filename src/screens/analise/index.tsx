import { View,Text, ScrollView } from "react-native";
import {DonutChart} from "../../components/DonutChart"
import { styles } from "./style";


export const ChartsScreen = () => {

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

            <View>
                <DonutChart/>
            </View>

        </ScrollView>
    )
};
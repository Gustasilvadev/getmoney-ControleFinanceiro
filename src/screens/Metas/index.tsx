import { View, Text, FlatList, TouchableOpacity, ScrollView } from "react-native"
import { styles } from "./style";
import { ProgressoMetaResponse } from "@/src/interfaces/estatistica/response";
import { EstatisticaService } from "@/src/services/api/estatisticas";
import { useNavigation } from "../../constants/router";
import { BotaoSalvar } from "@/src/components/BotaoSalvar";
import { useFocusEffect } from "@react-navigation/native";
import { useCallback, useState } from "react";


export const MetasScreen = () => {
    
    const navigation = useNavigation();
    
    const [progressoDaMeta, setProgressoDaMeta] = useState<ProgressoMetaResponse[]>([]);
    const [carregandoProgresso, setCarregandoProgresso] = useState(true);

    const carregarMetas = async () => {
        try {
            setCarregandoProgresso(true);
            const data = await EstatisticaService.listarProgressoDaMeta();
            setProgressoDaMeta(data);
        } catch (error) {
            setProgressoDaMeta([]);
        } finally {
            setCarregandoProgresso(false);
        }
    };

    useFocusEffect(
        useCallback(() => {
            carregarMetas();
        }, [])
    );
    
    return(
        <ScrollView
        contentContainerStyle={styles.scrollContainer}
        showsVerticalScrollIndicator={false}>

        
            <View style={styles.container}>
                <View style={styles.header}>
                    <View style={styles.titleHeader}>
                        <Text style={styles.titleText}>
                            Historico de Metas
                        </Text>
                    </View>
                </View>

                <View style={styles.containerMetas}>
                    <Text style={styles.subtitle}>Minhas metas</Text>
                    <FlatList
                        data={progressoDaMeta}
                        keyExtractor={(item) => item.metaId.toString()}
                        renderItem={({ item }) => (
                        <View style={styles.cardMeta}>
                            <Text style={styles.cardTitleMeta}>{item.metaNome}</Text>

                            <View style={styles.progressBar}>
                            <View
                                style={[
                                styles.progressFill,
                                { width: `${Math.min(item.percentualConcluido, 100)}%` },
                                ]}>
                            </View>

                            <View style={styles.progressInfo}>

                                <Text style={styles.cardSubTitleMeta}>
                                R${item.valorAtual.toFixed(2)} de R$
                                {item.valorAlvo.toFixed(2)}
                                </Text>

                                <Text style={styles.cardSubTitlePercent}>
                                {Math.min(item.percentualConcluido, 100)}%
                                </Text>

                            </View>
                            </View>
                        </View>
                        )}

                        ListEmptyComponent={
                        <Text style={styles.emptyText}>Nenhuma meta cadastrada</Text>
                        }
                        scrollEnabled={false}
                    />

                    <View style={styles.botao}>
                        <BotaoSalvar
                            onPress={navigation.adicionarMeta}
                            title="Adicionar Meta"
                        />

                    </View>

                </View>

            </View>
        </ScrollView>
          
    );
}
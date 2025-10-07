import { View,Text, FlatList } from "react-native"
import { styles } from "./style";
import { UsuarioService } from "@/src/services/api/usuario";
import { useApi } from "@/src/hooks/useApi";
import { ResumoService } from "@/src/services/api/resumo";
import { ProgressoMetaResponse } from "@/src/interfaces/resumo/response";



export const HomeScreen = () =>{

    const { data: usuario, loading:carregandoUsuario  } = useApi(UsuarioService.listarUsuarioLogado);
    const { data: resumo, loading:carregandoresumo  } = useApi(ResumoService.listarLucro);
    const { data: progressoDaMeta = [], loading: carregandoProgresso } = useApi<ProgressoMetaResponse[]>(ResumoService.listarProgressoDaMeta);


    return(
        <View>

            <View style={styles.container}>
                <Text style={styles.text}>Bem vindo, {usuario?.nome || carregandoUsuario}! </Text>
            </View>

            <View style={styles.cardTop}>
                <Text style={styles.cardText}>Seu Saldo</Text>
                <Text style={styles.cardText}>R${resumo?.lucro || 0}</Text>
            </View>

            <Text style={styles.subtitle}>Minhas metas</Text>

            <FlatList 
                data={progressoDaMeta}
                keyExtractor={(item) => item.metaId.toString()}
                renderItem={({ item }) => (
                    <View style={styles.cardMeta}>

                        <Text style={styles.cardTitleMeta}>{item.metaNome}</Text>

                            <View style={styles.progressBar}>

                                <View style={[styles.progressFill, 
                                    { width: `${item.percentualConcluido}%` }]}>
                                </View>
                                
                                <View style={styles.progressInfo}>

                                    <Text style={styles.cardSubTitleMeta}>
                                        R${item.valorAtual.toFixed(2)} de R${item.valorAlvo.toFixed(2)}
                                    </Text>

                                    <Text style={styles.cardSubTitlePercent}>
                                        {item.percentualConcluido}%
                                    </Text>

                                </View>
                                
                            </View>
                
                    </View>
                )}
                ListEmptyComponent={
                    <Text style={styles.emptyTextMeta}>Nenhuma meta cadastrada</Text>
                }
            />
        </View>
    );
};
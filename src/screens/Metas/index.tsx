import { View, Text, FlatList, ScrollView, Alert } from "react-native"
import { styles } from "./style";
import { ProgressoMetaResponse } from "@/src/interfaces/estatistica/response";
import { EstatisticaService } from "@/src/services/api/estatisticas";
import { useNavigation } from "../../constants/router";
import { BotaoSalvar } from "@/src/components/BotaoSalvar";
import { useFocusEffect } from "@react-navigation/native";
import { useCallback, useState } from "react";
import { Meta } from "@/src/interfaces/meta";
import { Usuario } from "@/src/interfaces/usuario";
import { MetaService } from "@/src/services/api/metas";
import { MetaModalEditar } from "@/src/components/ModalEditarMeta";
import { BotaoDeletarEditar } from "@/src/components/BotaoEditarDeletar";


export const MetasScreen = () => {
    
    const navigation = useNavigation();
    
    const [progressoDaMeta, setProgressoDaMeta] = useState<ProgressoMetaResponse[]>([]);
    const [carregandoProgresso, setCarregandoProgresso] = useState(true);
    const [modalEditarVisible, setModalEditarVisible] = useState(false);
    const [metaSelecionada, setMetaSelecionada] = useState<ProgressoMetaResponse | null>(null);

    const carregarMetas = async () => {
        try {
            setCarregandoProgresso(true);
            const data = await EstatisticaService.listarProgressoDaMeta();
            setProgressoDaMeta(data);
        } catch {
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

    const handleEditarMeta = (metaProgresso: ProgressoMetaResponse) => {
        setMetaSelecionada(metaProgresso);
        setModalEditarVisible(true);
    };

    const handleDeletarMeta = async (metaId: number) => {
        Alert.alert(
            "Confirmar Exclusão",
            "Tem certeza que deseja excluir esta meta?",
            [
                {
                    text: "Cancelar",
                    style: "cancel"
                },
                {
                    text: "Excluir",
                    style: "destructive",
                    onPress: async () => {
                        try {
                            await MetaService.deletarMeta(metaId);
                            await carregarMetas();
                            Alert.alert('Sucesso', 'Meta excluída com sucesso!');
                        } catch {
                            Alert.alert('Erro', 'Não foi possível excluir a meta');
                        }
                    }
                }
            ]
        );
    };

    const handleSalvarEdicao = async (metaEditada: Meta) => {
        try {
            await MetaService.editarPorMetaId(
                metaEditada.id,
                metaEditada.nome,
                metaEditada.valorAlvo,
                metaEditada.data
            );
            
            setModalEditarVisible(false);
            await carregarMetas();
            Alert.alert('Sucesso', 'Meta atualizada com sucesso!');
        } catch {
            Alert.alert('Erro', 'Não foi possível atualizar a meta');
        }
    };

    const handleFecharModal = () => {
        setModalEditarVisible(false);
        setMetaSelecionada(null);
    };

    // Converter ProgressoMetaResponse para Meta
    const converterParaMeta = (metaProgresso: ProgressoMetaResponse): Meta => {
    // Se não temos a data, usar uma data padrão no formato correto
        const dataPadrao = new Date();
        dataPadrao.setMonth(dataPadrao.getMonth() + 1);
        
        const formatarDataParaAPI = (data: Date): string => {
            const ano = data.getFullYear();
            const mes = String(data.getMonth() + 1).padStart(2, '0');
            const dia = String(data.getDate()).padStart(2, '0');
            return `${ano}-${mes}-${dia}`; // Formato YYYY-MM-DD
        };

        return {
            id: metaProgresso.metaId,
            nome: metaProgresso.metaNome,
            valorAlvo: metaProgresso.valorAlvo,
            data: formatarDataParaAPI(dataPadrao), // Data no formato correto
            status: metaProgresso.status,
            usuario: {} as Usuario
        };
    };
    
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
                            {/* Cabeçalho do card com título e botões */}
                            <View style={styles.cardHeader}>
                                <Text style={styles.cardTitleMeta}>{item.metaNome}</Text>
                                <BotaoDeletarEditar
                                    onEdit={() => handleEditarMeta(item)}
                                    onDelete={() => handleDeletarMeta(item.metaId)}
                                />
                            </View>

                            {/* Barra de progresso */}
                            <View style={styles.progressContainer}>
                                <View style={styles.progressBar}>
                                    <View
                                        style={[
                                        styles.progressFill,
                                        { width: `${Math.min(item.percentualConcluido, 100)}%` },
                                        ]}>
                                    </View>
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

             {/* Modal de Edição de Meta */}
            <MetaModalEditar
                visible={modalEditarVisible}
                meta={metaSelecionada ? converterParaMeta(metaSelecionada) : null}
                onClose={handleFecharModal}
                onSave={handleSalvarEdicao}
            />
            
        </ScrollView>
          
    );
}
import { View, Text, Alert } from "react-native"
import { styles } from "./style";
import { InputForm } from "@/src/components/InputForm";
import { BotaoSalvar } from "@/src/components/BotaoSalvar";
import { DataPicker } from "@/src/components/DataPicker";
import { useState } from "react";
import { MetaService } from "@/src/services/api/metas";
import { useNavigation } from "../../constants/router";


export const AdicionarMetaScreen = () => {

    const navigation = useNavigation();
    const [nome, setNome] = useState('');
    const [valorAlvo, setValorAlvo] = useState('');
    const [data, setData] = useState('');
    const [loading, setLoading] = useState(false);


    const handleCriarMeta = async () => {
        // Validações
        if (!nome.trim()) {
            Alert.alert('Erro', 'Por favor, informe o nome da meta');
            return;
        }

        if (!valorAlvo.trim() || isNaN(Number(valorAlvo)) || Number(valorAlvo) <= 0) {
            Alert.alert('Erro', 'Por favor, informe um valor válido para a meta');
            return;
        }

        if (!data.trim()) {
            Alert.alert('Erro', 'Por favor, selecione uma data');
            return;
        }

        setLoading(true);
        try {
            await MetaService.criarMeta(
                nome.trim(),
                Number(valorAlvo),
                data
            );
            
            Alert.alert('Sucesso', 'Meta criada com sucesso!', [
                {
                    text: 'OK',
                    onPress: () => navigation.metas()
                }
            ]);
            
            // Limpa os campos
            setNome('');
            setValorAlvo('');
            setData('');
            
        } catch (error: any) {
            Alert.alert('Erro', error.response?.data?.message || 'Erro ao criar meta. Tente novamente.');
        } finally {
            setLoading(false);
        }
    };
    return(
        <View style={styles.container}>
            <View style={styles.header}>
                <View style={styles.titleHeader}>
                    <Text style={styles.titleText}>
                        Adicionar Meta
                    </Text>
                </View>
            </View>

            <View style={styles.containerForm}>
                <View style={styles.form}>
                    <InputForm
                        label="Nome"
                        placeholder="Nome da meta"
                        iconName="information-circle-outline"
                        value={nome}
                        onChangeText={setNome}
                    />

                    <InputForm
                        label="Valor"
                        placeholder="Valor da meta"
                        iconName="information-circle-outline"
                        value={valorAlvo}
                        onChangeText={setValorAlvo}
                    />

                    <DataPicker
                        label="Data"
                        placeholder="Data da meta"
                        value={data}
                        onChangeText={setData}
                    />
                    
                </View>

                    <BotaoSalvar
                        onPress={handleCriarMeta}
                        title="Adicionar Meta"
                        loading={loading}
                        disabled={loading}
                    />
            
                </View>
            </View>
    );
}
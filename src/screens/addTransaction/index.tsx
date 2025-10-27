import { useState } from 'react';
import { Pressable, View,Text, Keyboard } from "react-native"
import { styles } from "./style"
import { useSelectionCategory } from '@/src/hooks/selectionCategory/useSelectionCategory';
import { CategoriaTipo } from '@/src/enums/categoriaTipo';
import { CategoryModal } from '@/src/components/CategoryModal/CategoryModal';
import { InputField } from '@/src/components/InputForm/inputForm';
import { CategoryTypeSelector } from '@/src/components/CategoryTypeSelector/CategoryType';
import { MetaModal } from "@/src/components/MarkModal";
import { useSelectionMark } from '@/src/hooks/selectionMark/useSelectionMark';
import { useApi } from '@/src/hooks/useApi';
import { TransacaoService } from '@/src/services/api/transacao';
import { SaveButton } from '@/src/components/SaveButton';
import { AuthService } from '@/src/services/api/storage';
import { DatePickerField } from '@/src/components/DataPicker';

export const AddTransaction = () =>{

    const [tipoTransacao, setTipoTransacao] = useState<CategoriaTipo>(CategoriaTipo.RECEITA);
    const [nomeNovaCategoria, setNomeNovaCategoria] = useState('');

    const [valor, setValor] = useState('');
    const [descricao, setDescricao] = useState('');
    const [data, setData] = useState('');
    const [salvando, setSalvando] = useState(false);

    const {
        modalVisible:categoriaModalVisible,
        categoriaSelecionada,
        criandoNova,
        categorias,
        loading:categoriaLoading,
        abrirModal:abrirCategoriaModal,
        fecharModal:fecharCategoriaModal,
        selecionarCategoria,
        iniciarCriacao,
        criarCategoria
    } = useSelectionCategory(tipoTransacao);

    const {
        modalVisible: metaModalVisible,
        metaSelecionada,
        metas,
        loading: metaLoading,
        abrirModal: abrirMetaModal,
        fecharModal: fecharMetaModal,
        selecionarMeta
    } = useSelectionMark();

    const handleCriarCategoria = async () => {
        if (nomeNovaCategoria.trim()) {
            try {
                await criarCategoria(nomeNovaCategoria);
                setNomeNovaCategoria('');
            } catch (error) {
            console.error('Erro ao criar categoria:', error);
            }
        }
    };

    const handleSalvarTransacao = async () => {
        if (!valor || !descricao || !data || !categoriaSelecionada) {
            alert('Preencha todos os campos obrigatórios');
            return;
        }

        try {
            setSalvando(true);
            
            // Chama diretamente a service com os parâmetros corretos
            await TransacaoService.criarTransacao(
                parseFloat(valor.replace(',', '.')),
                descricao,                           
                data,                                
                categoriaSelecionada.id,            
                metaSelecionada?.id ? [metaSelecionada.id] : []
            );
            
            alert('Transação salva com sucesso!');
            
            // Limpa o formulário
            setValor('');
            setDescricao('');
            setData('');
            
        } catch (error) {
            console.error('Erro:', error);
            alert('Erro ao salvar transação');
        } finally {
            setSalvando(false);
        }
    };

    
    return (
        <Pressable style={styles.container} onPress={Keyboard.dismiss}>
            <View style={styles.header}>
                <View style={styles.titleHeader}>
                    <Text style={styles.titleText}>
                        Nova Transação
                    </Text>
                </View>
            </View>

            <View style={styles.containerForm}>
                <View style={styles.form}>
                    <InputField
                        label="Valor"
                        placeholder="Valor da transação"
                        iconName="information-circle-outline"
                        value={valor}
                        onChangeText={setValor}
                    />

                    <InputField
                        label="Descrição"
                        placeholder="Descrição da transação"
                        iconName="information-circle-outline"
                        value={descricao}
                        onChangeText={setDescricao}
                    />

                    <DatePickerField
                        label="Data"
                        placeholder="Data da transação"
                        value={data}
                        onChangeText={setData}
                    />

                    <CategoryTypeSelector
                        tipoTransacao={tipoTransacao}
                        onTipoChange={setTipoTransacao}
                    />

                    <InputField
                        label="Categoria"
                        placeholder="Selecione/crie uma categoria"
                        iconName="chevron-down"
                        value={categoriaSelecionada?.nome}
                        onPress={abrirCategoriaModal}
                        isCombobox={true}
                    />

                    <InputField
                        label="Meta"
                        placeholder="Selecione uma meta"
                        iconName="chevron-down"
                        value={metaSelecionada?.nome}
                        onPress={abrirMetaModal}
                        isCombobox={true}
                    />

                    <SaveButton 
                        onPress={handleSalvarTransacao}
                        loading={salvando}
                        disabled={!valor || !descricao || !data || !categoriaSelecionada}
                    />
                </View>
            </View>

             <CategoryModal
                visible={categoriaModalVisible}
                criandoNova={criandoNova}
                categorias={categorias}
                loading={categoriaLoading}
                nomeNovaCategoria={nomeNovaCategoria}
                onClose={fecharCategoriaModal}
                onCriarCategoria={handleCriarCategoria}
                onIniciarCriacao={iniciarCriacao}
                onSelecionarCategoria={selecionarCategoria}
                onNomeNovaCategoriaChange={setNomeNovaCategoria}
            />

             <MetaModal
                visible={metaModalVisible}
                metas={metas}
                loading={metaLoading}
                metaSelecionada={metaSelecionada}
                onClose={fecharMetaModal}
                onSelecionarMeta={selecionarMeta}
            />
        </Pressable>
  );
}
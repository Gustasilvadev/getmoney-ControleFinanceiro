import { useState } from 'react';
import { Pressable, View,Text, Keyboard } from "react-native"
import { styles } from "./style"
import { useSelecaoCategoria } from '@/src/hooks/SelecaoCategoria/useSelecaoCategoria';
import { CategoriaTipo } from '@/src/enums/categoriaTipo';
import { InputModalCategoria } from '@/src/components/InputModalCategoria';
import { InputForm } from '@/src/components/InputForm';
import { SelecaoTipoCategoria } from '@/src/components/SelecaoTipoCategoria';
import { MetaModal } from "@/src/components/MarkModal";
import { useSelecaoMeta } from '@/src/hooks/SelecaoMeta/useSelecaoMeta';
import { TransacaoService } from '@/src/services/api/transacao';
import { BotaoSalvar } from '@/src/components/BotaoSalvar';
import { DataPicker } from '@/src/components/DataPicker';

export const AdicionarTransacao = () =>{

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
    } = useSelecaoCategoria(tipoTransacao);

    const {
        modalVisible: metaModalVisible,
        metaSelecionada,
        metas,
        loading: metaLoading,
        abrirModal: abrirMetaModal,
        fecharModal: fecharMetaModal,
        selecionarMeta
    } = useSelecaoMeta();

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
                    <InputForm
                        label="Valor"
                        placeholder="Valor da transação"
                        iconName="information-circle-outline"
                        value={valor}
                        onChangeText={setValor}
                    />

                    <InputForm
                        label="Descrição"
                        placeholder="Descrição da transação"
                        iconName="information-circle-outline"
                        value={descricao}
                        onChangeText={setDescricao}
                    />

                    <DataPicker
                        label="Data"
                        placeholder="Data da transação"
                        value={data}
                        onChangeText={setData}
                    />

                    <SelecaoTipoCategoria
                        tipoTransacao={tipoTransacao}
                        onTipoChange={setTipoTransacao}
                    />

                    <InputForm
                        label="Categoria"
                        placeholder="Selecione/crie uma categoria"
                        iconName="chevron-down"
                        value={categoriaSelecionada?.nome}
                        onPress={abrirCategoriaModal}
                        isCombobox={true}
                    />

                    <InputForm
                        label="Meta"
                        placeholder="Selecione uma meta"
                        iconName="chevron-down"
                        value={metaSelecionada?.nome}
                        onPress={abrirMetaModal}
                        isCombobox={true}
                    />

                    <BotaoSalvar 
                        onPress={handleSalvarTransacao}
                        loading={salvando}
                        disabled={!valor || !descricao || !data || !categoriaSelecionada}
                    />
                </View>
            </View>

             <InputModalCategoria
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
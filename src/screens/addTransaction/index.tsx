import { useState } from 'react';
import { Pressable, View,Text, Keyboard } from "react-native"
import { styles } from "./style"
import { useSelectionCategory } from '@/src/hooks/selectionCategory/useSelectionCategory';
import { CategoriaTipo } from '@/src/enums/categoriaTipo';
import { CategoryModal } from '@/src/components/CategoryModal/CategoryModal';
import { InputField } from '@/src/components/InputForm/inputForm';
import { CategoryTypeSelector } from '@/src/components/CategoryTypeSelector/CategoryType';

export const AddTransaction = () =>{

    const [tipoTransacao, setTipoTransacao] = useState<CategoriaTipo>(CategoriaTipo.RECEITA);
    const [nomeNovaCategoria, setNomeNovaCategoria] = useState('');

    const {
        modalVisible,
        categoriaSelecionada,
        criandoNova,
        categorias,
        loading,
        abrirModal,
        fecharModal,
        selecionarCategoria,
        iniciarCriacao,
        criarCategoria
    } = useSelectionCategory(tipoTransacao);

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
                    />

                    <InputField
                        label="Descrição"
                        placeholder="Descrição da transação"
                        iconName="information-circle-outline"
                    />

                    <InputField
                        label="Data"
                        placeholder="Data da transação"
                        iconName="calendar-clear-outline"
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
                        onPress={abrirModal}
                        isCombobox={true}
                    />
                </View>
            </View>

            <CategoryModal
                visible={modalVisible}
                criandoNova={criandoNova}
                categorias={categorias}
                loading={loading}
                nomeNovaCategoria={nomeNovaCategoria}
                onClose={fecharModal}
                onCriarCategoria={handleCriarCategoria}
                onIniciarCriacao={iniciarCriacao}
                onSelecionarCategoria={selecionarCategoria}
                onNomeNovaCategoriaChange={setNomeNovaCategoria}
            />
        </Pressable>
  );
};
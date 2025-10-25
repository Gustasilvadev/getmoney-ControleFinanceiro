import { useState } from 'react';

import Icon from 'react-native-vector-icons/Ionicons';
import { Pressable, View,Text, Keyboard, TextInput, Modal, FlatList, ActivityIndicator } from "react-native"
import { styles } from "./style"
import { useSelectionCategory } from '@/src/hooks/selectionCategory/useSelectionCategory';
import { CategoriaTipo } from '@/src/enums/categoriaTipo';

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

                    <View style={styles.inputContainer}>
                        <Text style={styles.label}>Valor</Text>
                        <TextInput
                            style={styles.input}
                            placeholder="Valor da transação"
                            placeholderTextColor="#858587"
                        />
                        <Icon 
                            name="information-circle-outline" 
                            size={25} 
                            color="#136F6C" 
                            style={styles.iconRight} 
                        />
                        <View style={styles.underline} />
                    </View>

                    <View style={styles.inputContainer}>
                        <Text style={styles.label}>Descrição</Text>
                        <TextInput
                            style={styles.input}
                            placeholder="Descrição da transação"
                            placeholderTextColor="#858587"
                        />
                        <Icon
                            name="information-circle-outline"
                            size={25}
                            color="#136F6C"
                            style={styles.iconRight} 
                        />
                        <View style={styles.underline} />
                    </View>

                    <View style={styles.inputContainer}>
                        <Text style={styles.label}>Data</Text>
                        <TextInput
                            style={styles.input}
                            placeholder="Data da transação"
                            placeholderTextColor="#858587"
                        />
                        <Icon
                            name="calendar-clear-outline"
                            size={25}
                            color="#136F6C"
                            style={styles.iconRight}
                        />
                        <View style={styles.underline} />
                    </View>

                    <Text style={styles.sectionTitle}>Tipo de Transação</Text>
                    <View style={styles.checkboxGroup}>
                        <Pressable 
                            style={[
                                styles.checkboxItem, 
                                tipoTransacao === CategoriaTipo.RECEITA && styles.checkboxItemSelected
                            ]}
                            onPress={() => setTipoTransacao(CategoriaTipo.RECEITA)}
                            >
                            <View style={[
                                styles.checkboxCircle,
                                tipoTransacao === CategoriaTipo.RECEITA && styles.checkboxCircleSelected
                                ]}>
                                    <Icon 
                                        name="checkmark" 
                                        size={16} 
                                        color="#FFFFFF" 
                                        style={tipoTransacao === CategoriaTipo.RECEITA ? styles.checkIconSelected : styles.checkIcon}
                                    />
                            </View>
                            <Text style={styles.checkboxText}>Receita</Text>
                        </Pressable>

                        <Pressable 
                            style={[
                                styles.checkboxItem, 
                                tipoTransacao === CategoriaTipo.DESPESA && styles.checkboxItemSelected
                            ]}
                            onPress={() => setTipoTransacao(CategoriaTipo.DESPESA)}
                            >
                            <View style={[
                                styles.checkboxCircle,
                                tipoTransacao === CategoriaTipo.DESPESA && styles.checkboxCircleSelected
                            ]}>
                                <Icon 
                                    name="checkmark" 
                                    size={16} 
                                    color="#FFFFFF" 
                                    style={tipoTransacao === CategoriaTipo.DESPESA ? styles.checkIconSelected : styles.checkIcon}
                                />
                            </View>
                            <Text style={styles.checkboxText}>Despesa</Text>
                        </Pressable>
                    </View>

                    <View style={styles.inputContainer}>
                        <Text style={styles.label}>Categoria</Text>
                        
                        <Pressable style={styles.comboboxWrapper} onPress={abrirModal}>
                            <Text style={styles.input}>
                                {categoriaSelecionada?.nome || "Selecione/crie uma categoria"}
                            </Text>
                            <Icon 
                                name="chevron-down" 
                                size={20} 
                                color="#136F6C" 
                                style={styles.dropdownIcon} 
                            />
                        </Pressable>
                        
                        <View style={styles.underline} />
                    </View>


                    {/* Input de associar a uma meta criada */}


                </View>
            </View>

            <Modal
                visible={modalVisible}
                animationType="slide"
                transparent={true}
                onRequestClose={fecharModal}
            >
                <View style={styles.modalOverlay}>
                    <View style={styles.modalContent}>

                        <Text style={styles.modalTitle}>
                            {criandoNova ? 'Nova Categoria' : 'Selecionar Categoria'}
                        </Text>

                        {criandoNova ? (
                            // Modo de criação
                            <View style={styles.modalBody}>
                                <TextInput
                                    style={styles.modalInput}
                                    placeholder="Digite o nome da categoria"
                                    value={nomeNovaCategoria}
                                    onChangeText={setNomeNovaCategoria}
                                    autoFocus={true}
                                    onSubmitEditing={handleCriarCategoria}
                                />
                                <View style={styles.modalButtons}>
                                    <Pressable 
                                        style={styles.modalButton}
                                        onPress={fecharModal}
                                    >
                                        <Text style={styles.modalButtonText}>Cancelar</Text>
                                    </Pressable>
                                    <Pressable 
                                        style={[styles.modalButton, styles.primaryButton]}
                                        onPress={handleCriarCategoria}
                                        disabled={!nomeNovaCategoria.trim() || loading}
                                    >
                                        <Text style={styles.primaryButtonText}>
                                            {loading ? 'Criando...' : 'Criar'}
                                        </Text>
                                    </Pressable>
                                </View>
                            </View>
                        ) : (
                            // Modo de seleção
                            <View style={styles.modalBody}>
                                <Pressable 
                                    style={styles.novaCategoriaButton}
                                    onPress={iniciarCriacao}
                                >
                                    <Icon name="add-circle" size={20} color="#136F6C" />
                                    <Text style={styles.novaCategoriaText}>Nova Categoria</Text>
                                </Pressable>

                                <Text style={styles.categoriasTitle}>
                                    Categorias ({categorias.length})
                                </Text>

                                <View style={styles.categoriasList}>
                                    {categorias.map((item) => (
                                        <Pressable
                                            key={item.id}
                                            style={styles.categoriaItem}
                                            onPress={() => selecionarCategoria(item)}
                                        >
                                            <Text style={styles.categoriaText}>{item.nome}</Text>
                                            <Icon name="chevron-forward" size={16} color="#858587" />
                                        </Pressable>
                                    ))}
                                </View>

                                <Pressable 
                                    style={[styles.modalButton, styles.closeButton, {marginTop: 'auto'}]}
                                    onPress={fecharModal}
                                >
                                    <Text style={styles.modalButtonText}>Fechar</Text>
                                </Pressable>
                            </View>
                        )}
                    </View>
                </View>
            </Modal>
        </Pressable>
    );
};
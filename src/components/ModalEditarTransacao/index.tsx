import { Modal, ScrollView, TouchableOpacity, View, Text, Alert } from "react-native";
import { BotaoSalvar } from "../BotaoSalvar";
import { Transacao } from "@/src/interfaces/transacao";
import { useEffect, useState } from "react";
import { CategoriaTipo } from "@/src/enums/categoriaTipo";
import { CategoriaService } from "@/src/services/api/categoria";
import { MetaService } from "@/src/services/api/metas";
import { TransacaoService } from "@/src/services/api/transacao";
import { styles } from "./style";
import { InputForm } from "../InputForm";
import { Picker } from "@react-native-picker/picker";
import { DataPicker } from "../DataPicker";

interface ModalEditarTransacaoProps {
  visible: boolean;
  transacao: Transacao | null;
  onClose: () => void;
  onSuccess: () => void;
}

export function ModalEditarTransacao({ 
  visible, 
  transacao, 
  onClose, 
  onSuccess 
}: ModalEditarTransacaoProps) {
  const [valor, setValor] = useState('');
  const [descricao, setDescricao] = useState('');
  const [data, setData] = useState('');
  const [tipoTransacao, setTipoTransacao] = useState<CategoriaTipo>(CategoriaTipo.DESPESA);
  const [categoriaId, setCategoriaId] = useState<number>(0);
  const [metaIds, setMetaIds] = useState<number[]>([]);
  
  const [categorias, setCategorias] = useState<any[]>([]);
  const [metas, setMetas] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [carregandoDados, setCarregandoDados] = useState(false);

  // Carrega categorias e metas
  useEffect(() => {
    if (visible) {
      carregarDados();
    }
  }, [visible]);

  // Preenche os dados quando a transação muda
  useEffect(() => {
    if (transacao) {
      setValor(transacao.valor.toString());
      setDescricao(transacao.descricao);
      setData(transacao.data.split('T')[0]); // Formata a data para YYYY-MM-DD
      setTipoTransacao(transacao.categoria.tipo);
      setCategoriaId(transacao.categoria.id);
      setMetaIds(transacao.metas.map(meta => meta.id));
    }
  }, [transacao]);

  const carregarDados = async () => {
    try {
      setCarregandoDados(true);
      const [categoriasData, metasData] = await Promise.all([
        CategoriaService.listarCategoria(),
        MetaService.listarMetas()
      ]);
      setCategorias(categoriasData || []);
      setMetas(metasData || []);
    } catch (error) {
      console.error('Erro ao carregar dados:', error);
      Alert.alert('Erro', 'Não foi possível carregar categorias e metas');
    } finally {
      setCarregandoDados(false);
    }
  };

  const filtrarCategoriasPorTipo = () => {
    return categorias.filter(cat => cat.tipo === tipoTransacao);
  };

  const handleSalvar = async () => {
    if (!transacao) return;

    // Validações
    if (!valor || !descricao || !data || !categoriaId) {
      Alert.alert('Atenção', 'Preencha todos os campos obrigatórios');
      return;
    }

    if (parseFloat(valor) <= 0) {
      Alert.alert('Atenção', 'O valor deve ser maior que zero');
      return;
    }

    try {
      setLoading(true);
      
      await TransacaoService.editarPorTransacaoId(
        transacao.id,
        parseFloat(valor),
        descricao,
        data,
        1, // ou o status atual da transação
        categoriaId,
        metaIds
      );

      Alert.alert('Sucesso', 'Transação atualizada com sucesso!');
      onSuccess();
      onClose();
    } catch (error) {
      console.error('Erro ao editar transação:', error);
      Alert.alert('Erro', 'Não foi possível atualizar a transação');
    } finally {
      setLoading(false);
    }
  };

  const handleSelecionarMeta = (metaId: number) => {
    setMetaIds(prev => {
      if (prev.includes(metaId)) {
        return prev.filter(id => id !== metaId);
      } else {
        return [...prev, metaId];
      }
    });
  };

  const formatarData = (text: string) => {
    // Formatação simples para data (DD/MM/YYYY)
    let cleaned = text.replace(/\D/g, '');
    if (cleaned.length > 8) cleaned = cleaned.substring(0, 8);
    
    if (cleaned.length >= 5) {
      cleaned = cleaned.substring(0, 2) + '/' + cleaned.substring(2, 4) + '/' + cleaned.substring(4);
    } else if (cleaned.length >= 3) {
      cleaned = cleaned.substring(0, 2) + '/' + cleaned.substring(2);
    }
    
    return cleaned;
  };

  if (!transacao) return null;

  return (
    <Modal
      visible={visible}
      animationType="slide"
      transparent={true}
      onRequestClose={onClose}
    >
      <View style={styles.modalOverlay}>
        <View style={styles.modalContainer}>
          <View style={styles.modalHeader}>
            <Text style={styles.modalTitle}>Editar Transação</Text>
            <TouchableOpacity onPress={onClose} style={styles.closeButton}>
              <Text style={styles.closeButtonText}>X</Text>
            </TouchableOpacity>
          </View>

          <ScrollView style={styles.modalContent}>
            {/* Valor */}
            <InputForm
              label="Valor"
              placeholder="0,00"
              value={valor}
              onChangeText={setValor}
              iconName="cash-outline"
            />

            {/* Descrição */}
            <InputForm
              label="Descrição"
              placeholder="Digite a descrição"
              value={descricao}
              onChangeText={setDescricao}
              iconName="information-circle-outline"
            />

            {/* Data */}
            <DataPicker
              label="Data"
              placeholder="DD/MM/AAAA"
              value={data}
              onChangeText={setData}
            />

            {/* Tipo de Transação */}
            <View style={styles.section}>
              <Text style={styles.sectionTitle}>Tipo de transação</Text>
              <View style={styles.radioGroup}>
                <TouchableOpacity
                  style={[
                    styles.radioButton,
                    tipoTransacao === CategoriaTipo.DESPESA && styles.radioButtonSelected
                  ]}
                  onPress={() => setTipoTransacao(CategoriaTipo.DESPESA)}
                >
                  <Text style={[
                    styles.radioText,
                    tipoTransacao === CategoriaTipo.DESPESA && styles.radioTextSelected
                  ]}>
                    Despesa
                  </Text>
                </TouchableOpacity>
                
                <TouchableOpacity
                  style={[
                    styles.radioButton,
                    tipoTransacao === CategoriaTipo.RECEITA && styles.radioButtonSelected
                  ]}
                  onPress={() => setTipoTransacao(CategoriaTipo.RECEITA)}
                >
                  <Text style={[
                    styles.radioText,
                    tipoTransacao === CategoriaTipo.RECEITA && styles.radioTextSelected
                  ]}>
                    Receita
                  </Text>
                </TouchableOpacity>
              </View>
            </View>

            {/* Categoria */}
            <View style={styles.section}>
              <Text style={styles.sectionTitle}>Categoria</Text>
              <View style={styles.pickerContainer}>
                <Picker
                  selectedValue={categoriaId}
                  onValueChange={setCategoriaId}
                  style={styles.picker}
                >
                  <Picker.Item label="Selecione uma categoria" value={0} />
                  {filtrarCategoriasPorTipo().map(categoria => (
                    <Picker.Item 
                      key={categoria.id} 
                      label={categoria.nome} 
                      value={categoria.id} 
                    />
                  ))}
                </Picker>
              </View>
            </View>

            {/* Metas */}
            <View style={styles.section}>
              <Text style={styles.sectionTitle}>Vincular a Metas</Text>
              {metas.map(meta => (
                <TouchableOpacity
                  key={meta.id}
                  style={[
                    styles.metaItem,
                    metaIds.includes(meta.id) && styles.metaItemSelected
                  ]}
                  onPress={() => handleSelecionarMeta(meta.id)}
                >
                  <Text style={[
                    styles.metaText,
                    metaIds.includes(meta.id) && styles.metaTextSelected
                  ]}>
                    {meta.nome}
                  </Text>
                </TouchableOpacity>
              ))}
            </View>
          </ScrollView>

          <View style={styles.modalFooter}>
            <TouchableOpacity 
              style={styles.cancelButton} 
              onPress={onClose}
            >
              <Text style={styles.cancelButtonText}>Cancelar</Text>
            </TouchableOpacity>
            
            <BotaoSalvar
              onPress={handleSalvar}
              loading={loading}
              title="Salvar"
            />
          </View>
        </View>
      </View>
    </Modal>
  );
}
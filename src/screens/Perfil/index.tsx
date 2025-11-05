
import { Pressable, View, Text, Keyboard, Alert, TouchableOpacity } from "react-native"
import { styles } from "./style"
import { InputForm } from "@/src/components/InputForm"
import { useEffect, useState } from "react";
import { UsuarioService } from "@/src/services/api/usuario";
import { useApi } from "@/src/hooks/useApi";


export const PerfilScreen = () => {
    
    const { data: usuario, loading: carregandoUsuario } = useApi(UsuarioService.listarUsuarioLogado);
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senhaAtual, setSenhaAtual] = useState('');
    const [novaSenha, setNovaSenha] = useState('');
    const [salvandoDados, setSalvandoDados] = useState(false);
    const [salvandoSenha, setSalvandoSenha] = useState(false);

    useEffect(() => {
        if (usuario) {
            setNome(usuario.nome || '');
            setEmail(usuario.email || '');
        }
    }, [usuario]);

    const handleSalvarDados = async () => {
        if (!nome.trim()) {
            Alert.alert('Erro', 'Preencha nome');
            return;
        }

        if (!usuario) {
            Alert.alert('Erro', 'Usuário não encontrado');
            return;
        }

        setSalvandoDados(true);
        try {
            await UsuarioService.editarPorUsuarioId(usuario.id, nome, email);
            Alert.alert('Sucesso', 'Dados atualizados com sucesso!');
        } catch {
            Alert.alert('Erro', 'Não foi possível atualizar os dados');
        } finally {
            setSalvandoDados(false);
        }
    };

    const handleAlterarSenha = async () => {
        if (novaSenha.trim().length < 8) {
            Alert.alert("Erro", "A nova senha deve conter pelo menos 8 caracteres.");
            return;
        }
        if (!senhaAtual.trim() || !novaSenha.trim()) {
            Alert.alert('Erro', 'Preencha a senha atual e a nova senha');
            return;
        }

        setSalvandoSenha(true);
        try {
            await UsuarioService.alterarSenhaUsuario(senhaAtual, novaSenha);
            Alert.alert('Sucesso', 'Senha alterada com sucesso!');
            setSenhaAtual('');
            setNovaSenha('');
        } catch (error: any) {
            if (error.response?.status === 403) {
                Alert.alert('Acesso Negado', 
                    'Você não tem permissão para alterar a senha. \nPossíveis causas:\n• Sessão expirada\n• Problema no servidor');
            } else {
                Alert.alert('Erro', 'Não foi possível alterar a senha');
            }
        } finally {
        setSalvandoSenha(false);
         }
    };

    return(
        <Pressable style={styles.container} onPress={Keyboard.dismiss}>
            <View style={styles.header}>
                <View style={styles.titleHeader}>
                    <Text style={styles.titleText}>
                        Perfil
                    </Text>
                </View>
            </View>

            <View style={styles.containerForm}>
                <View style={styles.form}>

                    <InputForm
                    label="Nome"
                    placeholder="Digite seu nome"
                    iconName="person-outline"
                    value={nome}
                    onChangeText={setNome}
                    />

                    <TouchableOpacity 
                        style={styles.botaoSalvar}
                        onPress={handleSalvarDados}
                        disabled={salvandoDados}
                    >
                        <Text style={styles.textoBotao}>
                            {salvandoDados ? "Salvando..." : "Salvar Dados"}
                        </Text>
                    </TouchableOpacity>

                    <View style={styles.separador} />

                        <InputForm
                            label="Senha Atual"
                            placeholder="Digite sua senha atual"
                            iconName="lock-closed-outline"
                            value={senhaAtual}
                            onChangeText={setSenhaAtual}
                        />

                        <InputForm
                            label="Nova Senha"
                            placeholder="Digite a nova senha"
                            iconName="lock-closed-outline"
                            value={novaSenha}
                            onChangeText={setNovaSenha}
                        />

                        {/* Botão para alterar senha */}
                        <TouchableOpacity
                            style={[
                                styles.botaoSenha,
                                (
                                    !senhaAtual.trim() ||
                                    !novaSenha.trim() ||
                                    novaSenha.trim().length < 8
                                ) && styles.botaoDesabilitado
                            ]}
                            onPress={handleAlterarSenha}
                            disabled={
                                !senhaAtual.trim() ||
                                !novaSenha.trim() ||
                                novaSenha.trim().length < 8 ||
                                salvandoSenha
                            }
                        >
                            <Text style={styles.textoBotao}>
                                {salvandoSenha ? "Alterando..." : "Alterar Senha"}
                            </Text>
                        </TouchableOpacity>

                </View>
            </View>
            
        </Pressable>
    );
}
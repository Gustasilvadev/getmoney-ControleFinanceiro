import { useState } from "react";
import { LogoTitle } from "@/src/components/LogoTitle";
import { View,Text,TextInput,TouchableOpacity,Keyboard, Pressable } from "react-native";
import {styles} from "./style"
import { useNavigation } from "@/src/constants/router";
import { registerService } from "@/src/services/api/auth/register";
import { useFormRegister } from "@/src/hooks/formRegister";
import { Alert } from "@/src/components/Alert";


export const RegisterScreen = () =>{

    const navigation = useNavigation();

    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const { errors, validate, clearError } = useFormRegister();
    const [loading, setLoading] = useState(false);

    const [alertVisible, setAlertVisible] = useState(false);
    const [alertTitle, setAlertTitle] = useState('');
    const [alertMessage, setAlertMessage] = useState('');

    const mostrarAlerta = (titulo: string, mensagem: string) => {
        setAlertTitle(titulo);
        setAlertMessage(mensagem);
        setAlertVisible(true);
    };

    const handleRegister = async () => {
        if (!validate(nome, email, senha)) return;

        setLoading(true);
        try {
            await registerService.register(nome, email, senha);
            mostrarAlerta("Sucesso", "Conta criada com sucesso!");
            setTimeout(() => {
                navigation.login();
            }, 1000);
        } catch (error: any) {
            console.error('Erro no registro:', error);
            
            if (error.response?.status === 400) {
                mostrarAlerta("Erro", "Dados inválidos. Verifique as informações.");
            } else if (error.response?.status === 409) {
                mostrarAlerta("Atenção", "Este e-mail já está em uso.");
            } else if (error.message?.includes('Network Error')) {
                mostrarAlerta("Erro", "Sem conexão com a internet.");
            } else {
                mostrarAlerta("Erro", error.message || 'Erro ao criar usuário');
            }
        } finally {
            setLoading(false);
        }
    };

    return(
        <Pressable style={styles.container} onPress={Keyboard.dismiss}>
            <LogoTitle/>
        
            <View style={styles.form}>

                <Text style={styles.title}>Crie Sua Conta</Text>   
                   
                <View style={styles.formInput}>

                    <View style={styles.inputContainer}>
                        {errors.nome ? <Text style={styles.errorText}>{errors.nome}</Text> : null}
                        <TextInput 
                        style={[styles.input, errors.nome && styles.inputError]} 
                        placeholder="Nome"
                        value={nome}
                        onChangeText={(text) => {
                                    setNome(text);
                                    clearError('nome');
                                }}
                        placeholderTextColor='#858587'
                        keyboardType="default"
                        autoCapitalize="none"></TextInput>
                    </View>

                    <View style={styles.inputContainer}>
                        {errors.email ? <Text style={styles.errorText}>{errors.email}</Text> : null}
                        <TextInput style={[styles.input,errors.email && styles.inputError]}
                        placeholder="Email"
                        value={email}
                        onChangeText={(text) => {
                                    setEmail(text);
                                    clearError('email');
                                }}
                        placeholderTextColor='#858587'
                        keyboardType="email-address"></TextInput>
                    </View>
        
                    <View style={styles.inputContainer}>
                        {errors.senha ? <Text style={styles.errorText}>{errors.senha}</Text> : null}
                        <TextInput style={[styles.input, errors.senha && styles.inputError]}
                        placeholder="Senha"
                        value={senha}
                        onChangeText={(text) => {
                                    setSenha(text);
                                    clearError('senha');
                                }}
                        placeholderTextColor='#858587'
                        keyboardType="default"
                        secureTextEntry></TextInput>
                    </View>


                    <View style={styles.options}>

                        <Text style={styles.textOptions} onPress={navigation.login}>Ja tenho conta</Text>
                    
                        <TouchableOpacity style={styles.button} onPress={handleRegister}>
                            <Text style={styles.buttonText}>Criar</Text>
                        </TouchableOpacity>

                    </View>
                </View>
            </View>

            {/* Alert Personalizado */}
            <Alert
                visible={alertVisible}
                title={alertTitle}
                message={alertMessage}
                onClose={() => setAlertVisible(false)}
                confirmText="OK"
            />
        </Pressable>
    );
}
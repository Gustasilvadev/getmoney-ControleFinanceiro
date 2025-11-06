import React, { useState } from 'react';
import { View,Text, TextInput,TouchableOpacity,Keyboard, Pressable} from "react-native";
import { LogoTitle } from "@/src/components/LogoTitle";
import {styles} from "./style"
import { useNavigation } from "@/src/constants/router";
import { LoginService } from "@/src/services/api/auth/login";
import { useFormLogin } from '@/src/hooks/formLogin';
import { Alert} from "@/src/components/Alert"


export const LoginScreen = () => {

    const navigation = useNavigation();
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const { errors, validate, clearError, setInvalidCredentialsError  } = useFormLogin();

    const [alertVisible, setAlertVisible] = useState(false);
    const [alertTitle, setAlertTitle] = useState('');
    const [alertMessage, setAlertMessage] = useState('');

    const [loading, setLoading] = useState(false);

    const mostrarAlerta = (titulo: string, mensagem: string) => {
        setAlertTitle(titulo);
        setAlertMessage(mensagem);
        setAlertVisible(true);
    };

    const handleLogin = async () => {
        if (!validate(email, senha)) return;

        try {
            setLoading(true);
            await LoginService.login(email, senha);
            navigation.home();
        } catch (error: any) {
            // Se o login falhou no servidor, mostra erro de credenciais
            if (error.response?.status === 401 || error.response?.status === 400) {
                setInvalidCredentialsError();
            } else {
                mostrarAlerta("Erro", "Não foi possível fazer login. Tente novamente.");
            }
        } finally {
            setLoading(false);
        }
    };

    return(

        <Pressable style={styles.container} onPress={Keyboard.dismiss}>
            <LogoTitle/>

            <View style={styles.form}>

                <Text style={styles.title}>Login</Text>      

                <View style={styles.formInput}>

                    <View style={styles.inputContainer}>
                        {errors.email ? <Text style={styles.errorText}>{errors.email}</Text> : null}
                        <TextInput 
                        style={[styles.input, errors.email && styles.inputError]}
                        placeholder="Email"
                        value={email}
                        onChangeText={(text) => {
                                    setEmail(text);
                                    clearError('email');
                                }}
                        placeholderTextColor='#858587'
                        keyboardType="email-address"
                        autoCapitalize="none"></TextInput>
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
                            secureTextEntry>

                        </TextInput>

                    </View>
                    

                    <View style={styles.options}>

                        <Text style={styles.textOptions} onPress={navigation.register}>Não tenho conta</Text>
                    
                        <TouchableOpacity style={styles.button} onPress={handleLogin}>
                            <Text style={styles.buttonText}>Entrar</Text>
                        </TouchableOpacity>

                    </View>
                </View>
            </View>

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
import React, { useState } from 'react';
import { View,Text, TextInput,TouchableOpacity,Keyboard, Pressable, Alert} from "react-native";
import { LogoTitle } from "@/src/components/LogoTitle";
import {styles} from "./style"
import { useNavigation } from "@/src/constants/router";
import { loginService } from "@/src/services/api/auth/login";


export const LoginScreen = () => {

    const navigation = useNavigation();
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');


    const handleLogin = async () => {
        try {
            const response = await loginService.login(email, senha);
            Alert.alert('Sucesso', `Bem-vindo ${response.token}!`);
            // Navega para a tela principal após login
            // navigation.home();

        } catch (error: any) {
            alert(error.message || 'Erro no login');
        }
    };

    return(


        <Pressable style={styles.container} onPress={Keyboard.dismiss}>
            <LogoTitle/>

            <View style={styles.form}>

                <Text style={styles.title}>Login</Text>      

                <View style={styles.formInput}>

                    <TextInput style={styles.input}
                    placeholder="Email"
                    value={email}
                    onChangeText={setEmail}
                    placeholderTextColor='#858587'
                    keyboardType="email-address"
                    autoCapitalize="none"></TextInput>

                    <TextInput style={styles.input}
                    placeholder="Senha"
                    value={senha}
                    onChangeText={setSenha}
                    placeholderTextColor='#858587'
                    keyboardType="default"
                    secureTextEntry></TextInput>

                    <View style={styles.options}>

                        <Text style={styles.textOptions} onPress={navigation.register}>Não tenho conta</Text>
                    
                        <TouchableOpacity style={styles.button} onPress={handleLogin}>
                            <Text style={styles.buttonText}>Entrar</Text>
                        </TouchableOpacity>

                    </View>
                </View>
            </View>
        </Pressable>
    );
};
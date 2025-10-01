import { useState } from "react";
import { LogoTitle } from "@/src/components/LogoTitle";
import { View,Text,TextInput,TouchableOpacity,Keyboard, Pressable, Alert } from "react-native";
import {styles} from "./style"
import { useNavigation } from "@/src/constants/router";
import { registerService } from "@/src/services/api/auth/register";



export const RegisterScreen = () =>{
    const navigation = useNavigation();
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');

    const handleRegister = async () => {
        try {
            await registerService.register(nome,email, senha);
            Alert.alert('Sucesso', `Bem-vindo!`);
            // Navega para a tela principal após criar o usuario
            navigation.login();
    
        } catch (error: any) {
            alert(error.message || 'Erro ao criar usuario');
        }
    };

    return(
        <Pressable style={styles.container} onPress={Keyboard.dismiss}>
            <LogoTitle/>
        
            <View style={styles.form}>

                <Text style={styles.title}>Crie Sua Conta</Text>   
                   
                <View style={styles.formInput}>

                    <TextInput style={styles.input}
                    placeholder="Nome"
                    value={nome}
                    onChangeText={setNome}
                    placeholderTextColor='#858587'
                    keyboardType="default"></TextInput>

                    <TextInput style={styles.input}
                    placeholder="Email"
                    value={email}
                    onChangeText={setEmail}
                    placeholderTextColor='#858587'
                    keyboardType="email-address"></TextInput>
        
                    <TextInput style={styles.input}
                    placeholder="Senha"
                    value={senha}
                    onChangeText={setSenha}
                    placeholderTextColor='#858587'
                    keyboardType="default"
                    secureTextEntry></TextInput>


                    <View style={styles.options}>

                        <Text style={styles.textOptions} onPress={navigation.login}>Ja tenho conta</Text>
                    
                        <TouchableOpacity style={styles.button} onPress={handleRegister}>
                            <Text style={styles.buttonText}>Criar</Text>
                        </TouchableOpacity>

                    </View>
                </View>
            </View>
        </Pressable>
    );
};
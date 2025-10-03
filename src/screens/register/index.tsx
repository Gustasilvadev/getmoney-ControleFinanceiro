import { useState } from "react";
import { LogoTitle } from "@/src/components/LogoTitle";
import { View,Text,TextInput,TouchableOpacity,Keyboard, Pressable, Alert } from "react-native";
import {styles} from "./style"
import { useNavigation } from "@/src/constants/router";
import { registerService } from "@/src/services/api/auth/register";
import { useFormRegister } from "@/src/hooks/formRegister";


export const RegisterScreen = () =>{

    const navigation = useNavigation();

    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const { errors, validate, clearError } = useFormRegister();

    const handleRegister = async () => {
         if (!validate(nome,email, senha)) return;

        try {
            await registerService.register(nome,email, senha);
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
        </Pressable>
    );
};
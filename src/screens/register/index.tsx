import { LogoTitle } from "@/src/components/LogoTitle";
import { View,Text,TextInput,TouchableOpacity } from "react-native";
import {styles} from "./style"
import { useNavigation } from "@/src/constants/router";


export const RegisterScreen = () =>{
     const { login } = useNavigation();

    return(
        <View style={styles.container}>
            <LogoTitle/>
        
            <View style={styles.form}>

                <Text style={styles.title}>Crie Sua Conta</Text>   
                   
                <View style={styles.formInput}>

                    <TextInput style={styles.input}
                    placeholder="Nome"
                    placeholderTextColor='#858587'
                    keyboardType="default"></TextInput>

                    <TextInput style={styles.input}
                    placeholder="Email"
                    placeholderTextColor='#858587'
                    keyboardType="email-address"></TextInput>
        
                    <TextInput style={styles.input}
                    placeholder="Senha"
                    placeholderTextColor='#858587'
                    keyboardType="default"></TextInput>


                    <View style={styles.options}>

                        <Text style={styles.textOptions} onPress={login}>Ja tenho conta</Text>
                    
                        <TouchableOpacity style={styles.button}>
                            <Text style={styles.buttonText}>Criar</Text>
                        </TouchableOpacity>

                    </View>
                </View>
            </View>
        </View>
    );
};
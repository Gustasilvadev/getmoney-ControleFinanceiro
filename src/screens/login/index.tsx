import { View,Text, TextInput,TouchableOpacity} from "react-native";
import { LogoTitle } from "@/src/components/LogoTitle";
import {styles} from "./style"


export const LoginScreen = () => {
    return(
        <View style={styles.container}>
            <LogoTitle/>

            <View style={styles.form}>
                <Text style={styles.title}>Login</Text>      

                <View style={styles.formInput}>
                     <TextInput style={styles.input}
                    placeholder="Email"
                    placeholderTextColor='#858587'></TextInput>

                    <TextInput style={styles.input}
                    placeholder="Senha"
                    placeholderTextColor='#858587'></TextInput>
                </View>
               

                <View>
                    <Text style={styles.textOptions}>Não tenho conta</Text>
                </View>

                <TouchableOpacity style={styles.button}>Entrar</TouchableOpacity>
            </View>
 
        </View>
    );
};
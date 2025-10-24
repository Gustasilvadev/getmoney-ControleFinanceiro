import { Pressable, View,Text, Keyboard, TextInput } from "react-native"
import { styles } from "./style"


export const AddTransaction = () =>{
    return(

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

                    <TextInput
                        style={styles.input}
                        placeholder="Teste"
                    />

                </View>

            </View>



        </Pressable>
    )
}
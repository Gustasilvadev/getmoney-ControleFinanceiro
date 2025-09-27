import { View, Image,Text} from "react-native";
import { LogoTitle } from "@/src/components/LogoTitle";
import {styles} from "./style"

export const LoginScreen = () => {
    return(
        <View style={styles.container}>
            <LogoTitle/>
        </View>
    );
};
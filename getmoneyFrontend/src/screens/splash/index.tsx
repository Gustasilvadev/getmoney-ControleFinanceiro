import { View,Text } from "react-native";
import { styles } from "./style";

export const SplashScreen = () =>{
    return(

        <View style={styles.container} >
            <Text style={styles.titulo}
                numberOfLines={1}
                adjustsFontSizeToFit
                minimumFontScale={0.5}
            >
            GetMoney
            </Text>
        </View>

    );
}


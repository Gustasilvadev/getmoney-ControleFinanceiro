import { View,Text } from "react-native"
import { styles } from "./style";
import { UsuarioService } from "@/src/services/api/usuario";
import { useApi } from "@/src/hooks/useApi";
import { resumoService } from "@/src/services/api/resumo";


export const HomeScreen = () =>{

    const { data: usuario, loading:carregandoUsuario  } = useApi(UsuarioService.listarUsuarioLogado);
    const { data: resumo, loading:carregandoresumo  } = useApi(resumoService.listarLucro);

    return(
        <View>

            <View style={styles.container}>
                <Text style={styles.text}>Bem vindo, {usuario?.nome || carregandoUsuario}! </Text>
            </View>

            <View style={styles.cardTop}>
                <Text style={styles.cardText}>Seu Saldo</Text>
                <Text style={styles.cardText}>R${resumo?.lucro || 0}</Text>
            </View>

        </View>
            

            
    );
};
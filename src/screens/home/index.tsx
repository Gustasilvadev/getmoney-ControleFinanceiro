import { useEffect, useState } from "react";
import { View,Text } from "react-native"
import { styles } from "./style";
import { UsuarioLoginResponse } from "@/src/interfaces/usuario/response";
import { HomeService } from "@/src/services/api/home";


export const HomeScreen = () =>{

    const [usuario, setUsuario] = useState<UsuarioLoginResponse | null>(null);

    useEffect(() => {
        const carregarUsuario = async () => {
            try {
                const usuarioData = await HomeService.listarUsuario();
                setUsuario(usuarioData);
            
            } catch (error) {
                console.error('Erro ao carregar usuário:', error);
        }
    };

    carregarUsuario();
  }, []);

    return(
        <View style={styles.container}>
            <Text style={styles.text}>Bem vindo, {usuario?.nome || 'Carregando...'}! </Text>
        </View>
    );
};
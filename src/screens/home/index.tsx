import { View,Text, FlatList, ActivityIndicator } from "react-native"
import { styles } from "./style";
import { UsuarioService } from "@/src/services/api/usuario";
import { useApi } from "@/src/hooks/useApi";
import { ResumoService } from "@/src/services/api/resumo";
import { MetaService } from "@/src/services/api/metas";
import { MetaResponse} from "@/src/interfaces/meta/response"


export const HomeScreen = () =>{

    const { data: usuario, loading:carregandoUsuario  } = useApi(UsuarioService.listarUsuarioLogado);
    const { data: resumo, loading:carregandoresumo  } = useApi(ResumoService.listarLucro);
    const { data: metas = [], loading:carregandoMetas } = useApi<MetaResponse[]>(MetaService.listarMetas);

    if (carregandoMetas) {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <ActivityIndicator size="large" />
        <Text>Carregando metas...</Text>
      </View>
    );
  }

  console.log('Metas:', metas); // <-- verificar dados aqui

    return(
        <View>

            <View style={styles.container}>
                <Text style={styles.text}>Bem vindo, {usuario?.nome || carregandoUsuario}! </Text>
            </View>

            <View style={styles.cardTop}>
                <Text style={styles.cardText}>Seu Saldo</Text>
                <Text style={styles.cardText}>R${resumo?.lucro || 0}</Text>
            </View>

                <FlatList
                    data={metas}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={({ item }) => (
                        <Text>{item.nome}</Text>
                    )}
                />

        </View>
               
    );
};
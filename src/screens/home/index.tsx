import { useApi } from "@/src/hooks/useApi";
import { ProgressoMetaResponse } from "@/src/interfaces/estatistica/response";
import { EstatisticaService } from "@/src/services/api/estatisticas";
import { UsuarioService } from "@/src/services/api/usuario";
import { FlatList, ScrollView, Text, View } from "react-native";
import { styles } from "./style";
import { TransacaoService } from "@/src/services/api/transacao";
import { TransacaoResponse } from "@/src/interfaces/transacao/response";

export const HomeScreen = () => {
  const { data: usuario, loading: carregandoUsuario } = useApi(UsuarioService.listarUsuarioLogado);
  const { data: resumo, loading: carregandoresumo } = useApi(EstatisticaService.listarLucro);
  const { data: progressoDaMeta = [], loading: carregandoProgresso } = useApi<ProgressoMetaResponse[]>(EstatisticaService.listarProgressoDaMeta);
  const { data:transacoes = [], loading:carregandoTransacoes} = useApi<TransacaoResponse[]>(TransacaoService.listarTransacao);

  return (
    
      <ScrollView 
        contentContainerStyle={styles.scrollContainer}
        showsVerticalScrollIndicator={false}>

        <View>
          <View style={styles.container}>
            <Text style={styles.text}>
              Bem vindo, {usuario?.nome || carregandoUsuario}!{" "}
            </Text>
          </View>

          <View style={styles.cardTop}>
            <Text style={styles.cardText}>Seu Saldo</Text>
            <Text style={styles.cardText}>R${resumo?.lucro || 0}</Text>
          </View>

          <Text style={styles.subtitle}>Minhas metas</Text>

          <FlatList
            data={progressoDaMeta}
            keyExtractor={(item) => item.metaId.toString()}
            renderItem={({ item }) => (

              <View style={styles.cardMeta}>
                <Text style={styles.cardTitleMeta}>{item.metaNome}</Text>

                <View style={styles.progressBar}>

                  <View
                    style={[styles.progressFill,{ width: `${Math.min(item.percentualConcluido, 100)}%` },]}>
                  </View>

                  <View style={styles.progressInfo}>

                    <Text style={styles.cardSubTitleMeta}>
                      R${item.valorAtual.toFixed(2)} de R$
                      {item.valorAlvo.toFixed(2)}
                    </Text>

                    <Text style={styles.cardSubTitlePercent}>
                      {Math.min(item.percentualConcluido, 100)}%
                    </Text>

                  </View>
                </View>
              </View>
            )}
            ListEmptyComponent={
              <Text style={styles.emptyTextMeta}>Nenhuma meta cadastrada</Text>
            }
            scrollEnabled={false}

          />

          <Text style={styles.subtitle}>Transações recentes</Text>
            <FlatList
            data={transacoes}
            keyExtractor={(item) => item.id.toString()}
            renderItem={({ item }) => (

              <View>
                <Text></Text>
              </View>
              // <View style={styles.}>
              //   

              //   <View style={styles.}>

              //     <View style={styles.}>

              //       <Text style={styles.}>
              //         R${item.transacoes.valor.toFixed(2)}
                     
              //       </Text>

              //     </View>
              //   </View>
              // </View>
            )}
            ListEmptyComponent={
              <Text style={styles.emptyTextMeta}>Nenhuma transação cadastrada</Text>
            }
            scrollEnabled={false}

          />


        </View>
      </ScrollView>
  );
};
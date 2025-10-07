import { useApi } from "@/src/hooks/useApi";
import { ProgressoMetaResponse } from "@/src/interfaces/estatistica/response";
import { EstatisticaService } from "@/src/services/api/estatisticas";
import { UsuarioService } from "@/src/services/api/usuario";
import { FlatList, Text, View } from "react-native";
import { styles } from "./style";

export const HomeScreen = () => {
  const { data: usuario, loading: carregandoUsuario } = useApi(
    UsuarioService.listarUsuarioLogado
  );
  const { data: resumo, loading: carregandoresumo } = useApi(
    EstatisticaService.listarLucro
  );
  const { data: progressoDaMeta = [], loading: carregandoProgresso } = useApi<
    ProgressoMetaResponse[]
  >(EstatisticaService.listarProgressoDaMeta);

  return (
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
                style={[
                  styles.progressFill,
                  { width: `${Math.min(item.percentualConcluido, 100)}%` },
                ]}
              ></View>

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
      />
    </View>
  );
};

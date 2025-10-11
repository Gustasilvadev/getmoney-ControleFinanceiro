import { useApi } from "@/src/hooks/useApi";
import { FlatList, ScrollView, Text, View } from "react-native";
import { styles } from "./style";

import { EstatisticaService } from "@/src/services/api/estatisticas";
import { TransacaoService } from "@/src/services/api/transacao";
import { UsuarioService } from "@/src/services/api/usuario";

import { CategoriaTipo } from "@/src/enums/categoriaTipo";
import { ProgressoMetaResponse } from "@/src/interfaces/estatistica/response";
import { TransacaoResponse } from "@/src/interfaces/transacao/response";

export const HomeScreen = () => {

    const { data: usuario, loading: carregandoUsuario } = useApi(UsuarioService.listarUsuarioLogado);
    const { data: resumo, loading: carregandoresumo } = useApi(EstatisticaService.listarLucro);
    const { data: progressoDaMeta = [], loading: carregandoProgresso } = useApi<ProgressoMetaResponse[]>(EstatisticaService.listarProgressoDaMeta);
    const { data: transacoes, loading: carregandoTransacoes } = useApi<TransacaoResponse[]>(TransacaoService.listarTransacao);

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
                    style={[
                      styles.progressFill,
                      { width: `${Math.min(item.percentualConcluido, 100)}%` },
                    ]}>
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
              <Text style={styles.emptyText}>Nenhuma meta cadastrada</Text>
            }
            scrollEnabled={false}
          />

          <Text style={styles.subtitle}>Transações recentes</Text>
          {!carregandoTransacoes && transacoes && transacoes.length > 0 ? (
            <FlatList
              data={transacoes.slice(-4)}
              keyExtractor={(item) => item.id.toString()}
              renderItem={({ item }) => (

                <View style={styles.transacaoContainer}>
                  <View style={styles.transacaoCard}>

                    <View style={styles.contentLeft}>

                      <View style={styles.descricaoLinha}>
                        <Text style={styles.descricao}>{item.descricao}</Text>
                        <Text style={styles.data}>{item.data}</Text>
                      </View>

                      {item.categoria && (
                        <Text style={styles.categoriaNome}>
                          {item.categoria.nome}
                        </Text>
                      )}

                      {item.metas && item.metas.length > 0 && (
                        <View>
                          {item.metas.map((meta) => (
                            <Text key={meta.id} style={styles.metaNome}>
                              Meta: {meta.nome}
                            </Text>
                          ))}
                        </View>
                      )}
                    </View>

                    <View style={styles.contentRight}>

                      <Text
                        style={[
                          styles.valor,
                          item.categoria?.tipo === CategoriaTipo.RECEITA
                            ? styles.valorReceita
                            : item.categoria?.tipo === CategoriaTipo.DESPESA
                            ? styles.valorDespesa
                            : styles.valorNeutro,
                        ]}
                      >
                        R${item.valor.toFixed(2)}
                      </Text>

                    </View>
                  </View>
                </View>
              )}

              ListEmptyComponent={
                <Text style={styles.emptyText}>Nenhuma transação cadastrada</Text>
              }
              scrollEnabled={false}
            />
          ) : carregandoTransacoes ? (
            <Text>Carregando...</Text>
          ) : (
            <Text style={styles.emptyText}>Nenhuma transação cadastrada</Text>
          )}
          
        </View>
      </ScrollView>
    );
};

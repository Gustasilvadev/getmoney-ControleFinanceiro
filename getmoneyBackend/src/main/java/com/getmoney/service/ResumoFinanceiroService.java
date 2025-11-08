package com.getmoney.service;

import com.getmoney.dto.response.CategoriaValorTotalResponseDTO;
import com.getmoney.dto.response.EvolucaoMensalResponseDTO;
import com.getmoney.dto.response.ProgressoMetaResponseDTO;
import com.getmoney.dto.response.ResumoFinanceiroResponseDTO;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Meta;
import com.getmoney.entity.Transacao;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;
import com.getmoney.repository.AnaliseRepository;
import com.getmoney.repository.TransacaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResumoFinanceiroService {

    private final TransacaoRepository transacaoRepository;

    private final AnaliseRepository analiseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ResumoFinanceiroService(TransacaoRepository transacaoRepository, AnaliseRepository analiseRepository) {
        this.transacaoRepository = transacaoRepository;
        this.analiseRepository = analiseRepository;
    }

    /**
     * Obtém o resumo financeiro de um usuário, calculando totais de receitas, despesas e lucro.
     */
    public ResumoFinanceiroResponseDTO getResumoFinanceiro(Integer usuarioId) {
        // Trata valores nulos
        BigDecimal receitas = transacaoRepository.TotalReceitas(usuarioId);
        BigDecimal despesas = transacaoRepository.TotalDespesas(usuarioId);

        // Se for null, usa ZERO
        BigDecimal receitasSafe = receitas != null ? receitas : BigDecimal.ZERO;
        BigDecimal despesasSafe = despesas != null ? despesas : BigDecimal.ZERO;

        BigDecimal lucro = receitasSafe.subtract(despesasSafe);

        return new ResumoFinanceiroResponseDTO(receitasSafe, despesasSafe, lucro);
    }

    /**
     * Lista categorias com total de gasto pelo usuário em despesas ativas
     * Retorna ID, nome da categoria e valor total gasto
     */
    public List<CategoriaValorTotalResponseDTO> getResumoCategorias(Integer usuarioId) {
        List<Object[]> resultados = analiseRepository.listarCategoriasComTotalGastoPorUsuario(usuarioId);

        return resultados.stream().map(result -> {
            Categoria categoria = (Categoria) result[0];
            BigDecimal totalGasto = result[1] != null ? (BigDecimal) result[1] : BigDecimal.ZERO;

            BigDecimal valorNegativo = totalGasto.negate();

            return new CategoriaValorTotalResponseDTO(
                    categoria.getId(),
                    categoria.getNome(),
                    valorNegativo,
                    usuarioId
            );
        }).collect(Collectors.toList());
    }

    /**
     * Lista categorias utilizadas por um usuário específico, calculando o valor total
     * das transações ativas do usuário em cada categoria
     */
    public List<CategoriaValorTotalResponseDTO> listarCategoriasComValorTotal(Integer usuarioId) {
        List<Categoria> categorias = analiseRepository.findByUsuarioIdAndStatus(usuarioId, Status.ATIVO);

        return categorias.stream()
                .map(categoria -> {
                    BigDecimal valorTotal = Optional.ofNullable(categoria.getTransacoes())
                            .orElse(Collections.emptyList())
                            .stream()
                            .filter(transacao -> transacao.getStatus() == Status.ATIVO &&
                                    transacao.getUsuario().getId().equals(usuarioId))
                            .map(Transacao::getValor)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal valorFinal = categoria.getTipo() == CategoriaTipo.DESPESA ?
                            valorTotal.negate() :
                            valorTotal;

                    return new CategoriaValorTotalResponseDTO(
                            categoria.getId(),
                            categoria.getNome(),
                            valorFinal,
                            usuarioId
                    );
                })
                .collect(Collectors.toList());
    }

    /**
     * Calcula evolução mensal de receitas e despesas por período
     * Retorna totais mensais para análise comparativa do fluxo financeiro
     */
    public List<EvolucaoMensalResponseDTO> getEvolucaoMensal(Integer usuarioId, Integer meses) {
        LocalDate dataFim = LocalDate.now();
        LocalDate dataInicio = dataFim.minusMonths(meses != null ? meses : 6);

        List<Object[]> resultados = analiseRepository.listarEvolucaoMensalPorUsuario(
                usuarioId, dataInicio, dataFim);

        return resultados.stream().map(result -> {
            Integer ano = (Integer) result[0];
            Integer mes = (Integer) result[1];
            LocalDate periodo = LocalDate.of(ano, mes, 1);

            BigDecimal totalDespesas = result[2] != null ? (BigDecimal) result[2] : BigDecimal.ZERO;
            BigDecimal totalReceitas = result[3] != null ? (BigDecimal) result[3] : BigDecimal.ZERO;

            return new EvolucaoMensalResponseDTO(periodo, totalDespesas, totalReceitas);
        }).collect(Collectors.toList());
    }

    /**
     * Monitora progresso das metas financeiras com percentual de conclusão
     * Calcula valor atual x valor alvo e status de cada meta
     */
    public List<ProgressoMetaResponseDTO> getProgressoMetas(Integer usuarioId) {
        List<Object[]> resultados = analiseRepository.ListarMetasComProgressoPorUsuario(usuarioId);

        return resultados.stream().map(result -> {
            Meta meta = (Meta) result[0];
            BigDecimal valorAtual = result[1] != null ? (BigDecimal) result[1] : BigDecimal.ZERO;

            ProgressoMetaResponseDTO dto = new ProgressoMetaResponseDTO();
            dto.setMetaId(meta.getId());
            dto.setMetaNome(meta.getNome());
            dto.setValorAlvo(meta.getValorAlvo());
            dto.setValorAtual(valorAtual);
            dto.setStatus(meta.getStatus());

            if (meta.getValorAlvo() != null && meta.getValorAlvo().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal percentual = valorAtual
                        .divide(meta.getValorAlvo(), 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"));
                dto.setPercentualConcluido(percentual.setScale(0, RoundingMode.HALF_UP));
            } else {
                dto.setPercentualConcluido(BigDecimal.ZERO);
            }

            return dto;
        }).collect(Collectors.toList());
    }
}


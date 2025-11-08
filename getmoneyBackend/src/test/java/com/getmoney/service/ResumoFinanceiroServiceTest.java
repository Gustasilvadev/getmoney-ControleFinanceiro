package com.getmoney.service;

import com.getmoney.dto.response.*;
import com.getmoney.entity.Categoria;
import com.getmoney.entity.Transacao;
import com.getmoney.entity.Usuario;
import com.getmoney.enums.CategoriaTipo;
import com.getmoney.enums.Status;
import com.getmoney.repository.AnaliseRepository;
import com.getmoney.repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para a classe ResumoFinanceiroService
 */
@ExtendWith(MockitoExtension.class)
class ResumoFinanceiroServiceTest {

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private AnaliseRepository analiseRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ResumoFinanceiroService resumoFinanceiroService;

    /**
     * Testa o cenário onde o resumo financeiro é calculado com sucesso
     */
    @Test
    void deveRetornarResumoFinanceiroComSucesso() {
        // Cenario
        Integer usuarioId = 1;
        BigDecimal receitas = new BigDecimal("5000.00");
        BigDecimal despesas = new BigDecimal("3000.00");
        BigDecimal lucroEsperado = new BigDecimal("2000.00");

        when(transacaoRepository.TotalReceitas(usuarioId))
                .thenReturn(receitas);
        when(transacaoRepository.TotalDespesas(usuarioId))
                .thenReturn(despesas);

        // Execucao
        ResumoFinanceiroResponseDTO resultado = resumoFinanceiroService.getResumoFinanceiro(usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertEquals(receitas, resultado.getReceitas());
        assertEquals(despesas, resultado.getDespesas());
        assertEquals(lucroEsperado, resultado.getLucro());
        verify(transacaoRepository, times(1)).TotalReceitas(usuarioId);
        verify(transacaoRepository, times(1)).TotalDespesas(usuarioId);
    }


    /**
     * Testa o cenário onde não há transações (valores nulos)
     */
    @Test
    void deveRetornarResumoFinanceiroComValoresZerados() {
        // Cenario
        Integer usuarioId = 1;

        when(transacaoRepository.TotalReceitas(usuarioId))
                .thenReturn(null);
        when(transacaoRepository.TotalDespesas(usuarioId))
                .thenReturn(null);

        // Execucao
        ResumoFinanceiroResponseDTO resultado = resumoFinanceiroService.getResumoFinanceiro(usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertEquals(BigDecimal.ZERO, resultado.getReceitas());
        assertEquals(BigDecimal.ZERO, resultado.getDespesas());
        assertEquals(BigDecimal.ZERO, resultado.getLucro());
    }

    /**
     * Testa o cálculo do resumo financeiro com apenas receitas
     */
    @Test
    void deveCalcularResumoApenasComReceitas() {
        // Cenario
        Integer usuarioId = 1;
        BigDecimal receitas = new BigDecimal("4000.00");

        when(transacaoRepository.TotalReceitas(usuarioId))
                .thenReturn(receitas);
        when(transacaoRepository.TotalDespesas(usuarioId))
                .thenReturn(null);

        // Execucao
        ResumoFinanceiroResponseDTO resultado = resumoFinanceiroService.getResumoFinanceiro(usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertEquals(receitas, resultado.getReceitas());
        assertEquals(BigDecimal.ZERO, resultado.getDespesas());
        assertEquals(receitas, resultado.getLucro());
    }

    /**
     * Testa o cenário onde não há gastos nas categorias
     */
    @Test
    void deveRetornarListaVaziaQuandoNaoHaGastos() {
        // Cenario
        Integer usuarioId = 1;
        List<Object[]> resultadosVazios = Collections.emptyList();

        when(analiseRepository.listarCategoriasComTotalGastoPorUsuario(usuarioId))
                .thenReturn(resultadosVazios);

        // Execucao
        List<CategoriaValorTotalResponseDTO> resultado = resumoFinanceiroService.getResumoCategorias(usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    /**
     * Testa a listagem de categorias com valor total (receitas e despesas)
     */
    @Test
    void deveListarCategoriasComValorTotalComSucesso() {
        // Cenario
        Integer usuarioId = 1;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        // Categoria de despesa
        Categoria categoriaDespesa = new Categoria();
        categoriaDespesa.setId(1);
        categoriaDespesa.setNome("Alimentação");
        categoriaDespesa.setTipo(CategoriaTipo.DESPESA);

        Transacao transacaoDespesa = new Transacao();
        transacaoDespesa.setValor(new BigDecimal("500.00"));
        transacaoDespesa.setStatus(Status.ATIVO);
        transacaoDespesa.setUsuario(usuario);

        categoriaDespesa.setTransacoes(Arrays.asList(transacaoDespesa));

        List<Categoria> categorias = Arrays.asList(categoriaDespesa);

        when(analiseRepository.findByUsuarioIdAndStatus(usuarioId, Status.ATIVO))
                .thenReturn(categorias);

        // Execucao
        List<CategoriaValorTotalResponseDTO> resultado = resumoFinanceiroService.listarCategoriasComValorTotal(usuarioId);

        // Validacao
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(new BigDecimal("-500.00"), resultado.get(0).getValorTotal());
    }

    /**
     * Testa a evolução mensal com período padrão (6 meses)
     */
    @Test
    void deveUsarPeriodoPadraoQuandoMesesNaoInformado() {
        // Cenario
        Integer usuarioId = 1;
        LocalDate dataFim = LocalDate.now();
        LocalDate dataInicio = dataFim.minusMonths(6); // Período padrão

        when(analiseRepository.listarEvolucaoMensalPorUsuario(usuarioId, dataInicio, dataFim))
                .thenReturn(Collections.emptyList());

        // Execucao
        List<EvolucaoMensalResponseDTO> resultado = resumoFinanceiroService.getEvolucaoMensal(usuarioId, null);

        // Validacao
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }
}

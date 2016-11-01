package com.github.kyriosdata.regras.infraestrutura;

import com.github.kyriosdata.regras.Pontuacao;
import com.github.kyriosdata.regras.Valor;
import com.github.kyriosdata.regras.regra.Regra;
import com.github.kyriosdata.regras.regra.RegraExpressao;
import com.github.kyriosdata.regras.regra.RegraPontosPorRelato;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SerializacaoTest {

    private static Serializador sz;

    @BeforeAll
    public static void setUpClass() {
        sz = new Serializador();
    }

    @Test
    public void serializarValor() {
        verificaValor(new Valor(LocalDate.now()));
        verificaValor(new Valor(3.14f));
        verificaValor(new Valor(true));
        verificaValor(new Valor(false));
        verificaValor(new Valor("casa"));
    }

    private void verificaValor(Valor valor) {
        Valor recuperado = sz.valor(sz.toJson(valor));
        assertEquals(valor, recuperado);
    }

    @Test
    public void serializarPontuacao() {
        verificaPontuacao(new Pontuacao("a", new Valor(false)));
    }

    public void verificaPontuacao(Pontuacao pontuacao) {
        String json = sz.toJson(pontuacao);
        Pontuacao recuperado = sz.pontuacao(json);
        assertEquals(pontuacao, recuperado);
    }

    @Test
    public void serializarRegraExpressao() {
        verificaRegra(new RegraExpressao("x", "d", 1f, 0f, "1"));
        verificaRegra(new RegraPontosPorRelato("p", "d", 1f, 0f, "t", 0.5f));
    }

    @Test
    public void valoresPadraoParaRegraExpressao() {
        String json = "{ \"tipo\" : \"expressao\", \"obj\" : " +
                "{ \"variavel\": \"soma\", \"expressao\": \"x + y\" } }";

        RegraExpressao recuperada = (RegraExpressao)sz.regra(json);
        assertEquals("x + y", recuperada.getExpressao());
        assertEquals("soma", recuperada.getVariavel());
        assertNull(recuperada.getDescricao());
        assertEquals(Float.MAX_VALUE, recuperada.getValorMaximo(), 0.0001d);
        assertEquals(Float.MIN_VALUE, recuperada.getValorMinimo(), 0.0001d);
    }

    private void verificaRegra(Regra expressao) {
        String json = sz.toJson(expressao);
        Regra recuperada = sz.regra(json);
        assertEquals(expressao, recuperada);
    }
}

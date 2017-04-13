package com.github.kyriosdata.regras.regra;

import com.github.kyriosdata.regras.Avaliavel;
import com.github.kyriosdata.regras.ParserTeste;
import com.github.kyriosdata.regras.Valor;
import com.github.kyriosdata.regras.excecoes.CampoExigidoNaoFornecido;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testes do avaliador de regras
 */
public class RegraPontosPorRelatoTest {

    @Test
    public void tipoRelatoNaoPodeSerNull() {
        assertThrows(CampoExigidoNaoFornecido.class, () -> new RegraPontosPorRelato("v", "d", 1, 0, null, 1));
    }

    @Test
    public void tipoRelatoNaoPodeSerVazio() {
        assertThrows(CampoExigidoNaoFornecido.class, () -> new RegraPontosPorRelato("v", "d", 1, 0, "", 1));
    }

    @Test
    public void agradaCobertura() {
        RegraPontosPorRelato rp = new RegraPontosPorRelato("v", "d", 1, 0, "a", 1);
        assertEquals("a", rp.getClasse());
        assertEquals(1f, rp.getPontosPorItem(), 0.0001f);
    }

    @Test
    public void nenhumRelatoTotalDePontosZeroOuMinimo() {
        RegraPontosPorRelato rp = new RegraPontosPorRelato("v", "d", 7, 6, "a", 8);

        ParserTeste parser = new ParserTeste();
        parser.setDependencias(new ArrayList<>(0));
        rp.preparacao(parser);

        assertEquals(6, rp.avalie(new ArrayList<>(0), new HashMap<>(0)).getReal(), 0.0001f);
    }

    @Test
    public void variosRelatosTotalObtidoPelaMultiplicacao() {
        final String classe = "classe-de-teste";

        RegraPontosPorRelato rp = new RegraPontosPorRelato("v", "d", 17, 6, classe, 8);

        ParserTeste parser = new ParserTeste();
        parser.setDependencias(new ArrayList<>(0));
        rp.preparacao(parser);

        ArrayList<Avaliavel> avaliaveis = new ArrayList<>(2);
        Avaliavel avaliavel = new Avaliavel() {
            @Override
            public Valor get(String atributo) {
                return null;
            }

            @Override
            public String getClasse() {
                return classe;
            }
        };
        avaliaveis.add(avaliavel);
        avaliaveis.add(avaliavel);

        assertEquals(16, rp.avalie(avaliaveis, new HashMap<>(0)).getReal(), 0.0001f);
    }
}

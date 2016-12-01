package com.github.kyriosdata.regras.avaliacao;

import com.github.kyriosdata.regras.Relato;
import com.github.kyriosdata.regras.Valor;
import com.github.kyriosdata.regras.regra.Regra;
import com.github.kyriosdata.regras.regra.RegraExpressao;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvaliadorServiceTest {

    @Test
    public void nenhumaRegraNenhumResultado() {
        AvaliadorService as = new AvaliadorService();
        Map<String, Valor> r = as.avalia(null, null, null, null);

        assertEquals(0, r.size());
    }

    @Test
    public void umaRegraConstante() {
        AvaliadorService as = new AvaliadorService();
        List<Regra> regras = new ArrayList();
        regras.add(getConstante());
        Map<String, Valor> r = as.avalia(regras, null, null, null);

        assertEquals(1, r.size());
        assertEquals(3f, r.get("x").getReal(), 0.0001d);
    }

    @Test
    public void umaExpressaoDependenteDeVarNaoFornecida() {
        AvaliadorService as = new AvaliadorService();

        List<Regra> regras = new ArrayList();
        regras.add(getExpressao());

        Map<String, Valor> r = as.avalia(regras, null, null, null);

        assertEquals(1, r.size());
        assertEquals(0f, r.get("x").getReal(), 0.0001d);
    }

    @Test
    public void umaExpressaoDependenteDeRelato() {
        AvaliadorService as = new AvaliadorService();

        List<Regra> regras = new ArrayList();
        regras.add(getExpressao());

        Map<String, Valor> atributos = new HashMap<>(1);
        atributos.put("a", new Valor(1.23f));
        List<Relato> relatos = new ArrayList<>(1);
        relatos.add(new Relato("c", atributos));

        Map<String, Valor> r = as.avalia(regras, relatos, null, null);

        assertEquals(1, r.size());
        assertEquals(1.23f, r.get("x").getReal(), 0.0001d);
    }

    private Regra getConstante() {
        return new RegraExpressao("x", "d", 10f, 0f, "3");
    }

    private Regra getExpressao() {
        return new RegraExpressao("x", "d", 10f, 0f, "a");
    }
}

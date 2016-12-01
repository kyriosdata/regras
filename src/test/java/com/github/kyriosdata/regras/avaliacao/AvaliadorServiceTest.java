package com.github.kyriosdata.regras.avaliacao;

import com.github.kyriosdata.regras.Valor;
import com.github.kyriosdata.regras.regra.Regra;
import com.github.kyriosdata.regras.regra.RegraExpressao;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
        regras.add(getExpressao());
        Map<String, Valor> r = as.avalia(regras, null, null, null);

        assertEquals(0, r.size());
    }

    private Regra getExpressao() {
        return new RegraExpressao("x", "d", 10f, 0f, "3");
    }
}

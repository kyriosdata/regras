package com.github.kyriosdata.regras.avaliacao;

import com.github.kyriosdata.regras.Valor;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvaliadorServiceTest {

    @Test
    public void nenhumaRegraNenhumResultado() {
        AvaliadorService as = new AvaliadorService();
        Map<String, Valor> r = as.avalia(null, null, null, null);

        assertEquals(0, r.size());
    }
}

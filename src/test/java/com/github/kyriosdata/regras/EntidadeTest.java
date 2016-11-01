package com.github.kyriosdata.regras;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EntidadeTest {

    @Test
    public void construcaoSemArgumentoIdAutomatico() {
        EntidadeParaTeste et = new EntidadeParaTeste();
        assertNotNull(et.getId());
    }
}

class EntidadeParaTeste extends Entidade {

}


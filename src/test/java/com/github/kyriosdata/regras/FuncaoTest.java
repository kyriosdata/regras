package com.github.kyriosdata.regras;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FuncaoTest {

    @Test
    public void umValorLogicoFalso() {
        assertEquals(1, calculo(1, 1, 0));
        assertEquals(2, calculo(1, 2, 0));
        assertEquals(2, calculo(12, 1, 1));
        assertEquals(14, calculo(12, 1, 2));
    }

    /**
     * Obtém o total de meses, contando o mês de início, a partir do
     * mês de ínicio, até o mês de fim, e a diferença em anos.
     *
     * <p>Essa função produz meses que são "tocados" pelo período fornecido.
     * Por exemplo, de dez/2015 a jan/2016 a resposta deve ser 2, pois "toca"
     * dezembro e janeiro. Observe que a data de início pode ser 31/12/2015 até
     * 01/01/2016, nesse período, 2 meses estão envolvidos.
     *
     * @param mi Mês de início do período.
     * @param mf M~es de fim do período.
     * @param anos Diferença em anos do período.
     *
     * @return A quantidade de meses contemplados pelo período, não necessariamente
     * são meses "cheios".
     */
    public float calculo(int mi, int mf, int anos) {

        int deltaAnos = anos > 1 ? 12 * (anos - 1) : 0;
        int meses = mi > mf ? 12 - mi + mf : mf - mi;

        return meses + deltaAnos + 1;
    }
}


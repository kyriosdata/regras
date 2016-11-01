/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras;

import com.github.kyriosdata.regras.regra.Regra;

/**
 * Representa qualquer item que pode ser
 * avaliado por uma regra. Adicionalmente,
 * um avaliável é um item que pode ser
 * "modificado" por uma observação.
 *
 * @see Observacao
 * @see Regra
 * @see Relato
 */
public interface Avaliavel {

    /**
     * Recupera o valor do atributo para o
     * objeto em questão.
     *
     * @param atributo O identificador único do atributo.
     *
     * @return O valor do atributo.
     */
    Valor get(String atributo);

    /**
     * Classe associada ao item avaliável.
     *
     * @return Identificador da classe do avaliável.
     */
    String getClasse();
}

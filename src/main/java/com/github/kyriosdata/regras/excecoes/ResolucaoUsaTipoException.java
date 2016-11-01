/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras.excecoes;

/**
 * Indica a existência de referência para o objeto que se
 * deseja remover.
 */
public class ResolucaoUsaTipoException extends RuntimeException {

    /**
     * Sinaliza que há resolução que faz uso do tipo.
     *
     * @param mensagem Detalhes da situação excepcional.
     */
    public ResolucaoUsaTipoException(final String mensagem) {
        super(mensagem);
    }
}

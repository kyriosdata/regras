/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras.regra;

import com.github.kyriosdata.regras.Avaliavel;
import com.github.kyriosdata.regras.excecoes.CampoExigidoNaoFornecido;
import com.github.kyriosdata.regras.Valor;

import java.util.List;
import java.util.Map;

/**
 * Regra que identifica total de pontos por dada
 * classe de objeto avaliável.
 */
public class RegraPontosPorRelato extends Regra {

    /**
     * Quantidade de pontos definidos por item
     * {@link Avaliavel}.
     */
    private float pontosPorItem;

    /**
     * Cria uma regra.
     *
     * @param variavel      O identificador (nome) da variável que retém o
     *                      valor da avaliação da regra. Em um dado conjunto de
     *                      regras, existe uma variável distinta para cada uma
     *                      delas.
     * @param descricao     Texto que fornece alguma explanação sobre a regra.
     * @param valorMaximo   O valor máximo a ser utilizado como resultado da
     *                      avaliação da regra. Esse valor é empregado apenas
     *                      se a avaliação resultar em valor superior ao
     *                      expresso por esse parâmetro.
     * @param valorMinimo   O valor mínimo a ser utilizado como resultado da
     *                      avaliação da regra. Esse valor é empregado apenas
     *                      se a avaliação resultar em valor inferior ao
     *                      expresso por esse parâmetro.
     * @param tipo    Nome que identifica um relato, empregado em regras
     *                      cuja avaliação é pontos por relato.
     * @param pontos Total de pontos para cada relato de um dado
     *                      tipo.
     * @throws CampoExigidoNaoFornecido Caso um campo obrigatório para a
     *                                  definição de uma regra não seja
     *                                  fornecido.
     */
    public RegraPontosPorRelato(final String variavel,
                                final String descricao,
                                final float valorMaximo,
                                final float valorMinimo,
                                final String tipo,
                                final float pontos) {
        super(variavel, descricao, valorMaximo, valorMinimo, tipo);
        if (tipo == null || tipo.isEmpty()) {
            throw new CampoExigidoNaoFornecido("tipoRelato");
        }

        this.pontosPorItem = pontos;
    }

    /**
     * Recupera a quantidade de pontos atribuída a cada
     * item para obtenção do valor da regra.
     *
     * @return Pontos por item avaliável.
     */
    public final float getPontosPorItem() {
        return pontosPorItem;
    }

    @Override
    public final Valor avalie(final List<? extends Avaliavel> avaliaveis,
                        final Map<String, Valor> contexto) {
        int total = 0;
        for (Avaliavel avaliavel : avaliaveis) {
            if (getClasse().equals(avaliavel.get("classe").getString())) {
                total = total + 1;
            }
        }

        float pontos = getPontosPorItem() * total;

        return new Valor(ajustaLimites(pontos));
    }
}

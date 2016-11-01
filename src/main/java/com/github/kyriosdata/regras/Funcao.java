/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras;

import com.github.kyriosdata.regras.excecoes.CampoExigidoNaoFornecido;
import com.github.kyriosdata.regras.regra.Regra;

import java.util.List;

/**
 * Uma função é o meio para realizar algumas operações
 * sobre atributos de um avaliável, cujo resultado é
 * atribuído a uma variável que pode fazer parte de
 * uma expressão de uma regra.
 *
 * <p>Por exemplo, a regra que soma todos os dias
 * dos relatos de uma dada classe exige que a diferença
 * em dias entre os atributos identificados por
 * "dataInicio" e "dataFim' seja estabelecida.
 * A função de código 1, que representa a diferença
 * em dias entre essas duas datas pode ser representada
 * por uma instância dessa classe.
 *
 * @see Regra
 */
public class Funcao {

    /**
     * Identificador do resultado da função
     * empregado em uma expressão.
     */
    private String variavel;

    /**
     * Código único da função.
     */
    private int codigo;

    /**
     * Sequência dos nomes dos argumentos empregados pela função.
     */
    private List<String> argumentos;

    /**
     * Cria uma função.
     *
     * @param resultado Nome da variável que guardará o resultado
     *                  da avaliação da função.
     *
     * @param identificador O identificador único da função.
     * @param parametros Lista de parâmetros empregados pela função.
     */
    public Funcao(final String resultado,
                  final int identificador,
                  final List<String> parametros) {

        if (resultado == null || resultado.isEmpty()) {
            throw new CampoExigidoNaoFornecido("resultado");
        }

        variavel = resultado;
        codigo = identificador;
        argumentos = parametros;
    }

    /**
     * Recupera o variavel pelo qual o tipo é conhecido.
     *
     * @return O variavel do tipo.
     */
    public final String getVariavel() {
        return variavel;
    }

    /**
     * Recupera o código da função.
     *
     * @return Valor inteiro que unicamente identifica a função.
     */
    public int getCodigo() {
        return codigo;
    }
}

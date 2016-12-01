/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras.avaliacao;

import com.github.kyriosdata.parser.IParser;
import com.github.kyriosdata.regras.Avaliavel;
import com.github.kyriosdata.regras.Observacao;
import com.github.kyriosdata.regras.Relato;
import com.github.kyriosdata.regras.Valor;
import com.github.kyriosdata.regras.excecoes.CampoExigidoNaoFornecido;
import com.github.kyriosdata.regras.infraestrutura.ParserAdapter;
import com.github.kyriosdata.regras.regra.OrdenacaoService;
import com.github.kyriosdata.regras.regra.Regra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementação do serviço de avaliação de regras.
 *
 */
public class AvaliadorService {

    /**
     * Observações a serem consideradas durante a
     * avaliação.
     */
    private List<Observacao> observacoes;

    /**
     * Avalia os relatos conforme as regras fornecidas, as observações e os
     * parâmetros.
     *
     * @param regras      Regras a serem avaliadas. Possivelmente
     *                    a ordem em que são fornecidas não é a ordem esperada
     *                    ou correta de execução. Ou seja, dependências entre
     *                    as regras não necessariamente são contempladas nesse
     *                    parâmetro.
     * @param relatos     Conjunto de relatos sobre os quais a avaliação
     *                    das regras será executada. Esse é o principal
     *                    componente da entrada.
     * @param observacoes Conjunto de pontuações que fornecem valores
     *                    "substitutos".
     * @param parametros  Conjunto de valores iniciais, possivelmente
     *                    empregados para definição de constantes.
     * @return Resultados produzidos pela avaliação. Cada regra dá origem
     * a um valor quando avaliada, o valor é associado ao nome da
     * variável da regra e retornado.
     */
    public Map<String, Valor> avalia(
            final List<Regra> regras,
            final List<Relato> relatos,
            final Map<String, Valor> observacoes,
            final Map<String, Valor> parametros) {

        // Acumula valores produzidos pela avaliação.
        Map<String, Valor> resultados = new HashMap<>();

        // Se não forem definidas regras, então não há o que avaliar
        if (regras == null || regras.size() == 0) {
            return resultados;
        }

        // Se nenhuma observação é fornecida, trate-a como lista vazia
        Map<String, Valor> prioridade = observacoes;
        if (prioridade == null) {
            prioridade = new HashMap<>(0);
        }

        // Preparação das regras
        IParser parser = new ParserAdapter();
        for(Regra regra : regras) {
            regra.preparacao(parser);
        }

        // Parâmetros fornecidos devem estar disponíveis na avaliação
        if (parametros != null) {
            // Valores iniciais devem estar disponíveis
            for (Map.Entry<String, Valor> entrada : parametros.entrySet()) {
                resultados.put(entrada.getKey(), entrada.getValue());
            }
        }

        // Regras são fornecidas em ordem arbitrária, contudo, a execução
        // deve respeitar dependências entre elas. Ou seja, se uma regra
        // depende de outra, então a "outra" deve ser executada antes da
        // "uma".
        List<Regra> ordenadas = OrdenacaoService.ordena(regras);

        // Algumas regras aplicam-se a um subconjunto dos relatos.
        // Um cache dos relatos por classe evita que essa avaliação
        // seja realizada para cada uma das regras.
        Map<String, List<Avaliavel>> relatosPorClasse = montaRelatosPorTipo(relatos);

        for (Regra regra : ordenadas) {

            // A avaliação da regra pode fazer uso de apenas um
            // subconjunto dos relatos.
            List<Avaliavel> relatosRelevantes = relatosPorClasse.get("tipo");

            // Avalie a regra, para o contexto disponível.
            Valor valor = regra.avalie(relatosRelevantes, resultados);

            // Valor produzido deve ser adicionado ao contexto.
            String variavel = regra.getVariavel();

            // Contudo, pode ser que uma "substituição" deva prevalecer
            // Ou seja, o valor produzido deve ceder para o fornecido.
            if (prioridade.containsKey(variavel)) {
                valor = prioridade.get(variavel);
            }

            // Acrescenta o valor calculado (ou o que deve prevalecer)
            // ao conjunto de resultados (contexto a ser utilizado).
            resultados.put(variavel, valor);
        }

        return resultados;
    }

    /**
     * Dado um conjunto de relatos, agrupa-os por tipo.
     *
     * @param relatos Conjunto de relatos.
     * @return Dicionário que reúne os relatos fornecidos pelos tipos
     * correspondentes. Se nenhum relato é fornecido como argumento,
     * então o dicionário retornado é vazio.
     */
    private Map<String, List<Avaliavel>> montaRelatosPorTipo(
            final List<Relato> relatos) {
        Map<String, List<Avaliavel>> relatosPorTipo = new HashMap<>();

        if (relatos == null || relatos.size() == 0) {
            return relatosPorTipo;
        }

        for (Relato relato : relatos) {
            String tipo = relato.getClasse();

            List<Avaliavel> lista = relatosPorTipo.get(tipo);
            if (lista == null) {
                lista = new ArrayList<>();
                relatosPorTipo.put(tipo, lista);
            }

            lista.add(relato);
        }

        return relatosPorTipo;
    }

    /**
     * Adiciona observação ao parecer.
     *
     * <p>Caso a observacao a ser acrescentada
     * se refira a um item {@link Avaliavel} para o qual já
     * exista uma observacao, então esse observacao existente é
     * substituída por aquela fornecida. Caso contrário, a
     * observacao é simplesmente acrescentada.
     *
     * <p>A adição de uma observacao possivelmente altera o
     * conjunto de pontuações do parecer, dado que o valor de
     * um relato é substituído por outro, ou até mesmo o valor
     * de uma pontuação. Esse método não atualiza a pontuação
     * de um parecer.
     *
     * @param observacao A observacao a ser acrescentada ao parecer.
     * @throws CampoExigidoNaoFornecido Caso a observacao
     *                                  seja {@code null}.
     */
    public void adicionaObservacao(final Observacao observacao) {
        if (observacao == null) {
            throw new CampoExigidoNaoFornecido("observacao");
        }

        if (observacoes == null) {
            observacoes = new ArrayList<>(1);
            observacoes.add(observacao);
            return;
        }

        for (Observacao n : observacoes) {
            Avaliavel original = n.getItemOriginal();
            Avaliavel novo = observacao.getItemOriginal();
            if (original.equals(novo)) {
                observacoes.remove(n);
                observacoes.add(observacao);
                return;
            }
        }

        observacoes.add(observacao);
    }
}

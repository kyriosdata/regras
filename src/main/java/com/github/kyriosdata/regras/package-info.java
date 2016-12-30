/**
 * Classes que representam os recursos
 * oferecidos pela Biblioteca de Execução
 * de Regras (BER).
 *
 * <p>Um relatório ({@link com.github.kyriosdata.regras.Relatorio})
 * é um conjunto de relatos ({@link com.github.kyriosdata.regras.Relato}),
 * que é definido por um conjunto de atributos, onde cada atributo
 * ({@link com.github.kyriosdata.regras.Atributo}) possui um valor
 * ({@link com.github.kyriosdata.regras.Valor}).
 *
 * <p>Um relatório reúne
 * os dados de entrada para uma contagem
 * ({@link com.github.kyriosdata.regras.Contagem}), que é o resultado
 * produzido pela avaliação do relatório. A avaliação é realizada
 * conforme a configuração
 * ({@link com.github.kyriosdata.regras.regra.Configuracao}), ou seja,
 * o conjunto de regras que define como a avaliação de um relatório
 * deve ser feita.
 *
 */
package com.github.kyriosdata.regras;

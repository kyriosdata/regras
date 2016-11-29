/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras.infraestrutura;

import com.github.kyriosdata.regras.Pontuacao;
import com.github.kyriosdata.regras.Relato;
import com.github.kyriosdata.regras.Valor;
import com.github.kyriosdata.regras.regra.Configuracao;
import com.github.kyriosdata.regras.regra.Regra;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Serviço para conversão de objetos do domínio em
 * JSON e, no sentido inverso,
 * recuperação de objetos a partir do JSON gerado.
 */
public class Serializador {

    private static Gson gson;
    private static Type valorType;
    private static Type pontuacaoType;
    private static Type regraType;
    private static Type configuracaoType;
    private static Type relatoType;

    /**
     * Cria instância de serializar preparada
     * para realizar conversões entre objetos e
     * sequências de caracters.
     *
     * <p>Há dois grupos de métodos principais nessa classe.
     * Aqueles do tipo {@link #toJson(Pontuacao)}, por exemplo,
     * cujo argumento é o objeto a ser convertido em JSON e,
     * no sentido inverso, {@link #pontuacao(String)}, que recebe
     * a sequência JSON e produz um objeto do tipo {@link Pontuacao}.
     */
    public Serializador() {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(Valor.class, new ValorSerializer());
        gb.registerTypeAdapter(Valor.class, new ValorDeserializer());
        gb.registerTypeAdapterFactory(new CustomTypeAdapterFactory());
        gson = gb.create();

        valorType = new TypeToken<Valor>() {}.getType();
        pontuacaoType = new TypeToken<Pontuacao>() {}.getType();
        regraType = new TypeToken<Regra>() {}.getType();
        configuracaoType = new TypeToken<Configuracao>() {}.getType();
        relatoType = new TypeToken<Relato>() {}.getType();
    }

    /**
     * Converte instância de {@link Valor} em
     * sequência de caracteres.
     *
     * @param v Instância de {@link Valor}.
     *
     * @return Sequência de caracteres correspondente ao
     * objeto fornecido no primeiro parâmetro.
     */
    public String toJson(Valor v) {
        return gson.toJson(v, valorType);
    }

    /**
     * Converte sequência de caracteres em instância
     * de {@link Valor}.
     *
     * @param json Sequência de caracteres correspondente
     *             a uma instância de {@link Valor}.
     *
     * @return Instância de {@link Valor} obtida da
     * sequência de caracteres fornecida.
     */
    public Valor valor(String json) {
        return gson.fromJson(json, valorType);
    }

    public String toJson(Pontuacao v) {
        return gson.toJson(v, pontuacaoType);
    }

    public Pontuacao pontuacao(String json) {
        return gson.fromJson(json, pontuacaoType);
    }

    public Regra regra(String json) {
        return gson.fromJson(json, regraType);
    }

    public String toJson(Regra v) {
        return gson.toJson(v, regraType);
    }

    public Configuracao configuracao(String json) {
        return gson.fromJson(json, configuracaoType);
    }

    public String toJson(Configuracao v) {
        return gson.toJson(v, configuracaoType);
    }

    public Relato relato(String json) {
        return gson.fromJson(json, relatoType);
    }

    public String toJson(Relato v) {
        return gson.toJson(v, relatoType);
    }
}



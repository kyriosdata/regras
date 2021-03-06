/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras.infraestrutura;

import com.github.kyriosdata.regras.Valor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import static com.github.kyriosdata.regras.Valor.REAL;

/**
 * Define processo de conversão de instância de Valor
 * em "string".
 *
 * @see Valor
 */
public class ValorSerializer implements JsonSerializer<Valor> {

    @Override
    public JsonElement serialize(Valor valor, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        byte tipo = valor.getTipo();

        obj.addProperty("tipo", tipo);
        switch (tipo) {
            case Valor.REAL:
                obj.addProperty("valor", valor.getReal());
                break;
            case Valor.LOGICO:
                obj.addProperty("valor", valor.getBoolean());
                break;
            case Valor.DATA:
                obj.addProperty("valor", valor.getData().format(Valor.FORMATO_DATA));
                break;
            case Valor.STRING:
                obj.addProperty("valor", valor.getString());
                break;
        }

        return obj;
    }
}

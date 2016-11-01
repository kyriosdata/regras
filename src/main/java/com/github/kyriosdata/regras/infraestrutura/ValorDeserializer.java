/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras.infraestrutura;

import com.github.kyriosdata.regras.Valor;
import com.google.gson.*;

import java.lang.reflect.Type;

import static com.github.kyriosdata.regras.Atributo.LOGICO;
import static com.github.kyriosdata.regras.Valor.DATA;
import static com.github.kyriosdata.regras.Valor.REAL;

/**
 * Define processo de conversão de "string" em instância
 * de Valor.
 *
 * @see Valor
 */
public class ValorDeserializer implements JsonDeserializer<Valor> {

    @Override
    public Valor deserialize(
            JsonElement jsonElement, Type type,
            JsonDeserializationContext jsonDeserializationContext)
            throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        byte tipo = jsonObject.get("tipo").getAsByte();
        JsonElement valor = jsonObject.get("valor");

        if (tipo == Valor.STRING) {
            return new Valor(valor.getAsString());
        } else if (tipo == Valor.REAL) {
            return new Valor(valor.getAsFloat());
        } else if (tipo == Valor.LOGICO) {
            return new Valor(valor.getAsBoolean());
        } else if (tipo == Valor.DATA) {
            return Valor.dataFromString(valor.getAsString());
        }

        return null;
    }
}

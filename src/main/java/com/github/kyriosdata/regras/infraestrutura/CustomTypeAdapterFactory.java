/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras.infraestrutura;

import com.github.kyriosdata.regras.regra.Regra;
import com.github.kyriosdata.regras.regra.RegraExpressao;
import com.github.kyriosdata.regras.regra.RegraPontosPorRelato;
import com.google.gson.*;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class CustomTypeAdapterFactory implements TypeAdapterFactory {

    private String[] classes = { "RegraExpressao", "RegraPontosPorRelato"};
    private String[] tipos = { "expressao", "pontosPorRelato"};

    private String getTipo(String className) {
        for (int i = 0; i < classes.length; i++) {
            if (className.contains(classes[i])) {
                return tipos[i];
            }
        }

        return null;
    }

    @Override
    public <T> TypeAdapter<T> create (final Gson gson, final TypeToken<T> type) {
        if (type.getRawType () != Regra.class)
            return null;

        final TypeAdapter<T> delegate = gson.getDelegateAdapter (this, type);

        return new TypeAdapter<T> () {
            @Override
            public void write (final JsonWriter jsonWriter, final T t) throws IOException {
                jsonWriter.beginObject();
                jsonWriter.name("tipo");
                jsonWriter.value(getTipo(t.getClass().getName()));
                jsonWriter.name("obj");
                delegate.write (jsonWriter, t);
                jsonWriter.endObject();
            }

            @Override
            public T read (final JsonReader jsonReader) throws IOException, JsonParseException {
                JsonElement tree = Streams.parse (jsonReader);
                JsonObject object = tree.getAsJsonObject();

                String clazz = object.get("tipo").getAsString();
                JsonElement obj = object.get("obj").getAsJsonObject();

                if ("expressao".equals(clazz)) {
                    return (T) gson.getDelegateAdapter(CustomTypeAdapterFactory.this, TypeToken.get(RegraExpressao.class)).fromJsonTree(obj);
                }

                if ("pontosPorRelato".equals(clazz)) {
                    return (T) gson.getDelegateAdapter(CustomTypeAdapterFactory.this, TypeToken.get(RegraPontosPorRelato.class)).fromJsonTree(obj);
                }

                throw new JsonParseException ("Erro ao desserializar " + type + ". Não é uma regra?");
            }
        };
    }
}
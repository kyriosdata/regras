package com.github.kyriosdata.regras;

import com.github.kyriosdata.regras.regra.Expressao;
import com.github.kyriosdata.regras.regra.Parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserCondicaoTeste implements Parser {

    private Map<String, List<String>> dependencias = new HashMap<>(3);
    private Map<String, Expressao> expressoes = new HashMap<>(3);

    public void setCondicao(Expressao condicao) {
        expressoes.put("condicao", condicao);
    }
    public void setEntao(Expressao entao) {
        expressoes.put("entao", entao);
    }
    public void setSenao(Expressao senao) {
        expressoes.put("senao", senao);
    }

    public void setDependencias(String sentenca, List<String> deps) {
        dependencias.put(sentenca, deps);
    }

    @Override
    public Expressao ast(String sentenca) {
        return expressoes.get(sentenca);
    }

    @Override
    public List<String> dependencias(String sentenca) {
        return dependencias.get(sentenca);
    }
}

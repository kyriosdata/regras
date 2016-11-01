package com.github.kyriosdata.regras;

import com.github.kyriosdata.regras.regra.Expressao;
import com.github.kyriosdata.regras.regra.Parser;

import java.util.List;

public class ParserTeste implements Parser {

    private Expressao expressaoRetorno;
    private List<String> dependencias;

    public void setExpressao(Expressao exprRetorno) {
        expressaoRetorno = exprRetorno;
    }

    public void setDependencias(List<String> deps) {
        dependencias = deps;
    }

    @Override
    public Expressao ast(String sentenca) {
        return expressaoRetorno;
    }

    @Override
    public List<String> dependencias(String sentenca) {
        return dependencias;
    }
}

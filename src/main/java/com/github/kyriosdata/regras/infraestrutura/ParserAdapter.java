/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.regras.infraestrutura;

import com.github.kyriosdata.parser.*;
import com.github.kyriosdata.regras.Valor;

import java.util.ArrayList;
import java.util.List;

/**
 * Define processo de conversão de instância de Valor
 * em "string".
 *
 * @see Valor
 */
public class ParserAdapter implements IParser {

    @Override
    public Expressao ast(String sentenca) {
        List<Token> tokens = new Lexer(sentenca).tokenize();

        Parser parser = new Parser(tokens);
        return parser.expressao();
    }

    @Override
    public List<String> dependencias(String sentenca) {
        List<Token> tokens = new Lexer(sentenca).tokenize();
        List<String> ids = new ArrayList<>();
        for (Token token : tokens) {
            if (token.getTipo() == Lexer.ID) {
                ids.add(token.getElemento());
            }
        }

        return ids;
    }
}

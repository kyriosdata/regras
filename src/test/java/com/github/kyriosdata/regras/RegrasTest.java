package com.github.kyriosdata.regras;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RegrasTest {

    @Test
    public void umaRegraTrivial() {
        String regra = "{ \"tipo\" : \"expressao\", \"obj\" : { \"variavel\": \"soma\", \"expressao\": \"x + y\" } }";
        String cfg = "{ \"regras\" : " + regra + "}";
    }
}


package com.github.kyriosdata.regras.avaliacao;

import com.github.kyriosdata.regras.Relato;
import com.github.kyriosdata.regras.Valor;
import com.github.kyriosdata.regras.infraestrutura.Serializador;
import com.github.kyriosdata.regras.regra.Configuracao;
import com.github.kyriosdata.regras.regra.Regra;
import com.github.kyriosdata.regras.regra.RegraCondicional;
import com.github.kyriosdata.regras.regra.RegraExpressao;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AvaliadorServiceTest {

    @Test
    public void nenhumaRegraNenhumResultado() {
        AvaliadorService as = new AvaliadorService();
        Map<String, Valor> r = as.avalia(null, null, null, null);

        assertEquals(0, r.size());
    }

    @Test
    public void umaRegraConstante() {
        AvaliadorService as = new AvaliadorService();

        List<Regra> regras = new ArrayList();
        regras.add(getConstante());

        Map<String, Valor> r = as.avalia(regras, null, null, null);

        assertEquals(1, r.size());
        assertEquals(3f, r.get("x").getReal(), 0.0001d);
    }

    @Test
    public void umaExpressaoDependenteDeVarNaoFornecida() {
        AvaliadorService as = new AvaliadorService();

        List<Regra> regras = new ArrayList();
        regras.add(getExpressao());

        Map<String, Valor> r = as.avalia(regras, null, null, null);

        assertEquals(1, r.size());
        assertEquals(-1f, r.get("x").getReal(), 0.0001d);
    }

    @Test
    public void umaExpressaoDependenteDeParametro() {
        AvaliadorService as = new AvaliadorService();

        List<Regra> regras = new ArrayList();
        regras.add(getExpressao());

        Map<String, Valor> parametros = new HashMap<>(1);
        parametros.put("a", new Valor(1.23f));

        List<Relato> relatos = new ArrayList<>(0);

        Map<String, Valor> r = as.avalia(regras, relatos, null, parametros);

        assertEquals(2, r.size());
        assertEquals(0.23f, r.get("x").getReal(), 0.0001d);
    }

    @Test
    public void umaCondicao() {
        AvaliadorService as = new AvaliadorService();

        List<Regra> regras = new ArrayList();
        regras.add(getCondicao()); // 2 < b então b, senão 0

        Map<String, Valor> parametros = new HashMap<>(1);
        parametros.put("b", new Valor(3f));

        List<Relato> relatos = new ArrayList<>(0);

        Map<String, Valor> r = as.avalia(regras, relatos, null, parametros);

        assertEquals(2, r.size());
        assertEquals(3f, r.get("x").getReal(), 0.0001d);

        // Altera para condição resultar em false
        parametros.put("b", new Valor(1f));

        // Novo valor para a mesma regra (condição false)
        r = as.avalia(regras, relatos, null, parametros);
        assertEquals(2, r.size());
        assertEquals(0f, r.get("x").getReal(), 0.0001d);
    }

    private Regra getConstante() {
        return new RegraExpressao("x", "d", 10f, 0f, "3");
    }

    private Regra getExpressao() {
        return new RegraExpressao("x", "d", 10f, 0f, "a + (2 - 3)");
    }

    private Regra getCondicao() {
        return new RegraCondicional("x", "d", 10f, 0f, "2 < b", "b", "0");
    }

    @Test
    public void testesDeArquivosJson() throws IOException {
        String contents = getContentOfFile("configuracao.json");
        Serializador sz = new Serializador();
        Configuracao c = sz.configuracao(contents);
    }

    private String getContentOfFile(String name) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(name).getFile());
        String cfgFile = file.getAbsolutePath();
        Path path = Paths.get(cfgFile);

        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}

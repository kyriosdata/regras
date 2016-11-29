# regras
Biblioteca para Execução de Regras (BER). 

[<img src="https://api.travis-ci.org/kyriosdata/saep.svg?branch=master">](https://travis-ci.org/kyriosdata/saep)
[![Dependency Status](https://www.versioneye.com/user/projects/5818f81589f0a91d55eb921c/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5818f81589f0a91d55eb921c)
[![Sonarqube](https://sonarqube.com/api/badges/gate?key=com.github.kyriosdata.regras:regras)](https://sonarqube.com/dashboard/index?id=com.github.kyriosdata.regras%3Aregras)
[![Javadocs](http://javadoc.io/badge/com.github.kyriosdata.regras/regras.svg)](http://javadoc.io/doc/com.github.kyriosdata.regras/regras)

<br />
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">
<img alt="Creative Commons License" style="border-width:0"
 src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a>
 <br />This work is licensed under a <a rel="license" 
 href="http://creativecommons.org/licenses/by/4.0/">Creative Commons 
 Attribution 4.0 International License</a>. 
 <br />Fábio Nogueira de Lucena - Fábrica de Software - 
 Instituto de Informática (UFG).

## Como usar (via maven)?

Acrescente a dependencia abaixo no arquivo pom.xml:

<pre>
&lt;dependency&gt;
  &lt;groupId&gt;com.github.kyriosdata.regras&lt;/groupId&gt;
  &lt;artifactId&gt;regras&lt;/artifactId&gt;
  &lt;version&gt;1.0.0&lt;/version&gt;
&lt;/dependency&gt;
</pre>

## O que é uma regra?
Regra é o instrumento empregado para produzir um valor para uma
dada entrada. A entrada é definida por um conjunto de objetos (JSON). O 
resultado é um valor numérico, uma data, um valor lógico ou uma 
sequência de caracteres. A saída também é fornecida como um objeto
JSON. Em tempo, já não é surpresa, uma regra também é definida por um objeto 
JSON.

Abaixo segue uma regra do tipo expressão que soma os valores das 
variáveis "x" e "y". O resultado é associado à variável identificada 
por "soma". A variável deve ser única em um conjunto de regras, 
ou seja, não é permitido outra regra que deve ser avaliada juntamente 
com aquela abaixo e também define como variável o identificador 
"soma".

`{ "tipo": "expressao", "variavel": "soma", 
   "expressao": "x + y" }`
   
Uma regra pode possuir vários atributos, alguns deles são predefinidos, 
por exemplo, "valorMaximo". Enquanto a regra acima pode resultar no 
valor 150, por exemplo, para os valores de x e y, 100 e 50, respectivamente,
aquela abaixo teria como resultado o valor 107 para os mesmos valores de
x e y, pois 107 é o valor máximo indicado para essa regra.

`{ "variavel": "soma", 
   "expressao": "x + y",
    "valorMaximo" : 107 }`
    
## O que é uma configuração?
Em geral estamos interessados na avaliação de várias regras. Configuração
é o meio que permite reunir várias regras. Abaixo segue uma configuração 
que reúne as duas regras citadas acima.

`{ "regras" : [
   { "variavel": "soma", 
   "expressao": "x + y",
    "valorMaximo" : 107 },
    { "tipo": "expressao", "variavel": "soma", 
       "expressao": "x + y" }]}`

## Quais são as entradas/saídas?
As entradas são definids por objetos JSON. Os atributos desses
objetos, quaisquer que sejam eles, são tratados como variáveis. 
Por exemplo, para o objeto JSON abaixo

`{ "altura" : 1.8 }`

fornecido como entrada para a execução da regra 

`{ "tipo" : "expressao", "variavel" : "alturaEmCentimetros", "expressao" : "altura * 100" }`

o resultado produzido é 180, que passa a estar disponível por meio da 
variável "alturaEmCentimetros" no objeto JSON retornado

`{ "valores" : [ { "alturaEmCentimetros": 180 } ] }`
    
## Como executar um conjunto de regras?

A execução de uma regra produz um valor, associado à variável em 
questão, ou seja, um objeto JSON correspondente típico é 
ilustrado abaixo.

`{ "alturaEmCentimetros" : 180 }`

Quando um conjunto de regras é executado, temos como resultado
um conjunto de valores (objetos como aqueles acima). Um objeto 
resultado em JSON é ilustrado abaixo.

`{ "valores" : [ { "x": 1 }, { "quente": true } ] }`
 
A execução de várias regras, uma configuração, produz uma
coleção de valores, conforme ilustrado pelo método abaixo. 

```
String configuracao = // recupera conjunto de regras (JSON)
String relatos = // recupera conjunto de objetos (JSON)
String parametros = // recupera valores iniciais (JSON)
String resultado = Regras.avalia(configuracao, relatos, parametros);
```

Nesse ponto podemos ilustrar uma chamada completa para avaliação de
uma regra. 


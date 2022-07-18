Prova de Conceito Eduardo A. T Borges
=================

Aplicação desenvolvida nos padrões de API Spring boot.
-------------------
Configurações de aplicação:
- As configurações de aplicação estão descritas no arquivo application.properties que pode ser encontrado no path src.main.resources
- Deixei configurado por padrão como create (spring.jpa.hibernate.ddl-auto=create) para que a estrutura na base h2 seja criada
sempre que a aplicação subir.
- Caso seja necessário trocar o host ou nome da base de dados deve-se utilizar a classe BaseDadosConfiguracao (br.com.poc.configuracao.BaseDadosConfiguracao)
e não no arquivo application.properties. Isso se dá pelo fato de reconhecer o host de conexões (localhost, homologação, produção, etc..)
em ambientes orquestrados.


Passos para popular filmes
-------------------
- Deverá ser colocado o arquivo CSV no caminho: C://movielist.csv (Por motivo de tempo não coloquei no contexto da aplicação)

- Ao subir a aplicação, deve-se chamar o endpoint de carregamento dos dados 
http://localhost:8080/provaEduardo-api/movies/load

- Consultar os filmes importados
http://localhost:8080/provaEduardo-api/movies/list

- Consultar os vencedores
http://localhost:8080/provaEduardo-api/movies/winner

OBSERVAÇÕES:
- Implementei nos services mas não disponibilizei os métodos de update e remove por questão de tempo de entrega.
- O retorno dos vencedores não está retornando uma lista de mínimo e máximo, apenas 1 produtor MIN e 1 produtor MAX com os devidos anos.
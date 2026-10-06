# Trabalho de Grupo — Veículos e Garagem (Java + Maven)

Projeto desenvolvido para a disciplina de PSI. Implementa três tipos de veículos (`Carro`, `Mota`, `Barco`) e uma `Garagem` que os gere, seguindo o enunciado fornecido pelo professor. O projeto usa Maven para gestão de dependências e testes automáticos com JUnit 5.

## Estrutura do projeto

isaque/
├── pom.xml
├── README.md
└── src/
├── main/java/epbjc/java/
│ ├── Main.java -> menu interativo (teste manual)
│ ├── interfaces/
│ │ ├── Veiculo.java -> contrato comum a todos os veículos
│ │ └── TipoVeiculo.java -> enum: CARRO, MOTA, BARCO
│ ├── modelos/
│ │ ├── Carro.java
│ │ ├── Mota.java
│ │ └── Barco.java
│ └── garagem/
│ └── Garagem.java
└── test/java/ -> testes automáticos (JUnit)


## Conceitos de POO demonstrados

| Conceito | Onde aparece |
|---|---|
| Interfaces | `Veiculo`, implementada por `Carro`, `Mota` e `Barco` |
| Enumerações | `TipoVeiculo` |
| Encapsulamento | Todos os atributos são `private`, acedidos via getters/setters |
| Polimorfismo | `Garagem` guarda `List<Veiculo>`, aceitando qualquer tipo de veículo |
| Validação de dados | Todos os construtores e setters validam os valores recebidos |
| Tratamento de exceções | `IllegalArgumentException` para dados inválidos, `IllegalStateException` para ações impossíveis no estado atual |
| `equals`/`hashCode` | Dois veículos são iguais se tiverem o mesmo tipo e a mesma matrícula |

## Regras de cada veículo

| | **Carro** | **Mota** | **Barco** |
|---|---|---|---|
| `getTipo()` | `CARRO` | `MOTA` | `BARCO` |
| Formato da matrícula | `AA-00-AA` | `AA-00-AA` | `AA-0000` |
| Ano mínimo | 1886 | 1885 | 1850 |
| `getNumeroRodas()` | 4 | 2 | 0 |
| Campo próprio | `numeroPortas` (2 a 5) | `cilindrada` (50 a 2000 cc) | `comprimento` (0 a 100 m) |

- A matrícula é validada e normalizada (sem espaços nas pontas, em maiúsculas) no construtor.
- O ano aceite vai do mínimo da classe até ao **ano atual + 1**, calculado dinamicamente com `java.time.Year.now()`.
- `matricula` e `ano` são campos `final` — não têm setter, não podem ser alterados depois de criado o objeto.
- `quilometros` começa sempre em `0` e só pode aumentar (nunca diminuir).

## Garagem

- Criada com nome, capacidade (1 a 100) e os tipos de veículo que aceita.
- **`entrar(veiculo)`**: adiciona um veículo, validando que não é `null`, que o tipo é aceite, que há vaga, e que a matrícula ainda não está ocupada.
- **`sair(matricula)`**: remove e devolve o veículo com essa matrícula (aceita minúsculas e espaços).
- **`podeEntrar(veiculo)`**: verifica todas as condições de entrada sem lançar exceções nem alterar a garagem — útil para testar antes de agir.
- **`isCheia()` / `isVazia()`**: estado da garagem.
- **`getVeiculos()` / `getTiposPermitidos()`**: devolvem cópias, para impedir que o código fora da classe altere a garagem por fora.

## `Main.java` — menu interativo

O `Main.java` serve apenas para teste manual (não é avaliado pelos testes automáticos). Foi feito como um questionário no terminal:

1. O utilizador escolhe adicionar um Carro, Mota ou Barco.
2. A matrícula é **gerada automaticamente**, já no formato correto de cada veículo.
3. O utilizador só preenche os restantes dados (marca, modelo, ano, cor e o campo próprio).
4. É possível listar os veículos na garagem e remover um pela matrícula.
5. Erros de validação (ex: ano inválido, garagem cheia) são apanhados e mostrados sem fechar o programa.

## Como executar

### Pré-requisitos
- Java 17 (JDK)
- Maven

### Correr o menu interativo
No VS Code, abrir `Main.java` e clicar em **Run** por cima do método `main`.

Ou, pelo terminal:
```bash
mvn compile
mvn exec:java "-Dexec.mainClass=epbjc.java.Main"
```

### Correr os testes automáticos
```bash
mvn test                      # todos os testes
mvn test -Dgroups=CARRO       # só o Carro
mvn test -Dgroups=MOTA        # só a Mota
mvn test -Dgroups=BARCO       # só o Barco
mvn test -Dgroups=GARAGEM     # só a Garagem
mvn test -Dgroups=INTEGRACAO  # tudo junto
```

O trabalho está completo quando aparece `BUILD SUCCESS` e todos os testes passam.

## Regras gerais seguidas

- Nomes de classes, pacotes e assinaturas públicas mantidos exatamente como pedido no enunciado.
- Nenhum ficheiro em `src/test/java` foi alterado.
- Um `set` inválido lança a exceção correspondente e **não** altera o valor anterior.
- Dado inválido → `IllegalArgumentException`. Ação impossível no estado atual → `IllegalStateException`.
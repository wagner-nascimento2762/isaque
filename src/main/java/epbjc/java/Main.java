package epbjc.java;

import epbjc.java.garagem.Garagem;
import epbjc.java.interfaces.TipoVeiculo;
import epbjc.java.interfaces.Veiculo;
import epbjc.java.modelos.Barco;
import epbjc.java.modelos.Carro;
import epbjc.java.modelos.Mota;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static Garagem garagem = new Garagem("Garagem Central", 5,
            TipoVeiculo.CARRO, TipoVeiculo.MOTA, TipoVeiculo.BARCO);

    public static void main(String[] args) {
        int opcao;
        do {
            mostrarMenu();
            opcao = lerInteiro("Escolhe uma opção: ");

            try {
                switch (opcao) {
                    case 1 -> adicionarCarro();
                    case 2 -> adicionarMota();
                    case 3 -> adicionarBarco();
                    case 4 -> garagem.getVeiculos().forEach(System.out::println);
                    case 5 -> removerVeiculo();
                    case 0 -> System.out.println("A sair...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);

        scanner.close();
    }

    static void mostrarMenu() {
        System.out.println("\n=== " + garagem.getNome()
                + " (" + garagem.getOcupacao() + "/" + garagem.getCapacidade() + ") ===");
        System.out.println("1 - Adicionar Carro");
        System.out.println("2 - Adicionar Mota");
        System.out.println("3 - Adicionar Barco");
        System.out.println("4 - Listar veículos");
        System.out.println("5 - Remover veículo");
        System.out.println("0 - Sair");
    }

    static int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Escreve apenas um número inteiro.");
            }
        }
    }

    static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Escreve apenas um número (ex: 7.5).");
            }
        }
    }

    static String lerTexto(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Gera duas letras aleatórias (A-Z)
    static String gerarDuasLetras() {
        char c1 = (char) ('A' + random.nextInt(26));
        char c2 = (char) ('A' + random.nextInt(26));
        return "" + c1 + c2;
    }

    // Gera matrícula no formato AA-00-AA (Carro e Mota)
    static String gerarMatriculaCarroMota() {
        String letras1 = gerarDuasLetras();
        String numeros = String.format("%02d", random.nextInt(100));
        String letras2 = gerarDuasLetras();
        return letras1 + "-" + numeros + "-" + letras2;
    }

    // Gera matrícula no formato AA-0000 (Barco)
    static String gerarMatriculaBarco() {
        String letras = gerarDuasLetras();
        String numeros = String.format("%04d", random.nextInt(10000));
        return letras + "-" + numeros;
    }

    static void adicionarCarro() {
        String matricula = gerarMatriculaCarroMota();
        String marca = lerTexto("Marca: ");
        String modelo = lerTexto("Modelo: ");
        int ano = lerInteiro("Ano: ");
        String cor = lerTexto("Cor: ");
        int portas = lerInteiro("Número de portas (2-5): ");

        Carro carro = new Carro(matricula, marca, modelo, ano, cor, portas);
        garagem.entrar(carro);
        System.out.println("Carro adicionado: " + carro);
    }

    static void adicionarMota() {
        String matricula = gerarMatriculaCarroMota();
        String marca = lerTexto("Marca: ");
        String modelo = lerTexto("Modelo: ");
        int ano = lerInteiro("Ano: ");
        String cor = lerTexto("Cor: ");
        int cilindrada = lerInteiro("Cilindrada (50-2000cc): ");

        Mota mota = new Mota(matricula, marca, modelo, ano, cor, cilindrada);
        garagem.entrar(mota);
        System.out.println("Mota adicionada: " + mota);
    }

    static void adicionarBarco() {
        String matricula = gerarMatriculaBarco();
        String marca = lerTexto("Marca: ");
        String modelo = lerTexto("Modelo: ");
        int ano = lerInteiro("Ano: ");
        String cor = lerTexto("Cor: ");
        double comprimento = lerDouble("Comprimento em metros (0-100): ");

        Barco barco = new Barco(matricula, marca, modelo, ano, cor, comprimento);
        garagem.entrar(barco);
        System.out.println("Barco adicionado: " + barco);
    }

    static void removerVeiculo() {
        garagem.getVeiculos().forEach(System.out::println);
        String matricula = lerTexto("Matrícula do veículo a remover: ");
        Veiculo removido = garagem.sair(matricula);
        System.out.println("Removido: " + removido);
    }
}
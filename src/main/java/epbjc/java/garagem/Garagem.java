package epbjc.java.garagem;

import epbjc.java.interfaces.TipoVeiculo;
import epbjc.java.interfaces.Veiculo;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class Garagem {

    private String nome;
    private int capacidade;
    private final Set<TipoVeiculo> tiposPermitidos;
    private final List<Veiculo> veiculos;

    public Garagem(String nome, int capacidade, TipoVeiculo... tipos) {
        this.nome = validarNome(nome);
        this.capacidade = validarCapacidade(capacidade);
        if (tipos == null || tipos.length == 0) {
            throw new IllegalArgumentException("É preciso indicar pelo menos um tipo de veículo.");
        }
        Set<TipoVeiculo> conjunto = EnumSet.noneOf(TipoVeiculo.class);
        for (TipoVeiculo tipo : tipos) {
            if (tipo == null) {
                throw new IllegalArgumentException("Não pode haver tipos nulos.");
            }
            conjunto.add(tipo);
        }
        this.tiposPermitidos = conjunto;
        this.veiculos = new ArrayList<>();
    }

    private static String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        return nome.trim();
    }

    private static int validarCapacidade(int capacidade) {
        if (capacidade < 1 || capacidade > 100) {
            throw new IllegalArgumentException("Capacidade tem de estar entre 1 e 100.");
        }
        return capacidade;
    }

    public String getNome() { return nome; }

    public int getCapacidade() { return capacidade; }

    public Set<TipoVeiculo> getTiposPermitidos() {
        return EnumSet.copyOf(tiposPermitidos);
    }

    public List<Veiculo> getVeiculos() {
        return new ArrayList<>(veiculos);
    }

    public int getOcupacao() { return veiculos.size(); }

    public int getLugaresLivres() { return capacidade - veiculos.size(); }

    public void setNome(String nome) { this.nome = validarNome(nome); }

    public void setCapacidade(int capacidade) {
        int nova = validarCapacidade(capacidade);
        if (nova < getOcupacao()) {
            throw new IllegalStateException("A nova capacidade não pode ser menor do que a ocupação atual.");
        }
        this.capacidade = nova;
    }

    public boolean isCheia() { return getOcupacao() == capacidade; }

    public boolean isVazia() { return veiculos.isEmpty(); }

    public boolean aceitaTipo(TipoVeiculo tipo) {
        if (tipo == null) return false;
        return tiposPermitidos.contains(tipo);
    }

    public int contarPorTipo(TipoVeiculo tipo) {
        if (tipo == null) return 0;
        int contador = 0;
        for (Veiculo v : veiculos) {
            if (v.getTipo() == tipo) contador++;
        }
        return contador;
    }

    public boolean contem(String matricula) {
        if (matricula == null) return false;
        String limpa = matricula.trim().toUpperCase();
        for (Veiculo v : veiculos) {
            if (v.getMatricula().equalsIgnoreCase(limpa)) return true;
        }
        return false;
    }

    public boolean podeEntrar(Veiculo v) {
        if (v == null) return false;
        if (getLugaresLivres() <= 0) return false;
        if (!aceitaTipo(v.getTipo())) return false;
        if (contem(v.getMatricula())) return false;
        return true;
    }

    public void entrar(Veiculo v) {
        if (v == null) {
            throw new IllegalArgumentException("Veículo não pode ser nulo.");
        }
        if (!aceitaTipo(v.getTipo())) {
            throw new IllegalArgumentException("Esta garagem não aceita este tipo de veículo.");
        }
        if (isCheia()) {
            throw new IllegalStateException("A garagem está cheia.");
        }
        if (contem(v.getMatricula())) {
            throw new IllegalStateException("Já existe um veículo com esta matrícula na garagem.");
        }
        veiculos.add(v);
    }

    public Veiculo sair(String matricula) {
        if (matricula == null || matricula.trim().isEmpty()) {
            throw new IllegalArgumentException("Matrícula inválida.");
        }
        String limpa = matricula.trim().toUpperCase();
        for (int i = 0; i < veiculos.size(); i++) {
            if (veiculos.get(i).getMatricula().equalsIgnoreCase(limpa)) {
                return veiculos.remove(i);
            }
        }
        throw new IllegalArgumentException("Não existe nenhum veículo com esta matrícula na garagem.");
    }
}
package epbjc.java.modelos;

import epbjc.java.interfaces.TipoVeiculo;
import epbjc.java.interfaces.Veiculo;

import java.time.Year;
import java.util.Objects;
import java.util.regex.Pattern;

public class Mota implements Veiculo {

    public static final String REGEX_MATRICULA = "^[A-Z]{2}-\\d{2}-[A-Z]{2}$";
    public static final int ANO_MINIMO = 1885;
    private static final int NUMERO_RODAS = 2;

    private final String matricula;
    private String marca;
    private String modelo;
    private final int ano;
    private String cor;
    private double quilometros;
    private int cilindrada;

    public Mota(String matricula, String marca, String modelo, int ano, String cor, int cilindrada) {
        this.matricula = validarMatricula(matricula);
        this.marca = validarTexto(marca, "marca");
        this.modelo = validarTexto(modelo, "modelo");
        this.ano = validarAno(ano);
        this.cor = validarTexto(cor, "cor");
        this.quilometros = 0;
        setCilindrada(cilindrada);
    }

    private static String validarMatricula(String matricula) {
        if (matricula == null) {
            throw new IllegalArgumentException("Matrícula obrigatória.");
        }
        String limpa = matricula.trim().toUpperCase();
        if (!Pattern.matches(REGEX_MATRICULA, limpa)) {
            throw new IllegalArgumentException("Matrícula inválida. Formato esperado: AA-00-AA");
        }
        return limpa;
    }

    private static String validarTexto(String valor, String nomeCampo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException(nomeCampo + " é obrigatório.");
        }
        return valor.trim();
    }

    private static int validarAno(int ano) {
        int anoMaximo = Year.now().getValue() + 1;
        if (ano < ANO_MINIMO || ano > anoMaximo) {
            throw new IllegalArgumentException(
                "Ano inválido. Tem de estar entre " + ANO_MINIMO + " e " + anoMaximo + ".");
        }
        return ano;
    }

    @Override
    public TipoVeiculo getTipo() { return TipoVeiculo.MOTA; }

    @Override
    public String getMatricula() { return matricula; }

    @Override
    public String getMarca() { return marca; }

    @Override
    public String getModelo() { return modelo; }

    @Override
    public int getAno() { return ano; }

    @Override
    public String getCor() { return cor; }

    @Override
    public double getQuilometros() { return quilometros; }

    @Override
    public int getNumeroRodas() { return NUMERO_RODAS; }

    public int getCilindrada() { return cilindrada; }

    public void setMarca(String marca) { this.marca = validarTexto(marca, "marca"); }
    public void setModelo(String modelo) { this.modelo = validarTexto(modelo, "modelo"); }
    public void setCor(String cor) { this.cor = validarTexto(cor, "cor"); }

    public void setQuilometros(double quilometros) {
        if (Double.isNaN(quilometros) || Double.isInfinite(quilometros) || quilometros < 0) {
            throw new IllegalArgumentException("Quilómetros inválidos.");
        }
        if (quilometros < this.quilometros) {
            throw new IllegalArgumentException("Os quilómetros não podem diminuir.");
        }
        this.quilometros = quilometros;
    }

    public void setCilindrada(int cilindrada) {
        if (cilindrada < 50 || cilindrada > 2000) {
            throw new IllegalArgumentException("Cilindrada tem de estar entre 50 e 2000 cc.");
        }
        this.cilindrada = cilindrada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Veiculo)) return false;
        Veiculo outro = (Veiculo) o;
        return getTipo() == outro.getTipo() && matricula.equals(outro.getMatricula());
    }

    @Override
    public int hashCode() { return Objects.hash(getTipo(), matricula); }

    @Override
    public String toString() {
        return getTipo() + " " + matricula + " " + marca + " " + modelo + " (" + ano + ")";
    }
}
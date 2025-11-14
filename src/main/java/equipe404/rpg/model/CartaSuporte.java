package equipe404.rpg.model;

public class CartaSuporte extends Carta {
    private double poderModificador;
    private String efeito;

    public CartaSuporte(String nome, double poder, int custo, String efeito, String descricao) {
        super(nome, "suporte", custo, descricao);

        this.poderModificador = poder;
        this.efeito = efeito;
    }

    public double getPoderModificador() {
        return this.poderModificador;
    }

    public String getEfeito() {
        return this.efeito;
    }

//    @Override
//    public String toString() {
//        return "SUPORTE: " + nome + " | Custo: " + custoEnergia + " | Efeito: " + efeito + " | Valor: " + poder;
//    }
}

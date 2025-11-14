package equipe404.rpg.model;

public class CartaAtaque extends Carta {

    private int poderAtaque;

    public CartaAtaque(String nome, int poder, int custo, String descricao) {
        super(nome, "ataque", custo, descricao);

        this.poderAtaque = poder;
    }

    public int getPoderAtaque() {
        return this.poderAtaque;
    }

//    @Override
//    public String toString() {
//        return "ATAQUE: " + nome + " | Custo: " + custo + " | Poder: " + poderAtaque;
//    }
}

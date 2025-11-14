package equipe404.rpg.model;

public class CartaDefesa extends Carta {

    private int poderDefesa;

    public CartaDefesa(String nome, int poder, int custo, String descricao) {
        super(nome, "defesa", custo, descricao);

        this.poderDefesa = poder;
    }

    public int getPoderDefesa() {
        return this.poderDefesa;
    }

//    @Override
//    public String toString() {
//        return "DEFESA: " + nome + " | Custo: " + custoEnergia + " | Poder: " + poderDefesa;
//    }
}

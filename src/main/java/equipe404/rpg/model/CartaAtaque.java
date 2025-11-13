package equipe404.rpg.model;

public class CartaAtaque {
    private String nome;
    private int custo;
    private int poderAtaque;

    public CartaAtaque(String nome, int custo, int poderAtaque) {
        this.nome = nome;
        this.custo = custo;
        this.poderAtaque = poderAtaque;
    }

    public String getNome() {return nome;}
    public int getCusto() {return custo;}
    public int getPoderAtaque() {return poderAtaque;}

    @Override
    public String toString() {
        return "ATAQUE: " + nome + " | Custo: " + custo + " | Poder: " + poderAtaque;
    }
}

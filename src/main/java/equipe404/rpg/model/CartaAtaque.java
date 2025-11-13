package equipe404.rpg.model;

public class CartaAtaque {
    private String nome;
    private int custoEnergia;
    private int poderAtaque;

    public CartaAtaque(String nome, int custoEnergia, int poderAtaque) {
        this.nome = nome;
        this.custoEnergia = custoEnergia;
        this.poderAtaque = poderAtaque;
    }

    public String getNome() {return nome;}
    public int getCustoEnergia() {return custoEnergia;}
    public int getPoderAtaque() {return poderAtaque;}

    public String toString() {
        return "ATAQUE: " + nome + " | Custo: " + custoEnergia + " | Poder: " + poderAtaque;
    }
}

package equipe404.rpg.model;

public class CartaDefesa {
    private String nome;
    private int custoEnergia;
    private int poderDefesa;

    public CartaDefesa(String nome, int custoEnergia, int poderDefesa) {
        this.nome = nome;
        this.custoEnergia = custoEnergia;
        this.poderDefesa = poderDefesa;
    }

    public String getNome() {return nome;}
    public int getCustoEnergia() {return custoEnergia;}
    public int getPoderDefesa() {return poderDefesa;}

    public String toString() {
        return "DEFESA: " + nome + " | Custo: " + custoEnergia + " | Poder: " + poderDefesa;
    }
}

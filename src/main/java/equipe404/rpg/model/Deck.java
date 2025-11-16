package equipe404.rpg.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private ArrayList <CartaAtaque> cartasAtaque;
    private ArrayList <CartaDefesa> cartasDefesa;
    private ArrayList <CartaSuporte> cartasSuporte;

    public Deck (){
        cartasAtaque = new ArrayList<>();
        cartasDefesa = new ArrayList<>();
        cartasSuporte = new ArrayList<>();
    }

    //---Ataque---
    public void addAtaque(CartaAtaque carta) {
        cartasAtaque.add(carta);
    }
    public ArrayList<CartaAtaque> getCartasAtaque() {
        return cartasAtaque;
    }

    //---Defesa---
    public void addDefesa(CartaDefesa carta) {
        cartasDefesa.add(carta);
    }
    public ArrayList<CartaDefesa> getCartasDefesa() {
        return cartasDefesa;
    }

    //---Suporte---
    public void addSuporte(CartaSuporte carta) {
        cartasSuporte.add(carta);
    }
    public ArrayList<CartaSuporte> getCartasSuporte() {
        return cartasSuporte;
    }

    //---EMBARALHAR---
    public void embaralhar() {
        Collections.shuffle(cartasAtaque);
        Collections.shuffle(cartasDefesa);
        Collections.shuffle(cartasSuporte);
    }

    public void imprimirCartas() {
        System.out.println("Cartas de ATAQUE: ");
        for(CartaAtaque cAtaque : cartasAtaque) {
            System.out.println(cAtaque + " ");
        }

        System.out.println("Cartas de DEFESA: ");
        for(CartaDefesa cDefesa : cartasDefesa) {
            System.out.println(cDefesa + " ");
        }

        System.out.println("Cartas de SUPORTE: ");
        for(CartaSuporte cSuporte : cartasSuporte) {
            System.out.println(cSuporte + " ");
        }
    }

    //---DECK COMPLETO---

    public List<Carta> montaDeckCompleto() {
        List<Carta> deck = new ArrayList<>();

        deck.addAll(this.cartasAtaque);
        deck.addAll(this.cartasDefesa);
        deck.addAll(this.cartasSuporte);
        return deck;
    }

    public int getTamanhoDeck() {
        return montaDeckCompleto().size();
    }

    //remove as cartas que foram escolhidas
    public void removeCarta(Carta cartaSelecionada){
        String tipo = cartaSelecionada.getTipo();
        if(tipo.equals("ataque")){
            cartasAtaque.remove(cartaSelecionada);
        }
        if(tipo.equals("defesa")){
            cartasDefesa.remove(cartaSelecionada);
        }
        if(tipo.equals("suporte")){
            cartasSuporte.remove(cartaSelecionada);
        }
    }
}

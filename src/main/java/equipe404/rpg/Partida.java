package equipe404.rpg;
import java.util.Scanner;

import equipe404.rpg.model.*;

public class Partida {
    // Atributos
    private Hacker hacker1;
    private Hacker hacker2;
    private boolean h1Comeca;

    public Partida(Hacker hacker1, Hacker hacker2) {
        this.hacker1 = hacker1;
        this.hacker2 = hacker2;
        this.h1Comeca = true;
    }


    public void iniciar() {
        Scanner scannerDuelo = new Scanner(System.in);

        System.out.println("\n====== A PARTIDA VAI COMEÇAR! ======");
        System.out.println(hacker1.getNome() + " VS " + hacker2.getNome());
        System.out.println("=====================================");

        while (hacker1.getHp() > 0 && hacker2.getHp() > 0) {
            System.out.println("--- NOVO TURNO ---");

            Hacker primeiroAJogar = (h1Comeca) ? hacker1 : hacker2;
            Hacker segundoAJogar = (h1Comeca) ? hacker2 : hacker1;

            System.out.println("É a vez de: " + primeiroAJogar.getNome());
            primeiroAJogar.jogarCarta();

            // Evita que o segundo jogador ataque depois de morto
            if (segundoAJogar.getHp() > 0) {
                System.out.println("É a vez de: " + segundoAJogar.getNome());
                segundoAJogar.jogarCarta();
            }

            // Calcular dano, cura, etc.

            // recarregar energia, arredondar vida, etc.

            h1Comeca = !h1Comeca;
        }
        // Implementar lógica de finalizar partida
    }
}
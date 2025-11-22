package equipe404.rpg;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorReplay {
    // Lista para guardar o histórico do jogo em memória
    private List<String> logPartida;

    public GerenciadorReplay() {
        this.logPartida = new ArrayList<>();
    }

    // Método para registrar eventos
    public void registrar(String evento) {
        this.logPartida.add(evento);
    }

    // Método para salvar o arquivo no final
    public void gerarArquivoReplay() {
        String nomeArquivo = "replay_partida.txt";

        try (FileWriter fileWriter = new FileWriter(nomeArquivo);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            for (String linha : logPartida) {
                printWriter.println(linha);
            }

            System.out.println("Replay salvo com sucesso em: " + nomeArquivo);

        } catch (IOException e) {
            System.out.println("Erro ao salvar o replay: " + e.getMessage());
        }
    }
}

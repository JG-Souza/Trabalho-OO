package equipe404.rpg;

import equipe404.rpg.model.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GerenciadorCartas {
    // Para não perder as instancias e para renderizar para o jogador
    public static List<CartaAtaque> catalogoDeAtaque = new ArrayList<>();
    public static List<CartaDefesa> catalogoDeDefesa = new ArrayList<>();
    public static List<CartaSuporte> catalogoDeSuporte = new ArrayList<>();

    public static void carregarCartas() {
        // Ataque
        List<String[]> arrayAtaque = lerCSV("ataque.csv");
        for (String[] dados : arrayAtaque) { // Sintaxe do forEach em Java
            String nome = dados[0];
            int poder = Integer.parseInt(dados[2].trim());
            int custo = Integer.parseInt(dados[3].trim());
            String descricao = dados[4];
            catalogoDeAtaque.add(new CartaAtaque(nome, poder, custo, descricao));
        }

        // Defesa
        List<String[]> arrayDefesa = lerCSV("defesa.csv");
        for (String[] dados : arrayDefesa) { // Sintaxe do forEach em Java
            String nome = dados[0];
            int poder = Integer.parseInt(dados[2].trim());
            int custo = Integer.parseInt(dados[3].trim());
            String descricao = dados[4];
            catalogoDeDefesa.add(new CartaDefesa(nome, poder, custo, descricao));
        }

        // Suporte
        List<String[]> arraySuporte = lerCSV("suporte.csv");
        for (String[] dados : arraySuporte) { // Sintaxe do forEach em Java
            String nome = dados[0];
            double poder = Double.parseDouble(dados[2].trim());
            int custo = Integer.parseInt(dados[3].trim());
            String efeito = dados[4];
            String descricao = dados[5];
            catalogoDeSuporte.add(new CartaSuporte(nome, poder, custo, efeito, descricao));
        }
    }


    // Le os dados do arquivo e retorna uma lista de arrays com esses dados, sendo cada array uma linha do arquivo
    private static List<String[]> lerCSV(String nomeArquivo) {
        Path caminho = Paths.get(nomeArquivo);
        try (Stream<String> linhas = Files.lines(caminho)) { // linhas armazena o fluxo de strings, onde cada string é uma linha do arquivo
            return linhas
                    .skip(1) // Pula o cabeçalho
                    .map(linha -> linha.split(",")) // Quebra a linha do registro em um array de strings, separados por virgulas
                    .collect(Collectors.toList()); // Pega todos os arrays e coloca dentro de uma lista
        } catch (IOException e) { // Se o arquivo não for encontrado na raiz
            System.err.println("ERRO: Não foi possível ler o arquivo: " + nomeArquivo);
            return Collections.emptyList();
        }
    }


    public static void exibirCatalogo(String tipo) {
        if (tipo.equals("ataque")) {
            System.out.println("\n--- Cartas de ATAQUE Disponíveis ---");
            imprimirListaEmColunas(catalogoDeAtaque);
        } else if (tipo.equals("defesa")) {
            System.out.println("\n--- Cartas de DEFESA Disponíveis ---");
            imprimirListaEmColunas(catalogoDeDefesa);
        } else if (tipo.equals("suporte")) {
            System.out.println("\n--- Cartas de SUPORTE Disponíveis ---");
            imprimirListaEmColunas(catalogoDeSuporte);
        }
    }


    private static void imprimirListaEmColunas(List<? extends Carta> lista) {
        final int COLUNAS = 2;
        final String FORMATO_COLUNA = "%-40s"; // 35 caracteres de largura

        if (lista.isEmpty()) {
            System.out.println("Nenhuma carta disponível.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            Carta carta = lista.get(i);
            String infoCarta = String.format("[%d] %s (Custo: %d)",
                    (i + 1),
                    carta.getNome(),
                    carta.getCusto());

            System.out.printf(FORMATO_COLUNA, infoCarta);

            if ((i + 1) % COLUNAS == 0 || i == lista.size() - 1) {
                System.out.println();
            }
        }
    }
}

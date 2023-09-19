import java.util.Random;
import java.util.Scanner;

public class MonteCarloJoice {
    /*
     *  Vai pedir quantos digitos vai coletar
     *  Depois vai pedir as posiçoes, da direita pra esquerda digite os valores que quer
     *  O codigo baseia em valores definidos e sua frequencia, então deve ser digitado
     *  Para facilitar um dos exercicios 2 2 3 5 4 0,15 7 0,15 8 0,38 13 0,25 14 0,07
     *  Faz o calculo baseado nas frequencias ditas.
     */
    public static void main(String[] args) {
        long[][] tabela = new long[100][100];
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        int[] posicoes;
        int[] qntsColetas = { 40, 400, 4000, 40000, 400000, 4000000 };
        double[][] tabelaEx;
        long[] escolhidos;

        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela[i].length; j++) {
                long numeroAleatorio = (long) (rand.nextDouble() * 9_000_000_000L + 1_000_000_000L);
                tabela[i][j] = numeroAleatorio;
            }
        }
        // 2 7 3 5 4 0,15 7 0,15 8 0,38 13 0,25 14 0,07
        System.out.println("Digite quantas Coletas sera feita: (1-10)");
        int quantDigito = sc.nextInt();
        posicoes = new int[quantDigito];
        System.out.println("Agora digite as posições");
        for (int i = 0; i < quantDigito; i++) {
            posicoes[i] = sc.nextInt() - 1;
        }

        System.out.println("Informe quantos valores da tabela: ");
        int quntValor = sc.nextInt();
        tabelaEx = new double[quntValor][2];
        System.out.println("Valor e Frequencia: (4 0,15)");
        for (int i = 0; i < quntValor; i++) {
            tabelaEx[i][0] = sc.nextInt();
            tabelaEx[i][1] = sc.nextDouble();
        }

        for (int qntColetas : qntsColetas) {
            escolhidos = new long[qntColetas];
            escolhidos = realizarColetas(tabela, qntColetas, posicoes);
            double contagem = somaValores(escolhidos, tabelaEx, quantDigito, qntColetas);

            double estiMonteCarlo = (double) contagem / qntColetas;

            System.out.println("Coleta " + qntColetas);
            System.out.println("Estimativa Monte Carlo: " + estiMonteCarlo);
        }
    }

    private static long[] realizarColetas(long[][] tabela, int qntsColetas, int[] posicaoDesejada) {
        int linhas = tabela.length;
        int colunas = tabela[0].length;
        long[] escolhidos = new long[qntsColetas];
        int indiceEscolhidos = 0; // coletado todos os valores

        for (int coluna = 0; coluna < colunas; coluna++) {
            for (int linha = 0; linha < linhas; linha++) {
                if (indiceEscolhidos >= qntsColetas) {
                    // Se coletou deve sair;
                    break;
                }
                StringBuilder numeroCombinado = new StringBuilder();
                String numeroComoString = String.valueOf(tabela[linha][coluna]);
                int aux1;

                for (int h = 0; h < posicaoDesejada.length; h++) {
                    int posicoes = posicaoDesejada[h];
                    aux1 = numeroComoString.charAt(posicoes);

                    numeroCombinado.append(Character.getNumericValue(aux1));
                }
                if (numeroCombinado.length() > 0) {
                    escolhidos[indiceEscolhidos] = Integer.parseInt(numeroCombinado.toString());
                    indiceEscolhidos++;
                }
            }
        }
        return escolhidos;
    }

    private static double somaValores(long[] escolhidos, double[][] tabelaEx, int quantDigito, int qntColetas) {
        double soma = 0;
        double aux1 = 0;
        double aux2 = 0;
        for (int i = 0; i < tabelaEx.length; i++) {
            for (int j = 0; j < qntColetas; j++) {
                aux2 = (tabelaEx[i][1] * (Math.pow(10, quantDigito))) + aux1;
                if (escolhidos[j] >= aux1 && escolhidos[j] < aux2) {
                    soma += tabelaEx[i][0];
                    aux1 = 0;
                } 
            }
            aux1 = aux2;
        }
        return soma;
    }
}

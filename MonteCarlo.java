import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class MonteCarlo {
    
    public static void main(String[] args) {
        int tamTabela = 100; //100 x 100
        long[][] tabela = new long[tamTabela][tamTabela];

        preencherTabela(tabela);
        Scanner sc = new Scanner(System.in);
        
        System.out.print("quantos Digitos? (1-10) ");
        int qntDigitos = sc.nextInt();
        
        System.out.print("Qual posição? (0-" + (qntDigitos - 1) + ") ");
        int posicaoDesejada = sc.nextInt();
        
        sc.close();

        //Amostras
        int[] qntsColetas = {40, 400, 4000, 40000, 400000, 4000000};
        
        for (int qntColetas : qntsColetas) {
            int contagem = realizarColetas(tabela, qntColetas, posicaoDesejada);
            
            double estiMonteCarlo = (double) contagem / qntColetas;
            
            System.out.println("Coleta "+ qntColetas);
            System.out.println("Estimativa Monte Carlo: " + estiMonteCarlo);
        }
    }

    
    /*
     * ======================================================
     *              Preenche tabela = 10 digitos
     * ======================================================
     */
    private static void preencherTabela(long[][] tabela) {

        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j < tabela[i].length; j++) {
               tabela[i][j] = ThreadLocalRandom.current().nextLong(10000000000L); // Gera valores de 10 dígitos
            }
        }
    }

    
    /*
     * ======================================================
     *              Coleta e contar valores desejados
     * ======================================================
     * 
     * Essa função coleta quantas vezes o numero saiu na posição desejada 
     */
    private static int realizarColetas(long[][] tabela, int qntColetas, int posicaoDesejada) {
        Random random = new Random();
        int contagem = 0;

        for (int i = 0; i < qntColetas; i++) {
            int linha = random.nextInt(tabela.length);
            int coluna = random.nextInt(tabela[0].length); 

            long valor = tabela[linha][coluna];
            
            long dígitoDesejado = exDigito(valor, posicaoDesejada);
            if (dígitoDesejado == 0) {
                contagem++;
            }
        }
        return contagem;
    }

    /*
     * ======================================================
     *              Extrai um digito de um número
     * ======================================================
     */
    private static long exDigito(long numero, int posicaoDesejada) {
        for (int i = 0; i < posicaoDesejada; i++) {
            numero /= 10;
        }
        return numero % 10;
    }
}

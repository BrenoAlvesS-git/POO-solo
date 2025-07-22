package loja.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.*;
import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in); //Cria um scaner pra todos os metodos

    public static String lerString(String texto){
        System.out.println(texto);
        return scanner.nextLine();
    }
    public static void pausarParaContinuar() {
    System.out.print("\nPressione Enter para continuar...");
    scanner.nextLine(); 
}
    public static LocalDate lerData(String texto){
        DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while(true){
            System.out.print(texto);
            String input = scanner.nextLine().trim();
            if(input.isEmpty()){
                System.out.println("Erro, data vazia");
                continue;
            }
            try{
                LocalDate date = LocalDate.parse(input,formataData);
                return date;
            }catch(DateTimeParseException e){
                System.out.println("Formato invalido");
            }

        }
    }

    public static BigDecimal lerPreco(String mensagem) {
        while (true) { // Inicia um loop infinito que só será quebrado por um 'return'.
            System.out.print(mensagem);
            String inputDoUsuario = scanner.nextLine().trim().replace(',', '.'); // remove espaços e troca vírgula por ponto
            // Vê se o campo não ficou vazio
            if (inputDoUsuario.isEmpty()) {
                System.out.println("Erro: O preço não pode ser vazio. Tente novamente.");
                continue; //continue pra repetir caso erros 
            }

            try {
                // Tenta converter a string lida para um BigDecimal.
                BigDecimal preco = new BigDecimal(inputDoUsuario);

                // `compareTo` retorna -1 se o número for menor, 0 se for igual, 1 se for maior
                if (preco.compareTo(BigDecimal.ZERO) < 0) {
                    // O preço é negativo
                    System.out.println("Erro: O preço não pode ser um valor negativo. Tente novamente.");
                    continue; // Volta
                }

                
                return preco;

            } catch (NumberFormatException e) {
                // Se a conversão `new BigDecimal(input)` falhar, este bloco é executado.
                System.out.println("Erro: Formato de número inválido. Use um ponto como separador decimal (ex: 25.99).");
            }
        }
    }

}

package com.challenge;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
// --- NOVAS IMPORTACOES ---
import java.time.LocalDateTime; // Para o Log (data e hora)
import java.time.format.DateTimeFormatter; // Para formatar a data e hora
import java.util.ArrayList; // Para o Historico
import java.util.List; // Para o Historico

public class App {
    public static void main(String[] args) {
        
        Scanner leitura = new Scanner(System.in);
        ClienteApi cliente = new ClienteApi();
        Gson gson = new Gson();
        MoedaResponse respostaApi = null;

        // --- FEATURE 1 e 3: Historico e Log ---
        // Vamos usar uma Lista de Strings para guardar nosso log/historico
        List<String> historicoDeConversao = new ArrayList<>();
        
        // Vamos definir o formato da data e hora para os logs
        DateTimeFormatter formatadorDeLog = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        // --- FIM FEATURE 1 e 3 ---

        // --- ARTES ASCII (mantidas) ---
        String arteUSD = "     _______ \n    |  ___  |\n    | | $ | |\n    | |___| |\n    |_______|";
        String arteBRL = "    ( ( \n   ) ) ) \n ......... \n |       |] \n \\~~~~~~~/ \n  `-----'";
        String arteEUR = "     .----. \n    /  .--' \n   |  | \n    \\  '--. \n     '----'";
        String arteJPY = "     /\\ \n    (OO) \n   /~~\\ \n  |----| \n /| || |\\ \n(_| || |_) \n   '--'";
        String arteARS = "    _    \n   /_\\   \n  | $ |  \n  |ARS|  \n  |___|"; // Peso Argentino
        String arteCNY = "    .--. \n   | == | \n   |----| \n   | == | \n    '--'"; // Yuan Chines

        // Trazemos as cotações UMA VEZ no início
        try {
            System.out.println("Buscando cotacoes atualizadas..."); 
            String jsonResposta = cliente.buscaCotacoes("USD");
            respostaApi = gson.fromJson(jsonResposta, MoedaResponse.class);

            if (!respostaApi.result().equals("success")) {
                System.out.println("Erro ao buscar cotacoes da API. Encerrando.");
                return;
            }
            System.out.println("Cotacoes carregadas!\n");

        } catch (IOException | InterruptedException e) {
            System.out.println("Erro de conexao ao buscar cotacoes: " + e.getMessage());
            return;
        } catch (Exception e) {
            System.out.println("Erro ao processar dados da API: " + e.getMessage());
            return;
        }

        // --- INÍCIO DO LOOP DO MENU ---
        while (true) {
            
            // --- FEATURE 2: Menu com Mais Moedas ---
            System.out.println("***********************************************");
            System.out.println("Seja bem-vindo(a) ao Conversor de Moedas!");
            System.out.println("\n1) [$] Dolar (USD) => [R$] Real (BRL)");
            System.out.println("2) [R$] Real (BRL) => [$] Dolar (USD)");
            System.out.println("3) [$] Dolar (USD) => [€] Euro (EUR)");
            System.out.println("4) [€] Euro (EUR) => [$] Dolar (USD)");
            System.out.println("5) [$] Dolar (USD) => [¥] Iene (JPY)");
            System.out.println("6) [¥] Iene (JPY) => [$] Dolar (USD)");
            System.out.println("7) [$] Dolar (USD) => [ARS] Peso (ARS)");
            System.out.println("8) [ARS] Peso (ARS) => [$] Dolar (USD)");
            System.out.println("9) [$] Dolar (USD) => [CNY] Yuan (CNY)");
            System.out.println("10) [CNY] Yuan (CNY) => [$] Dolar (USD)");
            System.out.println("\n--- Outras Opcoes ---");
            System.out.println("11) Ver Historico de Conversoes");
            System.out.println("12) Sair");
            System.out.println("\nEscolha uma opcao valida:");
            System.out.println("***********************************************");

            try {
                int opcao = leitura.nextInt();
                
                // --- MUDANCA: Sair agora e opcao 12 ---
                if (opcao == 12) {
                    System.out.println("Obrigado por usar o conversor. Encerrando...");
                    break; 
                }

                // --- FEATURE 1: Ver Historico ---
                if (opcao == 11) {
                    System.out.println("\n--- Historico de Conversoes ---");
                    if (historicoDeConversao.isEmpty()) {
                        System.out.println("Nenhuma conversao foi realizada ainda.");
                    } else {
                        // Loop para imprimir cada item da lista
                        for (String registro : historicoDeConversao) {
                            System.out.println(registro);
                        }
                    }
                    System.out.println("----------------------------------\n");
                    continue; // Pula de volta para o inicio do loop 'while'
                }
                
                System.out.println("Digite o valor que deseja converter:");
                double valorParaConverter = leitura.nextDouble();

                // --- FEATURE 2: Pegar mais taxas ---
                double taxaBRL = respostaApi.taxasDeConversao().get("BRL");
                double taxaEUR = respostaApi.taxasDeConversao().get("EUR");
                double taxaJPY = respostaApi.taxasDeConversao().get("JPY");
                double taxaARS = respostaApi.taxasDeConversao().get("ARS"); // Nova
                double taxaCNY = respostaApi.taxasDeConversao().get("CNY"); // Nova
                
                double valorConvertido = 0;
                String logDaConversao = ""; // Para o historico

                switch (opcao) {
                    case 1: // USD -> BRL
                        System.out.println(arteUSD + "\n\n  PARA\n" + arteBRL);
                        valorConvertido = valorParaConverter * taxaBRL;
                        logDaConversao = String.format("$%.2f (USD) -> R$%.2f (BRL)", valorParaConverter, valorConvertido);
                        break;
                    
                    case 2: // BRL -> USD
                        System.out.println(arteBRL + "\n\n  PARA\n" + arteUSD);
                        valorConvertido = valorParaConverter / taxaBRL;
                        logDaConversao = String.format("R$%.2f (BRL) -> $%.2f (USD)", valorParaConverter, valorConvertido);
                        break;
                    
                    case 3: // USD -> EUR
                        System.out.println(arteUSD + "\n\n  PARA\n" + arteEUR);
                        valorConvertido = valorParaConverter * taxaEUR;
                        logDaConversao = String.format("$%.2f (USD) -> €%.2f (EUR)", valorParaConverter, valorConvertido);
                        break;
                    
                    case 4: // EUR -> USD
                        System.out.println(arteEUR + "\n\n  PARA\n" + arteUSD);
                        valorConvertido = valorParaConverter / taxaEUR;
                        logDaConversao = String.format("€%.2f (EUR) -> $%.2f (USD)", valorParaConverter, valorConvertido);
                        break;
                    
                    case 5: // USD -> JPY
                        System.out.println(arteUSD + "\n\n  PARA\n" + arteJPY);
                        valorConvertido = valorParaConverter * taxaJPY;
                        logDaConversao = String.format("$%.2f (USD) -> ¥%.2f (JPY)", valorParaConverter, valorConvertido);
                        break;

                    case 6: // JPY -> USD
                        System.out.println(arteJPY + "\n\n  PARA\n" + arteUSD);
                        valorConvertido = valorParaConverter / taxaJPY;
                        logDaConversao = String.format("¥%.2f (JPY) -> $%.2f (USD)", valorParaConverter, valorConvertido);
                        break;

                    // --- FEATURE 2: Novas Conversoes ---
                    case 7: // USD -> ARS
                        System.out.println(arteUSD + "\n\n  PARA\n" + arteARS);
                        valorConvertido = valorParaConverter * taxaARS;
                        logDaConversao = String.format("$%.2f (USD) -> $%.2f (ARS)", valorParaConverter, valorConvertido);
                        break;

                    case 8: // ARS -> USD
                        System.out.println(arteARS + "\n\n  PARA\n" + arteUSD);
                        valorConvertido = valorParaConverter / taxaARS;
                        logDaConversao = String.format("$%.2f (ARS) -> $%.2f (USD)", valorParaConverter, valorConvertido);
                        break;

                    case 9: // USD -> CNY
                        System.out.println(arteUSD + "\n\n  PARA\n" + arteCNY);
                        valorConvertido = valorParaConverter * taxaCNY;
                        logDaConversao = String.format("$%.2f (USD) -> ¥%.2f (CNY)", valorParaConverter, valorConvertido);
                        break;

                    case 10: // CNY -> USD
                        System.out.println(arteCNY + "\n\n  PARA\n" + arteUSD);
                        valorConvertido = valorParaConverter / taxaCNY;
                        logDaConversao = String.format("¥%.2f (CNY) -> $%.2f (USD)", valorParaConverter, valorConvertido);
                        break;
                    // --- FIM FEATURE 2 ---

                    default:
                        System.out.println("Opcao invalida. Tente novamente.\n");
                        continue; // Pula o Log e volta ao inicio
                }

                // --- FEATURE 1 e 3: Criando o Log e Adicionando ao Historico ---
                // Pegamos a data e hora ATUAL
                String timestamp = LocalDateTime.now().format(formatadorDeLog);
                
                // Criamos a entrada completa do log
                String registroDeLog = String.format("[%s] %s", timestamp, logDaConversao);
                
                // Adicionamos ao nosso historico
                historicoDeConversao.add(registroDeLog);
                
                // Imprime o resultado formatado (que estava no log)
                System.out.println("Resultado: " + logDaConversao + "\n");

            } catch (InputMismatchException e) {
                System.out.println("\nErro: Por favor, digite um numero valido para a opcao ou valor.");
                leitura.next(); 
            }
        } // Fim do loop 'while'

        leitura.close(); 
    }
}
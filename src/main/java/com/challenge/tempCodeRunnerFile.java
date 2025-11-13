package com.challenge;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        
        // 1. Cria uma instância do nosso cliente
        ClienteApi cliente = new ClienteApi();

        try {
            // 2. Chama o método para buscar cotações do Dólar (USD)
            String jsonResposta = cliente.buscaCotacoes("USD");

            // 3. Imprime o resultado no console
            System.out.println("Resposta da API:");
            System.out.println(jsonResposta);

        } catch (IOException | InterruptedException e) {
            // Tratamento de erro básico caso a internet falhe ou a API dê problema
            System.out.println("Erro ao buscar cotações: " + e.getMessage());
        }
    }
}
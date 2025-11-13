package com.challenge;

// Importações necessárias para fazer chamadas HTTP (incluídas no Java 11+)
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ClienteApi {

    // Método que busca as cotações
    // Ele pode "lançar" exceções (erros) que quem o chamou (o App.java) terá que tratar
    public String buscaCotacoes(String moedaBase) throws IOException, InterruptedException {

        // **IMPORTANTE:** Substitua "SUA_CHAVE_API_AQUI" pela sua chave real!
        String suaChaveApi = "SUA_CHAVE_API_AQUI"; 
        String url = "https://v6.exchangerate-api.com/v6/" + suaChaveApi + "/latest/" + moedaBase;

        // 1. Cria um "cliente" HTTP
        // O HttpClient é quem de fato vai fazer a chamada de rede
        HttpClient client = HttpClient.newHttpClient();

        // 2. Cria a "requisição" HTTP
        // O HttpRequest é o "pacote" que diz para onde ir (URI) e o que fazer (GET)
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // 3. Envia a requisição e recebe a "resposta"
        // client.send() envia a 'request' e espera uma 'response'
        // HttpResponse.BodyHandlers.ofString() diz que queremos a resposta como um texto (String)
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // 4. Retorna o corpo da resposta (o JSON que vimos no Postman!)
        return response.body();
    }
}
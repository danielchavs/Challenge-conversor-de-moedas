package com.challenge;

// Importa o "apelido" que o Gson usará
import com.google.gson.annotations.SerializedName;

// Importa o Map, que usaremos para as cotações
import java.util.Map;

/**
 * Este é um "Record" (molde) para o Gson entender o JSON da API.
 * Nós só pedimos 3 campos: o resultado, a moeda base, e o mapa de cotações.
 */
public record MoedaResponse(
    String result,

    @SerializedName("base_code") // Diz ao Gson: o campo "base_code" do JSON vai para esta variável
    String moedaBase,

    @SerializedName("conversion_rates") // O campo "conversion_rates" do JSON vai para este mapa
    Map<String, Double> taxasDeConversao
) {
}
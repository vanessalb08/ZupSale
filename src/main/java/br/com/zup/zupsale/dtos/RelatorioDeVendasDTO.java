package br.com.zup.zupsale.dtos;

import lombok.Data;

@Data
public class RelatorioDeVendasDTO {
    private Integer id, qtdDeEntrada, qtdDeSaida;
    private  Double porcentagemDeVendas;

}

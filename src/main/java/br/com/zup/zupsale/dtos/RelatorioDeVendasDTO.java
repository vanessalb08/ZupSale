package br.com.zup.zupsale.dtos;

import br.com.zup.zupsale.enuns.StatusEstoque;
import lombok.Data;

@Data
public class RelatorioDeVendasDTO {
    private Integer id, qtdDeEntrada, qtdDeSaida;
    private  Double porcentagemDeVendas;
    private StatusEstoque statusEstoque;

}

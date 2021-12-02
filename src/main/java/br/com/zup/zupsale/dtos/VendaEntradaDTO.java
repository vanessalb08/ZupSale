package br.com.zup.zupsale.dtos;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
public class VendaEntradaDTO {
    @NotNull(message = "{validacao.not-null}")
    private Integer id, quantidade;
    @DecimalMin(value = "0.01", message = "{validacao.valor-minimo}")
    private Double porcentagemDeLucro;

}

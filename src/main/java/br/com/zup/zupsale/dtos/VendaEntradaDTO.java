package br.com.zup.zupsale.dtos;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
public class VendaEntradaDTO {
    @NotNull
    private Integer id, quantidade;
    @DecimalMin(value = "0.01", message = "Valor tem que ser maior que zero")
    private Double porcentagemDeLucro;

}

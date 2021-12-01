package br.com.zup.zupsale.dtos;

import lombok.Data;

@Data
public class VendaDTO {
    private Integer id, quantidade;
    private Double valorDaVenda;

}

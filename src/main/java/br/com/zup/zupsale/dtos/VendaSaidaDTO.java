package br.com.zup.zupsale.dtos;

import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.DecimalMin;

@Data
public class VendaSaidaDTO {
    private int id;
    private int quantidade;
    private double valorTotalDaVenda;
}

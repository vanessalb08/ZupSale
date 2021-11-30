package br.com.zup.zupsale.dtos;

import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.enuns.Genero;
import lombok.Data;

@Data
public class ResumoCadastroDTO {
    private int id;
    private Genero genero;
    private Categoria categoria;
    private String cor, marca, modelo;
    private Integer tamanho, quantidadeDeEstoque;
    private Double valorDaCompra;

}

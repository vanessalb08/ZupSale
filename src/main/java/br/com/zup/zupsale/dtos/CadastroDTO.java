package br.com.zup.zupsale.dtos;

import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.enuns.Genero;
import br.com.zup.zupsale.models.Estoque;
import lombok.Data;

@Data
public class CadastroDTO {
    private Genero genero;
    private Categoria categoria;
    private String cor, marca;
    private Integer tamanho, quantidadeDeEstoque;
    private Double valorDaCompra;

}

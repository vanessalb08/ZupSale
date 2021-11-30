package br.com.zup.zupsale.dtos;

import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.enuns.Genero;
import br.com.zup.zupsale.models.Estoque;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CadastroDTO {
    private Genero genero;
    private Categoria categoria;
    @Size(min = 3, message = "{validacao.size}")
    private String cor, marca;
    @NotNull(message = "{validacao.not-null}")
    private Integer tamanho, quantidadeDeEstoque;
    @NotNull(message = "{validacao.not-null}")
    private Double valorDaCompra;

}

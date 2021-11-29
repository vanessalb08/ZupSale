package br.com.zup.zupsale.models;

import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.enuns.Genero;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Calcado {
    private Genero genero;
    private Categoria categoria;
    private String cor, marca;
    private Integer tamanho, quantidadeDeEstoque;
    private Double valorDaCompra;
    private LocalDateTime dataDeCadastro = LocalDateTime.now();

}

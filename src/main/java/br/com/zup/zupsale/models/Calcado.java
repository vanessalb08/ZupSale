package br.com.zup.zupsale.models;

import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.enuns.Genero;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class Calcado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Genero genero;
    private Categoria categoria;
    private String cor, marca;
    private Integer tamanho, quantidadeDeEstoque;
    private Double valorDaCompra;
    private LocalDateTime dataDeCadastro = LocalDateTime.now();

}

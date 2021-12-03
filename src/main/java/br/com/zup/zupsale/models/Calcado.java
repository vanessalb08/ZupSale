package br.com.zup.zupsale.models;

import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.enuns.Genero;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "calcados")
public class Calcado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @Column(nullable = false, columnDefinition = "VARCHAR(50)")
    private String cor, marca, modelo;
    @Column(nullable = false)
    private Integer tamanho, quantidadeDeEstoque;
    @Column(nullable = false)
    private Double valorDaCompra;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataDeCadastro;
    private Integer qtdDeEntrada = 0;
    private Integer qtdDeSaida = 0;
    private  Double porcentagemDeVendas = 0.0;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Estoque estoque;

}

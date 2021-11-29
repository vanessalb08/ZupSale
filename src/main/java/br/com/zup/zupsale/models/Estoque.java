package br.com.zup.zupsale.models;

import br.com.zup.zupsale.enuns.Categoria;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "estoques")
public class Estoque {
    @Id
    @Column(columnDefinition = "VARCHAR(50)")
    private String nomeEstoque;
    @OneToMany
    private List<Calcado> calcados = new ArrayList<>();
    @Column(nullable = false)
    private Double quantidade;

}

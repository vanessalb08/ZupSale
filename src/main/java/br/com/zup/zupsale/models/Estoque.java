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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany
    private List<Calcado> calcados = new ArrayList<>();
    @Column(nullable = false)
    private Double quantidade;

}

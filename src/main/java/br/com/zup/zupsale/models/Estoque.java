package br.com.zup.zupsale.models;

import lombok.Data;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Estoque {
    private List<Calcado> calcados = new ArrayList<>();
    private Double quantidade;

}

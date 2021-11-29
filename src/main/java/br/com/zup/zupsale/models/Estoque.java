package br.com.zup.zupsale.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Estoque {
    private List<Calcado> calcados = new ArrayList<>();
    private Double quantidade;

}

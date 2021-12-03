package br.com.zup.zupsale.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusEstoque {
    ABAIXO("Estoque está abaixo de 50%"),
    ADEQUADO("Estoque está adequado"),
    ZERADO("Estoque zerado");

    private String mensagem;
}

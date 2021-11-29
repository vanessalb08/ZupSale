package br.com.zup.zupsale.repositories;

import br.com.zup.zupsale.models.Calcado;
import br.com.zup.zupsale.models.Estoque;
import org.springframework.data.repository.CrudRepository;

public interface EstoqueRepository extends CrudRepository<Estoque, String> {
}

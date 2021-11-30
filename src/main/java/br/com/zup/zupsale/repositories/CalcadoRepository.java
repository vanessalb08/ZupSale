package br.com.zup.zupsale.repositories;

import br.com.zup.zupsale.models.Calcado;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CalcadoRepository extends CrudRepository<Calcado, Integer> {

    List<Calcado> findAllByTamanho(Integer tamanho);

    List<Calcado> findAllByMarca(String marca);
}

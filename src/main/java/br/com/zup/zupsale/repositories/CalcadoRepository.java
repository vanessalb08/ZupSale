package br.com.zup.zupsale.repositories;

import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.models.Calcado;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CalcadoRepository extends CrudRepository<Calcado, Integer> {

    List<Calcado> findAllByTamanho(Integer tamanho);

    List<Calcado> findAllByMarca(String marca);

    List<Calcado> findAllByCategoria(Categoria categoria);

    @Query(value = "SELECT * FROM calcados WHERE valor_da_compra BETWEEN :valor*0.85 AND :valor*1.15",nativeQuery = true)
    List<Calcado> findAllByValorDaCompraBetween(double valor);

    @Query(value = "SELECT * FROM calcados c WHERE c.categoria = ?1 AND c.genero = ?2", nativeQuery = true)
    List<Calcado> findAllByCategoriaAndGenero(String categoria, String genero);

    boolean existsByModeloIgnoreCase(String modelo);

    List<Calcado> findAllByModeloIgnoreCase(String modelo);
}

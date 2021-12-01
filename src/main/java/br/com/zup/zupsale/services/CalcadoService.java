package br.com.zup.zupsale.services;

import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.enuns.Genero;
import br.com.zup.zupsale.exceptions.CalcadoNaoLocalizadoException;
import br.com.zup.zupsale.models.Calcado;
import br.com.zup.zupsale.repositories.CalcadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CalcadoService {

    @Autowired
    private CalcadoRepository calcadoRepository;

    public void cadastrarCalcado(Calcado calcado) {
        calcado.setDataDeCadastro(LocalDateTime.now());
        calcadoRepository.save(calcado);

    }

    public List<Calcado> listarCalcados() {
        Iterable<Calcado> listaCalcados = calcadoRepository.findAll();
        return (List<Calcado>) listaCalcados;
    }

    public List<Calcado> buscarCalcados(Integer tamanho, String marca, Categoria categoria, Genero genero,
                                        Double valor) {
        if (categoria != null & genero != null){
            return calcadoRepository.findAllByCategoriaEGenero(categoria, genero);
        }
        else if (tamanho != null) {
            return calcadoRepository.findAllByTamanho(tamanho);
        }
        else if (marca != null){
            return calcadoRepository.findAllByMarca(marca);
        }
        else if (categoria != null){
            return calcadoRepository.findAllByCategoria(categoria);
        }
        else if (valor != null){
            return calcadoRepository.findAllByValorDaCompraBetween(valor);
        }
        return listarCalcados();
    }

    public void deletarCalcado(int id){
        if (calcadoRepository.existsById(id)) {
            calcadoRepository.deleteById(id);
        } else {
            throw new CalcadoNaoLocalizadoException("Calçado não localizado!");
        }
    }

    public int quantidadeTotalCalcado(){
        int  total = 0;
        for (Calcado calcadoReferencia : listarCalcados()){
            total +=calcadoReferencia.getQuantidadeDeEstoque();
        }
        return total;
    }

    public void efetuarVenda(Integer id, Integer quantidadeDeVenda) {
        if (quantidadeTotalCalcado() >= 0) {
            if (calcadoRepository.existsById(id)) {
                Optional<Calcado> calcado = calcadoRepository.findById(id);
                Integer qtdTotal = calcado.get().getQuantidadeDeEstoque();
                if (quantidadeDeVenda <= qtdTotal) {
                    Integer qtdAtualizada = qtdTotal - quantidadeDeVenda;
                    calcado.get().setQuantidadeDeEstoque(qtdAtualizada);
                    calcadoRepository.save(calcado.get());
                }
                else {
                    throw new RuntimeException("A quantidade da venda deve ser menor do que o estoque");
                }

            } else {
                throw new RuntimeException("Id não encontrado");
            }
        }
    }
}

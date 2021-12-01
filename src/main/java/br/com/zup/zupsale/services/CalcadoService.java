package br.com.zup.zupsale.services;

import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.exceptions.CalcadoNaoLocalizadoException;
import br.com.zup.zupsale.models.Calcado;
import br.com.zup.zupsale.repositories.CalcadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public List<Calcado> buscarCalcados(Integer tamanho, String marca, Categoria categoria, Double valor) {
        if (tamanho != null) {
            return calcadoRepository.findAllByTamanho(tamanho);
        }
        if (marca != null){
            return calcadoRepository.findAllByMarca(marca);
        }
        if (categoria != null){
            return calcadoRepository.findAllByCategoria(categoria);
        }
        if (valor != null){
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

}

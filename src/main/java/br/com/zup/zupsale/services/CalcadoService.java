package br.com.zup.zupsale.services;

import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.enuns.Genero;
import br.com.zup.zupsale.exceptions.CalcadoNaoLocalizadoException;
import br.com.zup.zupsale.exceptions.EstoqueInsuficienteException;
import br.com.zup.zupsale.exceptions.IdNaoEncontradoException;
import br.com.zup.zupsale.models.Calcado;
import br.com.zup.zupsale.repositories.CalcadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CalcadoService {

    @Autowired
    private CalcadoRepository calcadoRepository;

    public void cadastrarCalcado(Calcado calcadoASerCadastrado) {
        verificarModelo(calcadoASerCadastrado);
        if (!verificarEstoque(calcadoASerCadastrado)){
            calcadoASerCadastrado.setDataDeCadastro(LocalDateTime.now());
            calcadoRepository.save(calcadoASerCadastrado);
            atualizarQtdDeEntrada(calcadoASerCadastrado.getId(),
                    calcadoASerCadastrado.getQuantidadeDeEstoque());
        }
    }


    public  void verificarModelo(Calcado calcado){
        if (calcadoRepository.existsByModeloIgnoreCase(calcado.getModelo())){
            for (Calcado calcadoReferencia : calcadoRepository.findAllByModeloIgnoreCase(calcado.getModelo())) {
                if (!Objects.equals(calcado.getCategoria(), calcadoReferencia.getCategoria()) ||
                        !Objects.equals(calcado.getMarca(), calcadoReferencia.getMarca())) {
                    throw new RuntimeException("Tipo ou marca divergente");
                }
            }
        }
    }

    public boolean verificarEstoque(Calcado calcadoASerCadastrado){
        boolean estoqueAtualizado = false;
        for (Calcado calcado : calcadoRepository.findAllByModeloIgnoreCase(
                calcadoASerCadastrado.getModelo())) {
            if (calcado.getCor().equalsIgnoreCase(calcadoASerCadastrado.getCor()) &
                    calcado.getGenero().equals(calcadoASerCadastrado.getGenero()) &
                    calcado.getTamanho().equals(calcadoASerCadastrado.getTamanho())){
                Integer qtdEstoque = calcado.getQuantidadeDeEstoque();
                Integer qtdCadastro = calcadoASerCadastrado.getQuantidadeDeEstoque();
                Integer qtdAtualizada = qtdEstoque + qtdCadastro;
                calcado.setQuantidadeDeEstoque(qtdAtualizada);
                calcadoRepository.save(calcado);
                atualizarQtdDeEntrada(calcado.getId(), qtdCadastro);
                estoqueAtualizado = true;
            }
        }
        return estoqueAtualizado;
    }



    public List<Calcado> listarCalcados() {
        Iterable<Calcado> listaCalcados = calcadoRepository.findAll();
        return (List<Calcado>) listaCalcados;
    }

    public List<Calcado> buscarCalcados(Integer tamanho, String marca, Categoria categoria, Genero genero,
                                        Double valor) {
        if (categoria != null & genero != null) {
            return calcadoRepository.findAllByCategoriaAndGenero(categoria.name(), genero.name());
        } else if (tamanho != null) {
            return calcadoRepository.findAllByTamanho(tamanho);
        } else if (marca != null) {
            return calcadoRepository.findAllByMarca(marca);
        } else if (categoria != null) {
            return calcadoRepository.findAllByCategoria(categoria);
        } else if (valor != null) {
            return calcadoRepository.findAllByValorDaCompraBetween(valor);
        }
        return listarCalcados();
    }

    public void deletarCalcado(int id) {
        if (calcadoRepository.existsById(id)) {
            calcadoRepository.deleteById(id);
        } else {
            throw new CalcadoNaoLocalizadoException("Calçado não localizado!");
        }
    }

    public int quantidadeTotalCalcado() {
        int total = 0;
        for (Calcado calcadoReferencia : listarCalcados()) {
            total += calcadoReferencia.getQuantidadeDeEstoque();
        }
        return total;
    }
    public Calcado buscarCalcadoPorId(Integer id) {
        Optional<Calcado> calcado = calcadoRepository.findById(id);
        if (calcado.isPresent()) {
            return calcado.get();
        } else {
            throw new IdNaoEncontradoException("Id não encontrado");
        }

    }
    public double calcularValorVenda(int id, double porcentagemLucro, int quantidade) {
        Calcado calcado = buscarCalcadoPorId(id);
        double valorDaCompra = calcado.getValorDaCompra();
        double valorDaVenda = quantidade * (valorDaCompra + (valorDaCompra * porcentagemLucro));
        return valorDaVenda;
    }

    public void efetuarVenda(Integer id, Integer quantidadeDeVenda) {
        if (quantidadeTotalCalcado() >= 0) {
            Calcado calcado = buscarCalcadoPorId(id);
            Integer qtdTotal = calcado.getQuantidadeDeEstoque();
            if (quantidadeDeVenda <= qtdTotal) {
                Integer qtdAtualizada = qtdTotal - quantidadeDeVenda;
                calcado.setQuantidadeDeEstoque(qtdAtualizada);
                calcadoRepository.save(calcado);
            } else {
                throw new EstoqueInsuficienteException("A quantidade da venda deve ser menor do que o estoque");
            }
        }
    }

    public void atualizarQtdDeEntrada(Integer id, Integer qtdDeEntrada){
        Calcado calcado = buscarCalcadoPorId(id);
        Integer atualizacaoDeEntranda = calcado.getQtdDeEntrada() + qtdDeEntrada;
        calcado.setQtdDeEntrada(atualizacaoDeEntranda);
        calcadoRepository.save(calcado);
    }

}

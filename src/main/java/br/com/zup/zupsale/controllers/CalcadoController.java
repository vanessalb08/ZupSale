package br.com.zup.zupsale.controllers;

import br.com.zup.zupsale.dtos.*;
import br.com.zup.zupsale.dtos.VendaEntradaDTO;
import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.enuns.Genero;
import br.com.zup.zupsale.models.Calcado;
import br.com.zup.zupsale.services.CalcadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/zupsale")
public class CalcadoController {

    @Autowired
    private CalcadoService calcadoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void cadastrarCalcado(@RequestBody @Valid CadastroDTO cadastroDTO) {
        Calcado calcado = modelMapper.map(cadastroDTO, Calcado.class);
        calcadoService.cadastrarCalcado(calcado);
    }

    @GetMapping
    public List<ResumoCadastroDTO> buscarCalcados(@RequestParam(required = false) Integer tamanho,
                                                  @RequestParam(required = false) String marca,
                                                  @RequestParam(required = false) Categoria categoria,
                                                  @RequestParam(required = false) Double valor,
                                                  @RequestParam(required = false) Genero genero) {
        List<ResumoCadastroDTO> listaResumo = new ArrayList<>();

        for (Calcado calcado : calcadoService.buscarCalcados(tamanho, marca, categoria, genero, valor)) {
            ResumoCadastroDTO resumo = modelMapper.map(calcado, ResumoCadastroDTO.class);
            listaResumo.add(resumo);
        }
        return listaResumo;

    }

    @GetMapping("/estoque")
    public ResumoEstoqueDTO totalDoEstoque (){
        ResumoEstoqueDTO resumoEstoqueDTO = new ResumoEstoqueDTO();
        resumoEstoqueDTO.setQuantidadeTotal(calcadoService.quantidadeTotalCalcado());
        return resumoEstoqueDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarCadastro(@PathVariable int id){
        calcadoService.deletarCalcado(id);
    }

    @PutMapping("/venda")
    public VendaSaidaDTO efetuarVenda(@RequestBody VendaEntradaDTO vendaEntradaDTO){
        VendaSaidaDTO vendaSaidaDTO = new VendaSaidaDTO();
        calcadoService.efetuarVenda(vendaEntradaDTO.getId(), vendaEntradaDTO.getQuantidade());
        double valor = calcadoService.calcularValorVenda(
                vendaEntradaDTO.getId(),
                vendaEntradaDTO.getPorcentagemDeLucro(),
                vendaEntradaDTO.getQuantidade()
        );
        double valorFormatado = Math.round(valor*100.0)/100.0;
        vendaSaidaDTO = modelMapper.map(vendaEntradaDTO, VendaSaidaDTO.class);
        vendaSaidaDTO.setValorTotalDaVenda(valorFormatado);
        return vendaSaidaDTO;
    }

    @GetMapping("/relatorio{id}")
    public List<RelatorioDeVendasDTO> exibirRelatorioDeVendas(@RequestParam Integer id){
        calcadoService.atualizarPorcentagemDeVendas(id);
        List<RelatorioDeVendasDTO> relatorioDeVendasDTOS = new ArrayList<>();
        for (Calcado calcado : calcadoService.listarCalcados()) {
            RelatorioDeVendasDTO relatorioDeVendas = modelMapper.map(
                    calcado, RelatorioDeVendasDTO.class);
            relatorioDeVendasDTOS.add(relatorioDeVendas);
        }
        return relatorioDeVendasDTOS;
    }

}



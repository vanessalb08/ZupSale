package br.com.zup.zupsale.controllers;

import br.com.zup.zupsale.dtos.*;
import br.com.zup.zupsale.dtos.VendaEntradaDTO;
import br.com.zup.zupsale.enuns.Categoria;
import br.com.zup.zupsale.enuns.Genero;
import br.com.zup.zupsale.enuns.StatusEstoque;
import br.com.zup.zupsale.models.Calcado;
import br.com.zup.zupsale.services.CalcadoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/zupsale")
@Api(value = "zupsale")
@CrossOrigin(origins = "*")
public class CalcadoController {

    @Autowired
    private CalcadoService calcadoService;

    @Autowired
    private ModelMapper modelMapper;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Realizar novo cadastro de calçado")
    public void cadastrarCalcado(@RequestBody @Valid CadastroDTO cadastroDTO) {
        Calcado calcado = modelMapper.map(cadastroDTO, Calcado.class);
        calcadoService.cadastrarCalcado(calcado);
    }

    @GetMapping
    @ApiOperation(value = "Realizar busca de calçado")
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
    @ApiOperation(value = "Listar o estoque")
    public ResumoEstoqueDTO totalDoEstoque (){
        ResumoEstoqueDTO resumoEstoqueDTO = new ResumoEstoqueDTO();
        resumoEstoqueDTO.setQuantidadeTotal(calcadoService.quantidadeTotalCalcado());
        return resumoEstoqueDTO;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deletar calçado do estoque")
    public void deletarCadastro(@PathVariable int id){
        calcadoService.deletarCalcado(id);
    }

    @PutMapping("/venda")
    @ApiOperation(value = "Efetuar Venda")
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
        calcadoService.atualizarPorcentagemDeVendas(vendaEntradaDTO.getId());
        return vendaSaidaDTO;
    }

    @GetMapping("/relatorio")
    @ApiOperation("Gerar relatório de Venda")
    public List<RelatorioDeVendasDTO> exibirRelatorioDeVendas(){
        List<RelatorioDeVendasDTO> relatorioDeVendasDTOS = new ArrayList<>();
        for (Calcado calcado : calcadoService.listarCalcados()) {
            RelatorioDeVendasDTO relatorioDeVendas = modelMapper.map(
                    calcado, RelatorioDeVendasDTO.class);
            if(calcadoService.atualizarPorcentagemDeVendas(calcado.getId()) == 100){
                relatorioDeVendas.setStatusEstoque(StatusEstoque.ZERADO.getMensagem());
            }
            else if (calcadoService.atualizarPorcentagemDeVendas(calcado.getId()) <= 50){
                relatorioDeVendas.setStatusEstoque(StatusEstoque.ADEQUADO.getMensagem());
            }
            else {
                relatorioDeVendas.setStatusEstoque(StatusEstoque.ABAIXO.getMensagem());
            }
            relatorioDeVendasDTOS.add(relatorioDeVendas);
        }
        return relatorioDeVendasDTOS;
    }

}



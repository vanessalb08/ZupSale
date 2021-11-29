package br.com.zup.zupsale.controllers;

import br.com.zup.zupsale.dtos.CadastroDTO;
import br.com.zup.zupsale.dtos.ResumoCadastroDTO;
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
    public List<ResumoCadastroDTO> listarCalcados (){
        List<ResumoCadastroDTO> listaResumo = new ArrayList<>();

        for (Calcado calcado: calcadoService.listarCalcados()) {
            ResumoCadastroDTO resumoDTO =modelMapper.map(calcado, ResumoCadastroDTO.class);
            listaResumo.add(resumoDTO);
        }

        return listaResumo;
    }

}

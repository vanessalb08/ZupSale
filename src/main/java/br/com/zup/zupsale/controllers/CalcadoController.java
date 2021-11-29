package br.com.zup.zupsale.controllers;

import br.com.zup.zupsale.dtos.CadastroDTO;
import br.com.zup.zupsale.models.Calcado;
import br.com.zup.zupsale.services.CalcadoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/zupsale")
public class CalcadoController {

    @Autowired
    private CalcadoService calcadoService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Calcado cadastrarCalcado(@RequestBody @Valid CadastroDTO cadastroDTO) {
        Calcado calcado = modelMapper.map(cadastroDTO, Calcado.class);
        return calcadoService.cadastrarCalcado(calcado);
    }

}

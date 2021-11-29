package br.com.zup.zupsale.services;

import br.com.zup.zupsale.models.Calcado;
import br.com.zup.zupsale.repositories.CalcadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CalcadoService {

    @Autowired
    private CalcadoRepository calcadoRepository;

    public Calcado cadastrarCalcado(Calcado calcado) {
        calcado.setDataDeCadastro(LocalDateTime.now());
        return calcadoRepository.save(calcado);

    }

}

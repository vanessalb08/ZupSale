package br.com.zup.zupsale.services;

import br.com.zup.zupsale.repositories.CalcadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalcadoService {

    @Autowired
    private CalcadoRepository calcadoRepository;

}

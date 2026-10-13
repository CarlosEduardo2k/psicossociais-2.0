package br.com.psicossocial.agendamento;

import br.com.psicossocial.service.AplicacaoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AplicacaoAgendamento {
    private final AplicacaoService aplicacaoService;

    public  AplicacaoAgendamento(AplicacaoService aplicacaoService) {
        this.aplicacaoService = aplicacaoService;
    }
    @Scheduled(fixedDelay = 60000)
    public void verificarAplicacaoExpirada(){
        aplicacaoService.fecharAplicacaoAutomaticamente();
    }
}

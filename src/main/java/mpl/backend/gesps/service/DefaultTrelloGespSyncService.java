package mpl.backend.gesps.service;

import mpl.backend.gesps.Gesp;
import mpl.backend.gesps.SituacaoGespEnum;
import mpl.backend.gesps.Usuario;

//import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

//@Stateless
public class DefaultTrelloGespSyncService implements TrelloGespSyncService {

    private static final Logger logger = Logger
            .getLogger(DefaultTrelloGespSyncService.class.getName());

    String anterior = "ninguem";
    private Usuario usuarioLogado;

    @Override
    public Boolean loginGesp(Usuario usuarioGesp){

        System.out.println("LOGOU "+ anterior + " -> " + usuarioGesp.getLoginGesp());
        anterior = usuarioGesp.getLoginGesp();

        usuarioLogado = usuarioGesp;
        System.out.println("login=" + usuarioGesp.getLoginGesp());
        return true;
    }

    @Override
    public Boolean loginTrello(Usuario usuarioTrello){

        System.out.println("LOGOU "+ anterior + " -> " + usuarioTrello.getLoginGesp());
        anterior = usuarioTrello.getLoginGesp();

        usuarioLogado = usuarioTrello;
        System.out.println("login=" + usuarioTrello.getLoginGesp());
        return true;
    }

    @Override
    public List<Gesp> sincronizarTodosOsChamadosSccPorSituacao(
            SituacaoGespEnum situacao) {

        List<Gesp> listaDeGesps = new ArrayList<>();
        if(usuarioLogado != null) {
            System.out.println("sincronizarTodosOsChamadosSccPorSituacao logado=" + usuarioLogado.getLoginGesp());

            Gesp gesp = new Gesp();
            gesp.setId(12345L);

            gesp.setAtendenteGesp("MARILIA");
            gesp.setAtendentesTrello("MARILIA UAHA");
            gesp.setSituacaoGesp(SituacaoGespEnum.SITUACAO_AGUARDANDO_ATENDIMENTO);
            gesp.setDataSolicitacao(new Date());
            gesp.setSituacaoTrello("assas");
            gesp.setSolicitante("asaadd");
            gesp.setDescricao("assadasdgfgfhfhfghgfhfghhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            gesp.setTipoOcorrencia("dsfsdsd");
            listaDeGesps.add(gesp);

//            List<Gesp> gespsOficiais = GespExplorer.start(usuarioLogado.getLoginGesp(), usuarioLogado.getPasswordGesp());
//            listaDeGesps.addAll(gespsOficiais);
        }
        return listaDeGesps;
    }
}

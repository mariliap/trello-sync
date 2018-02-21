package mpl.backend.gesps;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.trello4j.TrelloURI;
import org.trello4j.core.TrelloTemplate;
import org.trello4j.model.Board;
import org.trello4j.model.Card;

import java.util.List;


/**
 * Created by Marilia Portela on 19/03/2017.
 */
public class TrelloExplorer {

    public static void main(String[] args) {

        ReflectionToStringBuilder.setDefaultStyle(new HtmlExplorerToStringStyle());

        String baseUrl = "http://gesp.cge.ce.gov.br"; //"https://news.ycombinator.com" ;
        String loginUrl = baseUrl + "/login.seam";  //"/login?goto=news" ;
        String myApiKey = "GXeJ-kyruHDaKUhQwCAR0QMbTQ8";
        String myToken = "" ;

        try {
            long tempoEmMilisegundos = System.currentTimeMillis();
            long tempoInicial = tempoEmMilisegundos;

            System.out.println("Iniciando login automático na url " + loginUrl);
            TrelloTemplate trello = new TrelloTemplate(myApiKey, myToken);
            //Trello trello = new TrelloImpl(myApiKey, myToken);

            tempoEmMilisegundos = temporizar(tempoEmMilisegundos);

            //List<Board> boardList = trello.getBoardsByMember("me");

            String boardSiconv = "574f39d510941a4645223b7a";
            String boardCGE = "56a61bfd90607d665a49ce22";

            List<org.trello4j.model.List> trelloLists = trello.boundBoardOperations(boardCGE).getList();//getListByBoard(boardSiconv);

            String listaCardsBackLog = "57502d1638056fc9443df1f0";
            String listaCardsAFazer = "574f39dbd424dc09788f6b13";
            String listaCardsEmAndamento = "574f39eb185fefa674f3d1cd";
            String listaCardsFeitos = "574f3a0e0748e47081eef332";
            String listaCardsImpedidos = "574f3a02e794cfb81ec87e04";
            String listaCardsAHomologar = "58b03356fcc5ec228f7b6b85";
            String listaCardsEmHomologacao = "589b23a7ab4fc9197543abed";
            String listaCardsHomologados = "57600ca761e1b0c507ad4f30";
            String listaCardsEmProducao = "575ef65ca0113b9f100f7fbe";
            String listaCardsEmGestaoDeDemandas = "576184ece784f06cf2820675";


            List<Card> cardsBackLog = trello.boundListOperations(listaCardsAFazer).getCards();//getCardsByList(listaCardsBackLog);
//            List<Card> cardsAFazer = trello.getCardsByList(listaCardsAFazer);
//            List<Card> cardsEmAtendimento = trello.getCardsByList(listaCardsEmAndamento);
//            List<Card> cardsFeitos = trello.getCardsByList(listaCardsFeitos);
//            List<Card> cardsImpedidos = trello.getCardsByList(listaCardsImpedidos);
//            List<Card> cardsAHomologar = trello.getCardsByList(listaCardsAHomologar);
//            List<Card> cardsEmHomologacao = trello.getCardsByList(listaCardsEmHomologacao);
//            List<Card> cardsHomologados = trello.getCardsByList(listaCardsHomologados);
//            List<Card> cardsEmProducao = trello.getCardsByList(listaCardsEmProducao);
//            List<Card> cardsGestaoDemandas = trello.getCardsByList(listaCardsEmGestaoDeDemandas);

            String listaAguardandoFeedback = "56cb3ea40853743f94e9a225";
            String listaAFazerCGE = "56a61c17bc0c9b6cb41b8b19";
            trello.boundListOperations(listaAFazerCGE).createCard("CARTAO 9-3.17", "Erro: Quando usuário faz isso, ele não consegue salvar",
                                                                    null, null, null, null, null, null);

            tempoEmMilisegundos = temporizar(tempoEmMilisegundos);

            System.out.println("-- FIM --");
            temporizar(tempoInicial);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long temporizar(long tempoAnteriorEmMilisegundos) {
        long tempoAtualEmMilisegundos = System.currentTimeMillis();
        System.out.println(converterMiliSegundosEmFormatoLegivel(tempoAtualEmMilisegundos - tempoAnteriorEmMilisegundos));
        return tempoAtualEmMilisegundos;
    }

    private static String converterMiliSegundosEmFormatoLegivel(long milisegundos){
        long x = milisegundos / 1000;
        long secondos = x % 60;
        x /= 60;
        long minutos = x % 60;
        x /= 60;
        long horas = x % 24;
        x /= 24;
        long dias = x;

        return dias + " Dias, "+ horas +" horas, "+ minutos +" minutos, "+ secondos +" segundos.";
    }
}

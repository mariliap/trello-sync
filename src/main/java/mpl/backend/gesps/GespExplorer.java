package mpl.backend.gesps;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marilia Portela on 17/03/2017.
 */
public class GespExplorer {

    private final static String SISTEMAS_SCC_CODIGO = "46";


    public static void main(String[] args) {
        String login = "";//colocar cpf com pontos e hífen
        String password = "" ;
        start(login,password);
    }

    public static List<Gesp> start(String login, String password){
        ReflectionToStringBuilder.setDefaultStyle(new HtmlExplorerToStringStyle());

        String baseUrl = "http://gesp.cge.ce.gov.br";
        String loginUrl = baseUrl + "/login.seam";

        List<Gesp> listaDeGesps = new ArrayList<>();
        try {
            long tempoEmMilisegundos = System.currentTimeMillis();
            long tempoInicial = tempoEmMilisegundos;

            System.out.println("Iniciando login automático na url " + loginUrl);
            WebClient client = autoLogin(loginUrl, login, password);

            tempoEmMilisegundos = temporizar(tempoEmMilisegundos);

            if(client != null) {

                System.out.println("Usuário " + login + " logado.");
                HtmlPage page = client.getPage(baseUrl);
                page = irParaTodosOsChamados(page);

                System.out.println("Buscando GESPs na situação "
                        + SituacaoGespEnum.SITUACAO_AGUARDANDO_ATENDIMENTO.toString());
                List<Gesp> gespsEmAtendimento = listarTodosOsChamadosSccPorSituacao(
                        client, page, SituacaoGespEnum.SITUACAO_AGUARDANDO_ATENDIMENTO);

                tempoEmMilisegundos = temporizar(tempoEmMilisegundos);

                page = irParaTodosOsChamados(page);

                tempoEmMilisegundos = temporizar(tempoEmMilisegundos);
                System.out.println("Buscando GESPs na situação "
                        + SituacaoGespEnum.SITUACAO_EM_ATENDIMENTO.toString());
                List<Gesp> gespsAguardandoAtendimento =listarTodosOsChamadosSccPorSituacao(
                        client, page, SituacaoGespEnum.SITUACAO_EM_ATENDIMENTO);

                tempoEmMilisegundos = temporizar(tempoEmMilisegundos);

                listaDeGesps.addAll(gespsEmAtendimento);
                listaDeGesps.addAll(gespsAguardandoAtendimento);
            } else {
                System.err.println("SENHA ERRADA");
            }

            System.out.println("-- FIM --");
            temporizar(tempoInicial);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaDeGesps;
    }

    public static WebClient autoLogin(String loginUrl, String login, String password)
            throws FailingHttpStatusCodeException, MalformedURLException, IOException {

        WebClient client = new WebClient(BrowserVersion.FIREFOX_45);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        HtmlPage paginaLogin = client.getPage(loginUrl);

        HtmlInput inputPassword = paginaLogin.getFirstByXPath("//input[@type='password']");
        //Pega o primeiro input precedente que não está escondido
        HtmlInput inputLogin = inputPassword.getFirstByXPath(".//preceding::input[not(@type='hidden')]");

        inputLogin.setValueAttribute(login);
        inputPassword.setValueAttribute(password);

        //get the enclosing form
        HtmlForm loginForm = inputPassword.getEnclosingForm() ;
        HtmlSubmitInput button = (HtmlSubmitInput) loginForm.getInputsByValue("Entrar").get(0);
        HtmlPage paginaInicial = button.click();

        if(paginaInicial.getWebResponse().getContentAsString().contains("Sair")){
            return client;
        } else {
            return null;
        }
    }

    private static HtmlPage irParaTodosOsChamados(HtmlPage page) {
        HtmlPage paginaTodosChamados = null;
        try {
            HtmlAnchor todosChamadosLink = page.getFirstByXPath(
                    String.format(".//a[@href='%s']", "/GESP/chamadoTodosChamados.seam?conversationPropagation=begin"));
            paginaTodosChamados = todosChamadosLink.click();
            return paginaTodosChamados;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paginaTodosChamados;
    }

    private static List<Gesp> listarTodosOsChamadosSccPorSituacao(WebClient client,
                                                                  HtmlPage page,
                                                                  SituacaoGespEnum situcaoGesp){

        List<Gesp> listaDeGesps = new ArrayList<>();
        try{
//          submitButton = page.getByXPath("/html/body//form//input[@type='submit' and @value='Save as XML']");
//          HtmlSelect select = (HtmlSelect) page.getElementById(mySelectId);categoriaSelect
//          HtmlSelect categoriaSelect = (HtmlSelect) page.getByXPath("//*[matches(@id, '*categoriaSelect')]");
//          HtmlSelect situacaoSelect = (HtmlSelect) page.getByXPath("//*[ends-with(@id, 'situacaoSelect')]");
//          xpath = "//a[substring(@href, 0, string-length('/GESP/chamado.seam?chamadoId=')) = '/GESP/chamado.seam?chamadoId=']";

            String xpath = "//select[substring(@id, string-length(@id) - string-length('categoriaSelect') +1) = " +
                    "'categoriaSelect']";
            HtmlSelect categoriaSelect = (HtmlSelect) page.getFirstByXPath(xpath);
            HtmlOption sistemaSccOption = categoriaSelect.getOptionByValue(SISTEMAS_SCC_CODIGO);
            categoriaSelect.setSelectedAttribute(sistemaSccOption, true);

            xpath = "//select[substring(@id, string-length(@id) - string-length('situacaoSelect') +1) = " +
                    "'situacaoSelect']";
            HtmlSelect situacaoSelect = (HtmlSelect) page.getFirstByXPath(xpath);
            HtmlOption situcaoOption = situacaoSelect.getOptionByValue(situcaoGesp.getCodigo());
            situacaoSelect.setSelectedAttribute(situcaoOption, true);

            HtmlForm localizarForm = categoriaSelect.getEnclosingForm();
            HtmlSubmitInput LocalizarButton = (HtmlSubmitInput) localizarForm.getInputsByValue("Localizar").get(0);

            client.getOptions().setJavaScriptEnabled(true);
            HtmlPage listagemGespsPage = LocalizarButton.click();


            xpath = "//table["+ obterElementoCujoAtributoTerminaCom("id", "chamadoTable") + "]";
            HtmlTable table = listagemGespsPage.getFirstByXPath(xpath);
            int qtdGesps = Integer.parseInt(table.getPreviousSibling().getPreviousSibling().asText());
            int qtdPaginas =  (int) Math.ceil(qtdGesps / 10d);

            xpath = "//a[starts-with(@href, '/GESP/chamado.seam?chamadoId=')]";
            List<HtmlAnchor> todosOsLinksParaGespsResultantesDaBusca = new ArrayList<>();
            List<HtmlAnchor> linksParaGespsNaPaginaAtual = null;

            int pagina = 1;

            while (listagemGespsPage != null){
                System.out.println("Pagina "+ pagina + " de " +  qtdPaginas);
                linksParaGespsNaPaginaAtual = listagemGespsPage.getByXPath(xpath);

                for (HtmlAnchor linkParaGesp : linksParaGespsNaPaginaAtual) {
                    todosOsLinksParaGespsResultantesDaBusca.add(linkParaGesp);
                }

                pagina++;
                listagemGespsPage = irParaPagina(client,listagemGespsPage, pagina);
            }
            client.getOptions().setJavaScriptEnabled(false);

            HtmlAnchor htmlAnchor = null;
            for (int i = 0; i < todosOsLinksParaGespsResultantesDaBusca.size(); i++) {

                htmlAnchor = todosOsLinksParaGespsResultantesDaBusca.get(i);
                Gesp gesp = obterInformacoesGesp(client, htmlAnchor, situcaoGesp);
                listaDeGesps.add(gesp);

                System.out.println("Gesp "+ (i+1) + " de " + qtdGesps + " | " + gesp.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaDeGesps;
    }



    private static HtmlPage irParaPagina(WebClient client, HtmlPage paginaAtual, int pagina) throws Exception{

        HtmlPage listagemGespsPage = null;

        HtmlTableCell tableCell = paginaAtual.getFirstByXPath("//td[text()='"+ pagina +"']");
        if(tableCell != null) {
            client.setAjaxController(new NicelyResynchronizingAjaxController());

            listagemGespsPage = tableCell.click();

            String xpath = "//a[starts-with(@href, '/GESP/chamado.seam?chamadoId=51292')]";
            if(listagemGespsPage.getByXPath(xpath).size() > 0){
                System.out.println("NAO FUNCIONOU");
            }

        }

        return listagemGespsPage;
    }

    private  static Gesp obterInformacoesGesp(WebClient client, HtmlAnchor linkParaGesp,
                                              SituacaoGespEnum situacaoGesp) throws Exception{
        Gesp gesp = new Gesp();

//        HtmlPage gespPage = linkParaGesp.click();
        String gespUrl = "http://gesp.cge.ce.gov.br"
                + linkParaGesp.getHrefAttribute().split("&")[0];
        HtmlPage gespPage = client.getPage(gespUrl);

        String xpath = "//span["+ obterElementoCujoAtributoTerminaCom("id", "idChamadoField:name") + "]";
        HtmlSpan span = gespPage.getFirstByXPath(xpath);
        gesp.setId(Long.parseLong(span.asText()));

        xpath = "//span["+ obterElementoCujoAtributoTerminaCom("id", "solicitanteField:name") + "]";
        span = gespPage.getFirstByXPath(xpath);
        gesp.setSolicitante(span.asText());

        xpath = "//span["+ obterElementoCujoAtributoTerminaCom("id", "dataSolicitacaoField:name") + "]";
        span = gespPage.getFirstByXPath(xpath);
        gesp.setDataSolicitacao(converterDataGesp(span.asText()));

        xpath = "//span["+ obterElementoCujoAtributoTerminaCom("id", "descricao") + "]";
        span = gespPage.getFirstByXPath(xpath);
        gesp.setDescricao(span.asText());

        xpath = "//span["+ obterElementoCujoAtributoTerminaCom("id", "tipoOcorrenciaField:tipoOcorrencia") + "]";
        span = gespPage.getFirstByXPath(xpath);
        gesp.setTipoOcorrencia(span.asText());

        xpath = "//span["+ obterElementoCujoAtributoTerminaCom("id", "atendenteField:name") + "]";
        span = gespPage.getFirstByXPath(xpath);
        gesp.setAtendenteGesp(span.asText());

        gesp.setSituacaoGesp(situacaoGesp);
//        gesp.setLinkGesp(linkParaGesp);

        return gesp;
    }

    private static Date converterDataGesp(String dataGesp) throws ParseException {
        //09:44 13/01/2016
        String[] horaData = dataGesp.split(" ");

        DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date dataConvertida = formato.parse(horaData[1]);
        return dataConvertida;
    }

    private static String obterElementoCujoAtributoTerminaCom(String atributo, String terminaCom){
        String qtdCaracteresAtributos = "string-length(@"+atributo+")";
        String menos = " - ";
        String qtdCaracteresNomeBuscado = "string-length('"+terminaCom+"')";
        String calculoIndexInicioString = qtdCaracteresAtributos + menos + qtdCaracteresNomeBuscado + " + 1";
        String finalDaString = "substring(@id," + calculoIndexInicioString + ")";
        String codicaoCapturaDeElemento = finalDaString +" = '" + terminaCom + "'";
        return codicaoCapturaDeElemento;
    }

    private static void mostrarHtml(HtmlPage page) {
        System.out.print(page.getWebResponse().getContentAsString());
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

package mpl.backend.gesps;

import org.apache.commons.lang3.builder.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Marilia Portela on 19/03/2017.
 */
@XmlRootElement
public class Gesp implements Serializable {



    private Long id;
    private String solicitante;
    private Date dataSolicitacao;
    private String descricao;
    private String tipoOcorrencia;


    private String atendenteGesp;
    private SituacaoGespEnum situacaoGesp;
//    private HtmlAnchor linkGesp;

    private String atendentesTrello;
    private String situacaoTrello;
//    private HtmlAnchor linkTrello;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }


    public Date getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(Date dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAtendenteGesp() {
        return atendenteGesp;
    }

    public void setAtendenteGesp(String atendenteGesp) {
        this.atendenteGesp = atendenteGesp;
    }

    public SituacaoGespEnum getSituacaoGesp() {
        return situacaoGesp;
    }

    public void setSituacaoGesp(SituacaoGespEnum situacaoGesp) {
        this.situacaoGesp = situacaoGesp;
    }

    public String getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    public void setTipoOcorrencia(String tipoOcorrencia) {
        this.tipoOcorrencia = tipoOcorrencia;
    }

    public String getAtendentesTrello() {
        return atendentesTrello;
    }

    public void setAtendentesTrello(String atendentesTrello) {
        this.atendentesTrello = atendentesTrello;
    }

    public String getSituacaoTrello() {
        return situacaoTrello;
    }

    public void setSituacaoTrello(String situacaoTrello) {
        this.situacaoTrello = situacaoTrello;
    }

//    public HtmlAnchor getLinkGesp() {
//        return linkGesp;
//    }
//
//    public void setLinkGesp(HtmlAnchor linkGesp) {
//        this.linkGesp = linkGesp;
//    }
//
//    public HtmlAnchor getLinkTrello() {
//        return linkTrello;
//    }
//
//    public void setLinkTrello(HtmlAnchor linkTrello) {
//        this.linkTrello = linkTrello;
//    }

    @Override
    public String toString(){
        String[] camposExcluidos = {"solicitante","descricao", "linkGesp",
                "atendentesTrello","situacaoTrello", "linkTrello"};
        return ReflectionToStringBuilder.toStringExclude(this, camposExcluidos);
//        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }

    @Override
    public boolean equals(Object obj){
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode(){
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

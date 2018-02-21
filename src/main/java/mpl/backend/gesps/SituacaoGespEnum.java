package mpl.backend.gesps;

/**
 * Created by Marilia Portela on 19/03/2017.
 */
public enum SituacaoGespEnum {

    SITUACAO_AGUARDANDO_ATENDIMENTO("1"),
    SITUCAO_SOLICITADO("2"),
    SITUACAO_EM_ATENDIMENTO("3");

    private String codigo;

    SituacaoGespEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}

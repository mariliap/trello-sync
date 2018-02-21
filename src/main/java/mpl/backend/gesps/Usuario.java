package mpl.backend.gesps;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Marilia Portela on 02/04/2017.
 */
@XmlRootElement
public class Usuario {

    String loginGesp;
    String passwordGesp;
    String loginTrello;
    String tokenTrello;

    public String getLoginGesp() {
        return loginGesp;
    }

    public void setLoginGesp(String loginGesp) {
        this.loginGesp = loginGesp;
    }

    public String getPasswordGesp() {
        return passwordGesp;
    }

    public void setPasswordGesp(String passwordGesp) {
        this.passwordGesp = passwordGesp;
    }

    public String getLoginTrello() {
        return loginTrello;
    }

    public void setLoginTrello(String loginTrello) {
        this.loginTrello = loginTrello;
    }

    public String getTokenTrello() {
        return tokenTrello;
    }

    public void setTokenTrello(String tokenTrello) {
        this.tokenTrello = tokenTrello;
    }
}

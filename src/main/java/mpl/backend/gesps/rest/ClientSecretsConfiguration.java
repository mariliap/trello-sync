package research.mpl.backend.gesps.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

//import org.hibernate.validator.constraints.NotBlank;
/**
 * Created by Marilia Portela on 01/07/2017.
 */
public class ClientSecretsConfiguration {

    //@NotBlank
    @JsonProperty
    String facebook;

    //@NotBlank
    @JsonProperty
    String google;

    //@NotBlank
    @JsonProperty
    String linkedin;

    //@NotBlank
    @JsonProperty
    String github;

    //@NotBlank
    @JsonProperty
    String foursquare;

    //@NotBlank
    @JsonProperty
    String twitter;

    public String getFacebook() {
        return facebook;
    }

    public String getGoogle() {
        return google;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public String getFoursquare() {
        return foursquare;
    }

    public String getTwitter() {
        return twitter;
    }
}
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package mpl.backend.gesps.rest;



//import javax.enterprise.context.ApplicationScoped;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.host.URL;
import com.github.scribejava.apis.TrelloApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth10aService;
import mpl.backend.gesps.Gesp;
import mpl.backend.gesps.SituacaoGespEnum;
import mpl.backend.gesps.TrelloExample;
import org.trello4j.core.TrelloTemplate;
import org.trello4j.model.Card;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/trelloAuth")
//@ApplicationScoped
public class TrelloAuthResource implements Serializable {

    private static final long serialVersionUID = 1L;

    //https://trello.com/app-key
    private static final String API_KEY = "2d569324b6afc1cbbf2402b847b5ab6d";
    private static final String API_SECRET = "380ac374d011c2ae542dd3533188d484fc38b7e5c197949e3a9b87a616cc5000";
    private static final String PROTECTED_RESOURCE_URL = "https://trello.com/1/members/me";

    private static final String URL_CALLBACK = "http://localhost:12345/rest/trelloAuth/receberVerificadorDoTrello";

    private OAuth10aService service = new ServiceBuilder(API_KEY)
            .apiSecret(API_SECRET)
            .callback(URL_CALLBACK)
            .build(TrelloApi.instance());
    private OAuth1RequestToken requestToken;

    @GET
    @Produces({"application/json"})
    public void login() throws IOException, InterruptedException, ExecutionException, URISyntaxException {



        System.out.println("Fetching the Request Token...");
        requestToken = service.getRequestToken();
        System.out.println("Got the Request Token!");
        System.out.println();

        System.out.println("Now go and authorize ScribeJava here:");
        String urlAutorizacao = service.getAuthorizationUrl(requestToken)+"&name=HelloMP";
        System.out.println(urlAutorizacao);

        Desktop.getDesktop().browse(new URI(urlAutorizacao));
    }

    //?oauth_token=cf2232c285670e3ebfc201d99bdbc3b8&oauth_verifier=c5c6dab409ef6accc98396561f54ab0c
    @GET
    @Path("/receberVerificadorDoTrello")
    @Produces({"application/json"})
    public void receberVerifier(
            @QueryParam("oauth_token") String requestToken1,
            @QueryParam("oauth_verifier") String oauthVerifier) throws IOException, InterruptedException, ExecutionException {

        // Trade the Request Token and Verfier for the Access Token
        System.out.println("Trading the Request Token for an Access Token...");
        final OAuth1AccessToken accessToken = service.getAccessToken(service.getRequestToken(), oauthVerifier);
        System.out.println("Got the Access Token!");
        System.out.println("(if your curious the raw answer looks like this: " + accessToken.getRawResponse() + "')");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource...");
        final OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        service.signRequest(accessToken, request);
        final Response response = service.execute(request);
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println(response.getBody());

        TrelloTemplate trello = new TrelloTemplate(API_KEY,accessToken.getToken());

        String boardCGE = "56a61bfd90607d665a49ce22";
        List<org.trello4j.model.List> trelloLists = trello.boundBoardOperations(boardCGE).getList();
        System.out.println(trelloLists.toString());

        String listaAFazerCGE = "56a61c17bc0c9b6cb41b8b19";
        List<Card> cardsBackLog = trello.boundListOperations(listaAFazerCGE).getCards();
        org.trello4j.model.List lista = trello.boundListOperations(listaAFazerCGE).get();
        System.out.println(lista.getName());
        if(cardsBackLog != null) {
            System.out.println(cardsBackLog.toString());
        } else {
            System.out.println(listaAFazerCGE + " nao há cartoes");
        }
//        trello.boundListOperations(listaAFazerCGE).createCard("CARTAO 9-3.17", "Erro: Quando usuário faz isso, ele não consegue salvar",
//                null, null, null, null, null, null);
    }

//    @PUT
//    @Path("{tokenTrello}")
//    @Consumes({"application/json"})
//    public Boolean login(
//            @PathParam("tokenTrello")
//            @NotNull
//            String tokenUser){
//        System.out.println("LOGOU TRELLO");
//        return true;
//    }

//    @POST
//    @Consumes({"application/json"})
//    @Produces({"application/json"})
//    public Boolean loginGesp(
//            @NotNull
//            String tokenGespTrello) {
//        System.out.println("LOGOU");
//        return true;
//    }

//    @PUT
//    @Path("{id}")
//    @Consumes({"application/json"})
//    public void edit(
//            @PathParam("username")
//            @NotNull
//            @Size(min = 4, max = 14, message = "User name must be between 4 and 14 characters.") String username,
//            @PathParam("id") Long id,
//            @Valid ToDoItem item) {
//        item.setId(id);
//        service.updateToDoItem(username, item);
//    }
//
//    @DELETE
//    @Path("{id}")
//    public void remove(
//            @PathParam("username")
//            @NotNull
//            @Size(min = 4, max = 14, message = "User name must be between 4 and 14 characters.") String username,
//            @PathParam("id") Long id) {
//        service.removeToDoItem(username, id);
//    }

//    @GET
//    @Produces({"application/json"})
//    public List<ToDoItem> getAll(
//            @PathParam("username")
//            @NotNull
//            @Size(min = 4, max = 14, message = "User name must be between 4 and 14 characters.") String username) {
//        if(username.equalsIgnoreCase("everybody")){
//            return service.findAllToDoItems();
//        } else {
//            return service.findToDoItemsByUsername(username);
//        }
//    }
}

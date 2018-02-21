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


import mpl.backend.gesps.Gesp;
import mpl.backend.gesps.SituacaoGespEnum;
import mpl.backend.gesps.Usuario;
import mpl.backend.gesps.service.DefaultTrelloGespSyncService;

//import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import java.io.Serializable;
import java.util.List;

@Path("/trelloGespSync")
//@ApplicationScoped
public class TrelloGespSyncResource implements Serializable{

    @Inject
    private DefaultTrelloGespSyncService service;

    //para acessar trello
//    https://cwiki.apache.org/confluence/display/OLTU/OAuth+2.0+Client+Quickstart
    //para se comunicar com a página web (angular)
//    https://cwiki.apache.org/confluence/display/OLTU/OAuth+2.0+Authorization+Server

    @GET
    @Path("{atendente}")
    @Produces({"application/json"})
    public List<Gesp> getAll(
            @PathParam("atendente")
            String atendente) {

        System.out.println(atendente);

        if(atendente.equalsIgnoreCase("everybody")){
            return service.sincronizarTodosOsChamadosSccPorSituacao(
                    SituacaoGespEnum.SITUACAO_AGUARDANDO_ATENDIMENTO);
        } else {
            return service.sincronizarTodosOsChamadosSccPorSituacao(
                    SituacaoGespEnum.SITUACAO_AGUARDANDO_ATENDIMENTO);
        }
    }


    @POST
    @Path("autenticar/gesp")
    @Consumes({"application/json"})
    public Boolean loginGesp(
            @NotNull
            Usuario tokenUser){
        System.out.println("LOGOU GESP");
        return service.loginGesp(tokenUser);
    }

    @POST
    @Path("autenticar/trello")
    @Consumes({"application/json"})
    public Boolean loginTrello(
            @NotNull
            Usuario tokenUser){
        System.out.println("LOGOU TRELLO");
        return service.loginGesp(tokenUser);
    }

}

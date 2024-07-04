<%@page import="classes.Recolte"%>
<%@page import="classes.JournalCulture"%>
<%@page import="classes.Plante"%>
<%@page import="java.util.List"%>
<%@page import="classes.SalleCulture"%>
<%@page import="classes.Utilisateur"%>
<!doctype html>
<%
    Object sessionId = session.getAttribute("idUser");
    Utilisateur user = new Utilisateur();
    if(sessionId != null){
        user.getById((String)sessionId);
    }
    else {
        response.sendRedirect("index.jsp");
    }
%>
<script src="inc/js/function.js"></script> 
<%  String success = (String)request.getAttribute("success");

    if(success != null){ %>
    <script>
        window.onload = function(){
            alert('<%= success%>','success');
        };
    </script> 
    <% }
   
    String error = (String)request.getAttribute("error");
    if (error != null) { %>
    <script>
        window.onload = function(){
            var inpPassword = document.getElementById('password');
            if("<%= error %>" === "pwd"){
                inpPassword.classList.add('is-invalid');
            }
            else {
                alert('<%= error%>','danger');
            }
        };
    </script>
 <% } %>
<html lang="en">

<head>
    <meta http-equiv="content-type" content="text/html;charset=utf-8" />
    <title>Acceuil</title>
    <link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" >
    <link href="inc/css/sidebars.css" rel="stylesheet">
    <link rel="stylesheet" href="bootstrap-icons/font/bootstrap-icons.css">
    <script src="inc/js/function.js"></script>
</head>
<body>

    <div id="alertPlaceholder" class="m-2 top-0 end-0 position-fixed"></div>
    
    <!-- edit salle culture -->
    <div class="modal py-5" tabindex="-1" role="dialog" id="modalEditSalleCulture">
        <div class="modal-dialog" role="document">
          <div class="modal-content rounded-5 shadow">
            <div class="modal-header p-5 pb-4 border-bottom-0">
              <h5 class="modal-title">Modification Salles de culture</h5>
              <!-- <h2 class="fw-bold mb-0"></h2> -->
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
      
            <div class="modal-body p-5 pt-0">  
              <form id="formEditSalleCulture" method="post" action="SalleCultureServelet?action=update">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="slnom" id="editslnom" placeholder="Nom" required>
                    <label for="editslnom">Nom</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="number" class="form-control rounded-4" name="sltemperature" id="editsltemperature" placeholder="Température (°c)" required>
                    <label for="editsltemperature">Température (°c)</label>
                </div>

                <div class="form-floating mb-3">
                  <input type="number" class="form-control rounded-4" name="slhumidite" id="editslhumidite" placeholder="Humidité (%)" required>
                  <label for="editslhumidite">Humidité (%)</label>
                </div>
                  
                <input type="hidden" name="slid" id="editslid" />

                <button class="w-100 mb-2 btn btn-lg rounded-4 btn-success" type="submit">Valider</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    
    
    <!-- edit Plante -->
    <div class="modal py-5" tabindex="-1" role="dialog" id="modalEditPlante">
        <div class="modal-dialog" role="document">
          <div class="modal-content rounded-5 shadow">
            <div class="modal-header p-5 pb-4 border-bottom-0">
              <h5 class="modal-title">Modification Plante</h5>
              <!-- <h2 class="fw-bold mb-0"></h2> -->
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
      
            <div class="modal-body p-5 pt-0">  
              <form id="formEditPlante" method="post" action="PlanteServelet?action=update">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="pespece" id="editpespece" placeholder="Espèce" required>
                    <label for="pespece">Espèce</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="pvariete" id="editpvariete" placeholder="Variété" required>
                    <label for="pvariete">Variété</label>
                </div>

                <div class="mb-3">
                    <label for="selecteditpsc" class="form-label">Salle de culture</label>
                    <select id="selecteditpsc" class="form-select" name="psc">
                        <%  
                            if(request.getAttribute("listSalleCulture") != null){
                                List<SalleCulture> lsc =(List<SalleCulture>) request.getAttribute("listSalleCulture");
                             
                                for(SalleCulture s : lsc){ %>
                                    <option value="<%= s.getId() %>"><%= s.getNom() %></option>
                                <% }
                            }
                        %>
                    </select>
                </div>
                  
                <input type="hidden" name="pid" id="editpid" />

                <button class="w-100 mb-2 btn btn-lg rounded-4 btn-success" type="submit">Valider</button>
              </form>
            </div>
          </div>
        </div>
      </div>
                    
    <!-- edit journalCulture -->
    <div class="modal py-5" tabindex="-1" role="dialog" id="modalEditJournalCulture">
        <div class="modal-dialog" role="document">
          <div class="modal-content rounded-5 shadow">
            <div class="modal-header p-5 pb-4 border-bottom-0">
              <h5 class="modal-title">Modification Journal de culture</h5>
              <!-- <h2 class="fw-bold mb-0"></h2> -->
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
      
            <div class="modal-body p-5 pt-0">  
              <form id="formEditJournalCulture" method="post" action="JournalCultureServelet?action=update">
                <div class="form-floating mb-3">
                    <input type="date" class="form-control rounded-4" name="jcdate" id="editjcDate" placeholder="Date" required>
                    <label for="editjcDate">Date</label>
                </div>
                        
                <div class="mb-3">
                    <label for="selectjcPlante" class="form-label">Plante</label>
                    <select id="selectjcPlante" class="form-select" name="jcplante">
                    <%  
                        if(request.getAttribute("listPlante") != null){
                            List<Plante> listPlante =(List<Plante>) request.getAttribute("listPlante");
                                
                            for (Plante p : listPlante) { %>
                                <option value="<%= p.getId() %>"> <%= p.toString() %></option>
                            <% }
                        }
                    %>
                    </select>
                </div>

                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="jcetapeCroissance" id="editjcEtapeCroissance" placeholder="étape de croissance" required>
                    <label for="editjcEtapeCroissance">étape de croissance</label>
                </div>
                            
                <div class=" mb-3">
                    <label for="editjcNotes">Notes</label>
                    <textarea class="form-control rounded-4" id="editjcNotes" name="jcnotes" placeholder="Notes" rows="3"></textarea>
                </div>
                  
                <input type="hidden" name="jcid" id="editjcid" />

                <button class="w-100 mb-2 btn btn-lg rounded-4 btn-success" type="submit">Valider</button>
              </form>
            </div>
          </div>
        </div>
      </div>
                    
    <!-- edit recolte -->
    <div class="modal py-5" tabindex="-1" role="dialog" id="modalEditRecolte">
        <div class="modal-dialog" role="document">
          <div class="modal-content rounded-5 shadow">
            <div class="modal-header p-5 pb-4 border-bottom-0">
              <h5 class="modal-title">Modification Récolte</h5>
              <!-- <h2 class="fw-bold mb-0"></h2> -->
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
      
            <div class="modal-body p-5 pt-0">  
              <form id="formEditJournalCulture" method="post" action="RecolteServelet?action=update">
                <div class="form-floating mb-3">
                    <input type="date" class="form-control rounded-4" name="rdate" id="editrDate" placeholder="Date" required>
                    <label for="editrDate">Date</label>
                </div>
                        
                <div class="mb-3">
                    <label for="selectrPlante" class="form-label">Plante</label>
                    <select id="selectrPlante" class="form-select" name="rplante">
                    <%  
                        if(request.getAttribute("listPlante") != null){
                            List<Plante> listPlante =(List<Plante>) request.getAttribute("listPlante");
                                
                            for (Plante p : listPlante) { %>
                                <option value="<%= p.getId() %>"> <%= p.toString() %></option>
                            <% }
                        }
                    %>
                    </select>
                </div>

                <div class="form-floating mb-3">
                    <input type="int" class="form-control rounded-4" name="rrendement" id="editrRendement" placeholder="Rendement en gramme" required>
                    <label for="editrRendement">Rendement en gramme</label>
                </div>
                
                    <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="rqualite" id="editrqualite" placeholder="Qualité" required>
                    <label for="editrqualite">Qualité</label>
                </div>
                  
                <input type="hidden" name="rid" id="editrid" />

                <button class="w-100 mb-2 btn btn-lg rounded-4 btn-success" type="submit">Valider</button>
              </form>
            </div>
          </div>
        </div>
      </div>
    
    <main>
        <div class="flex-shrink-0 p-3 bg-white" style="width: 20%;">
          <a href="Acceuil.jsp" class="d-flex align-items-center pb-3 mb-3 link-dark text-decoration-none border-bottom">
              <img src="img/medical-cannabis.png" class="me-3" width="70" height="70" alt="medical-cannabis"/>
              <span class="fs-5 fw-semibold text-success">CANNABIS</span>
          </a>
          <ul class="list-unstyled ps-0">
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#plantes-collapse" aria-expanded="false">
                Plantes
              </button>
              <div class="collapse" id="plantes-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                  <li><a href="preparedPlanteServelet?action=listPlante" class="link-dark rounded">Liste</a></li>
                  <li><a href="preparedSalleCutlureServelet?action=addPlante" class="link-dark rounded">Ajouter</a></li>
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#salleCulture-collapse" aria-expanded="false">
                Salles de culture
              </button>
              <div class="collapse" id="salleCulture-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                    <li><a href="preparedSalleCutlureServelet?action=listSalleCulture" class="link-dark rounded">Liste</a></li>
                    <li><a href="Acceuil.jsp?addSalleCulture" class="link-dark rounded">Ajouter</a></li>
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#journalCulture-collapse" aria-expanded="false">
                Journal de culture
              </button>
              <div class="collapse" id="journalCulture-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                  <li><a href="preparedJournalCutlureServelet?action=listJournalCulture" class="link-dark rounded">Liste</a></li>
                  <li><a href="preparedPlanteServelet?action=addJournalCulture" class="link-dark rounded">Ajouter</a></li>
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#recolte-collapse" aria-expanded="false">
                Récoltes
              </button>
              <div class="collapse" id="recolte-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                  <li><a href="preparedRecolteServelet?action=listRecolte" class="link-dark rounded">Liste</a></li>
                  <li><a href="preparedPlanteServelet?action=addRecolte" class="link-dark rounded">Ajouter</a></li>
                </ul>
              </div>
            </li>
            <li class="border-top my-3"></li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#compte-collapse" aria-expanded="false">
                Compte
              </button>
              <div class="collapse" id="compte-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                  <li><a href="Acceuil.jsp?profil" class="link-dark rounded">Profile</a></li>
                  <li><a href="Acceuil.jsp?setting" class="link-dark rounded">Paramétres</a></li>
                  <li><a href="UtilisateurServelet?action=logout" class="link-dark rounded text-danger">Deconnexion</a></li>
                </ul>
              </div>
            </li>
          </ul>
        </div>
        
        <div class="overflow-auto d-flex justify-content-center my-4" style="max-height: 100vh;width: 80%" >
            <div class="w-75">
                <!-- compte -->
                <% if(request.getParameter("profil") != null){ %>
                <script>showCollapse("compte-collapse");</script>
                    <h3 class="mb-5">Vos informations</h3>
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><p><span class="fw-bold">ID : </span><%= user.getId()%></p></li>
                        <li class="list-group-item"><p><span class="fw-bold">Nom : </span><%= user.getNom()%></p></li>
                        <li class="list-group-item"><p><span class="fw-bold">Prénom : </span><%= user.getPrenom()%></p></li>
                        <li class="list-group-item"><p><span class="fw-bold">Pseudo : </span><%= user.getPseudo()%></p></li>
                        <li class="list-group-item"><p><span class="fw-bold">E-mail : </span><%= user.getEmail()%></p></li>
                    </ul>
                <% } %>
                
                <% if(request.getParameter("setting") != null){ %>
                    <script>showCollapse("compte-collapse");</script>
                    
                    <h3 class="mb-5">Modification du profile</h3>
     
                    <form id="formModification" action="UtilisateurServelet?action=update" method="post">
                        <div class="row g-3">
                            <div class="col-sm-6">
                              <label for="inputNom" class="form-label">Nom</label>
                              <input type="text" class="form-control" id="inputNom" placeholder="Nom" name="nom" value="<%= user.getNom()%>" required="">
                            </div>

                            <div class="col-sm-6">
                              <label for="inputPrenom" class="form-label">Prénom</label>
                              <input type="text" class="form-control" id="inputPrenom" placeholder="Prénom" name="prenom" value="<%= user.getPrenom()%>" required="">
                            </div>

                            <div class="col-12">
                                <label for="inputPseudo" class="form-label">Pseudo</label>
                                <div class="input-group has-validation">
                                  <span class="input-group-text">@</span>
                                  <input type="text" class="form-control" id="inputPseudo" placeholder="Pseudo" name="pseudo" value="<%= user.getPseudo()%>" required="">
                                </div>
                            </div>

                            <div class="col-12">
                                <label for="inputEmail" class="form-label">Email</label>
                                <input type="email" class="form-control" id="inputEmail" name="email" value="<%= user.getEmail()%>" placeholder="you@example.com">
                            </div>
                            
                            <input type="hidden" value="<%= user.getId()%>" name="id">
                            <button class="w-100 btn btn-success btn-lg mt-4" type="submit">Valider</button>
                        </div>
                    </form>
                        
                    <hr class="my-4">
                    <h3 class="mb-5">Changer le mot de passe</h3>
                    <form id="formChangePassword" action="UtilisateurServelet?action=updatePwd" method="post">
                        <div class="input-group has-password-toggle position-relative mb-3">
                            <input class="form-control <%= request.getAttribute("errorPassword") != null ? "is-invalid" : "" %>" 
                                   type="password" name="password" id="password" placeholder="Mot de passe" 
                                   aria-describedby="validationServerPasswordFeedback" required>
                            <span class="input-group-text password-toggle" id="passwordToggle">
                                <i class="bi bi-eye"></i>
                            </span>
                            <div id="validationServerPasswordFeedback" class="invalid-feedback">
                                Mot de passe incorrecte.
                            </div>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="password" class="form-control rounded-4 <%= request.getAttribute("errorNewPassword") != null ? "is-invalid" : "" %>" 
                                   name="newPassword" id="floatingPassword" placeholder="Nouveau mot de passe" required>
                            <label for="floatingPassword">Nouveau mot de passe</label>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="password" class="form-control rounded-4 <%= request.getAttribute("errorPasswordConf") != null ? "is-invalid" : "" %>" 
                                   name="passwordConf" id="floatingPasswordConfirmation" placeholder="Mot de passe" required>
                            <label for="floatingPasswordConfirmation">Confirmer le mot de passe</label>
                            <div class="invalid-feedback">
                                <%= request.getAttribute("errorPasswordConf") != null ? request.getAttribute("errorPasswordConf") : "" %>
                            </div>
                        </div>

                        <input type="hidden" value="<%= user.getId()%>" name="id">
                        <button class="w-100 btn btn-success btn-lg mt-4" type="submit">Valider</button>
                    </form>
                    
                    <hr class="my-4">
                    <h3 class="mb-5">Supprimer votre compte</h3>
                    <a class="btn btn-danger" href="UtilisateurServelet?action=remove&id=<%= user.getId()%>">Supprimer</a>
                <% } %>
                
                <!-- plantes -->
                <% if(request.getParameter("addPlante") != null){ %>
                <script>showCollapse("plantes-collapse");</script>
                    <h3 class="mb-5">Ajouter une plante</h3>
                    
                    <form id="formPlante" method="post" action="PlanteServelet?action=create">
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control rounded-4" name="espece" id="floatingEspece" placeholder="Espèce" required>
                            <label for="floatingEspece">Espèce</label>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="text" class="form-control rounded-4" name="variete" id="floatingVariete" placeholder="Variété" required>
                            <label for="floatingVariete">Variété</label>
                        </div>

                        <div class="mb-3">
                            <label for="selectSalleCulture" class="form-label">Salle de culture</label>
                            <select id="selectSalleCulture" class="form-select" name="salleCulture">
                            <%  
                                List<SalleCulture> listSalleCulture = (List<SalleCulture>) request.getAttribute("listSalleCulture");
                                if (listSalleCulture.isEmpty()) { %>
                                <option value="" selected="" disabled>Vide</option>
                            <% } else {
                                for (SalleCulture sc : listSalleCulture) { %>
                                <option value="<%= sc.getId() %>"><%= sc.getNom() %></option>
                            <% } } %>
                            </select>
                        </div>

                        <button class="w-100 mb-2 btn btn-lg rounded-4 btn-success" type="submit" <%= listSalleCulture.isEmpty() ? "disabled" : "" %>>Valider</button>
                    </form>
                <% } %>

                <% if(request.getParameter("listPlante") != null){ %>
                <script>showCollapse("plantes-collapse");</script>
                    <h3 class="mb-5">Liste des Plantes</h3>
                    
                    <table class="table">
                        <thead>
                          <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Espèce</th>
                            <th scope="col">Variété</th>
                            <th scope="col">Salle de culture</th>
                            <th scope="col">Action</th>
                          </tr>
                        </thead>
                        <tbody>
                            <%  List<Plante> listPlante =(List<Plante>) request.getAttribute("listPlante");
                                if(listPlante.isEmpty()){ %>
                                <tr><td colspan="5" class="text-center">Vide</td></tr>
                                <% } else {
                                    for (Plante p : listPlante) { %>
                                <tr>
                                <th scope="row"><%= p.getId()%></th>
                                <td><%= p.getEspece() %></td>
                                <td><%= p.getVariete() %></td>
                                <td><%= p.getSalleCulture().getNom() %> <span hidden="" ><%= p.getSalleCulture().getId()%></span> </td>
                                <td>
                                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalEditPlante" onclick="editPlante(this)" >edit</button>
                                    <a class="btn btn-danger" href="PlanteServelet?action=remove&id=<%= p.getId()%>">remove</a>
                                </td>
                            </tr>  
                            <% } } %>
                        </tbody>
                      </table>

                <% } %>

                <!-- salleCulture -->
                <% if(request.getParameter("addSalleCulture") != null){ %>
                <script>showCollapse("salleCulture-collapse");</script>
                    <h3 class="mb-5">Ajouter une salle de culture</h3>
                    
                    <form id="formSalleCulture" method="post" action="SalleCultureServelet?action=create">
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control rounded-4" name="nom" id="floatingName" placeholder="Nom" required>
                            <label for="floatingName">Nom</label>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="number" class="form-control rounded-4" name="temperature" id="floatingTemperature" placeholder="Température" required>
                            <label for="floatingFirstName">Température (°c)</label>
                        </div>

                        <div class="form-floating mb-3">
                          <input type="number" class="form-control rounded-4" name="humidite" id="floatingHumidite" placeholder="Humidité" required>
                          <label for="floatingHumidite">Humidité (%)</label>
                        </div>

                        <button class="w-100 mb-2 btn btn-lg rounded-4 btn-success" type="submit">Valider</button>
                    </form>
                <% } %>

                <% if(request.getParameter("listSalleCulture") != null){ %>
                <script>showCollapse("salleCulture-collapse");</script>
                    <h3 class="mb-5">Liste des salles de culture</h3>
                      <table class="table">
                        <thead>
                          <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Nom</th>
                            <th scope="col">Température (°c)</th>
                            <th scope="col">Humidité (%)</th>
                            <th scope="col">Action</th>
                          </tr>
                        </thead>
                        <tbody>
                            <%  
                                List<SalleCulture> listSalleCulture = (List<SalleCulture>) request.getAttribute("listSalleCulture");
                                if (listSalleCulture.isEmpty()) { %>
                                <tr><td colspan="5" class="text-center">Vide</td></tr>
                            <% } else {
                                for (SalleCulture s : listSalleCulture) { %>
                                <tr>
                                    <th scope="row"><%= s.getId() %></th>
                                    <td><%= s.getNom() %></td>
                                    <td><%= s.getTemperature() %></td>
                                    <td><%= s.getHumidite() %></td>
                                    <td>
                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalEditSalleCulture" onclick="editSalleCulture(this)">edit</button>
                                        <a class="btn btn-danger" href="SalleCultureServelet?action=remove&id=<%= s.getId() %>">remove</a>
                                    </td>
                                </tr>
                            <% } } %>
                        </tbody>
                      </table>
                <% } %>

                <!-- journalCulture -->
                <% if(request.getParameter("addJournalCulture") != null){ %>
                <script>showCollapse("journalCulture-collapse");</script>

                <h3 class="mb-5">Ajouter journal de culture</h3>
                
                <form id="formJournalCulture" method="post" action="JournalCultureServelet?action=create">
                    <div class="form-floating mb-3">
                        <input type="date" class="form-control rounded-4" name="date" id="floatingDate" placeholder="Date" required>
                        <label for="floatingDate">Date</label>
                    </div>

                    <div class="mb-3">
                        <label for="selectPlante" class="form-label">Plante</label>
                        <select id="selectPlante" class="form-select" name="plante">
                        <%  
                            List<Plante> listPlante = (List<Plante>) request.getAttribute("listPlante");
                            if (listPlante.isEmpty()) { %>
                            <option value="" selected="" disabled>Vide</option>
                        <% } else {
                            for (Plante p : listPlante) { %>
                            <option value="<%= p.getId() %>"><%= p.toString() %></option>
                        <% } } %>
                        </select>
                    </div>

                    <div class="form-floating mb-3">
                        <input type="text" class="form-control rounded-4" name="etapeCroissance" id="floatingEtapeCroissance" placeholder="étape de croissance" required>
                        <label for="floatingEtapeCroissance">étape de croissance</label>
                    </div>

                    <div class="mb-3">
                        <label for="floatingNotes">Notes</label>
                        <textarea class="form-control rounded-4" id="floatingNotes" name="notes" placeholder="Notes" rows="3"></textarea>
                    </div>

                    <button class="w-100 mb-2 btn btn-lg rounded-4 btn-success" type="submit" <%= listPlante.isEmpty() ? "disabled" : "" %>>Valider</button>
                </form>
                <% } %>

                <% if(request.getParameter("listJournalCulture") != null){ %>
                <script>showCollapse("journalCulture-collapse");</script>
                    <h3 class="mb-5">Journal de culture</h3>
                    
                    <table class="table">
                        <thead>
                          <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Date</th>
                            <th scope="col">Plante</th>
                            <th scope="col">étape de croissance</th>
                            <th scope="col">Notes</th>
                            <th scope="col">Action</th>
                          </tr>
                        </thead>
                        <tbody>
                            <%  
                                List<JournalCulture> listJournalCulture = (List<JournalCulture>) request.getAttribute("listJournalCulture");
                                if (listJournalCulture.isEmpty()) { %>
                                <tr><td colspan="6" class="text-center">Vide</td></tr>
                            <% } else {
                                for (JournalCulture jc : listJournalCulture) { %>
                                <tr>
                                    <th scope="row"><%= jc.getId() %></th>
                                    <td><%= jc.getDate() %></td>
                                    <td><%= jc.getPlante().toString() %> <span hidden=""><%= jc.getPlante().getId() %></span></td>
                                    <td><%= jc.getEtapeCroissance() %></td>
                                    <td><%= jc.getNotes() %></td>
                                    <td>
                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalEditJournalCulture" onclick="editJournalCulture(this)">edit</button>
                                        <a class="btn btn-danger" href="JournalCultureServelet?action=remove&id=<%= jc.getId() %>">remove</a>
                                    </td>
                                </tr>  
                            <% } } %>
                        </tbody>
                      </table>
                <% } %>
                
                <!-- recoltes -->
                <% if(request.getParameter("addRecolte") != null){ %>
                <script>showCollapse("recolte-collapse");</script>
                    <h3 class="mb-5">Ajouter récolte</h3>
                    
                    <form id="formRecolte" method="post" action="RecolteServelet?action=create">
                        <div class="form-floating mb-3">
                            <input type="date" class="form-control rounded-4" name="date" id="floatingDate" placeholder="Date" required>
                            <label for="floatingDate">Date</label>
                        </div>

                        <div class="mb-3">
                            <label for="selectPlante" class="form-label">Plante</label>
                            <select id="selectPlante" class="form-select" name="plante">
                            <%  
                                List<Plante> listPlante = (List<Plante>) request.getAttribute("listPlante");
                                if (listPlante.isEmpty()) { %>
                                <option value="" selected="" disabled>Vide</option>
                            <% } else {
                                for (Plante p : listPlante) { %>
                                <option value="<%= p.getId() %>"><%= p.toString() %></option>
                            <% } } %>
                            </select>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="number" class="form-control rounded-4" name="rendement" id="floatingRendement" placeholder="Rendement en gramme" required>
                            <label for="floatingRendement">Rendement en gramme</label>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="text" class="form-control rounded-4" name="qualite" id="floatingQualite" placeholder="Qualité" required>
                            <label for="floatingQualite">Qualité</label>
                        </div>

                        <button class="w-100 mb-2 btn btn-lg rounded-4 btn-success" type="submit" <%= listPlante.isEmpty() ? "disabled" : "" %>>Valider</button>
                    </form>
                <% } %>
                <% if(request.getParameter("listRecolte") != null){ %>
                <script>showCollapse("recolte-collapse");</script>
                    <h3 class="mb-5">Liste récolte</h3>
                
                    <table class="table">
                        <thead>
                          <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Date</th>
                            <th scope="col">Plante</th>
                            <th scope="col">Rendement (g)</th>
                            <th scope="col">Qualité</th>
                            <th scope="col">Action</th>
                          </tr>
                        </thead>
                        <tbody>
                            <%  
                                List<Recolte> listRecolte = (List<Recolte>) request.getAttribute("listRecolte");
                                if (listRecolte.isEmpty()) { %>
                                <tr><td colspan="6" class="text-center">Vide</td></tr>
                            <% } else {
                                for (Recolte r : listRecolte) { %>
                                <tr>
                                    <th scope="row"><%= r.getId() %></th>
                                    <td><%= r.getDate() %></td>
                                    <td><%= r.getPlante().toString() %> <span hidden=""><%= r.getPlante().getId() %></span></td>
                                    <td><%= r.getRendement() %></td>
                                    <td><%= r.getQualite() %></td>
                                    <td>
                                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalEditRecolte" onclick="editRecolte(this)">edit</button>
                                        <a class="btn btn-danger" href="RecolteServelet?action=remove&id=<%= r.getId() %>">remove</a>
                                    </td>
                                </tr>
                            <% } } %>
                        </tbody>
                      </table>
                <% } %>
            </div>
        </div>
    </main>
    <script src="bootstrap/dist/js/bootstrap.bundle.min.js" ></script>
    <script src="inc/js/sidebars.js"></script>
    <script src="inc/js/password-toggle.js"></script> 
</body>
</html>

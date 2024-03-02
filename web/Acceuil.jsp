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
    <div id="alertPlaceholder"></div>
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
                  <li><a href="Acceuil.jsp?listPlante" class="link-dark rounded">Liste</a></li>
                  <li><a href="Acceuil.jsp?addPlante" class="link-dark rounded">Ajouter</a></li>
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#salleCulture-collapse" aria-expanded="false">
                Salles de culture
              </button>
              <div class="collapse" id="salleCulture-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                    <li><a href="Acceuil.jsp?SearchSalleCulture" class="link-dark rounded">Recherche</a></li>
                    <li><a href="Acceuil.jsp?listSalleCulture" class="link-dark rounded">Liste</a></li>
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
                  <li><a href="Acceuil.jsp?listJournalCulture" class="link-dark rounded">Liste</a></li>
                  <li><a href="Acceuil.jsp?addJournalCulture" class="link-dark rounded">Ajouter</a></li>
                </ul>
              </div>
            </li>
            <li class="mb-1">
              <button class="btn btn-toggle align-items-center rounded collapsed" data-bs-toggle="collapse" data-bs-target="#recolte-collapse" aria-expanded="false">
                Récoltes
              </button>
              <div class="collapse" id="recolte-collapse">
                <ul class="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                  <li><a href="Acceuil.jsp?listRecolte" class="link-dark rounded">Liste</a></li>
                  <li><a href="Acceuil.jsp?addRecolte" class="link-dark rounded">Ajouter</a></li>
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
                            <input class="form-control" type="password" name="password" id="password" placeholder="Mot de passe" aria-describedby="validationServerPasswordFeedback" required>
                            <span class="input-group-text password-toggle" id="passwordToggle">
                                <i class="bi bi-eye"></i>
                            </span>
                            <div id="validationServerPasswordFeedback" class="invalid-feedback">
                                Mot de passe incorrecte.
                            </div>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="password" class="form-control rounded-4" name="newPassword" id="floatingPassword" placeholder="Nouveau mot de passe" required>
                            <label for="floatingPassword">Nouveau mot de passe</label>
                        </div>

                        <div class="form-floating mb-3">
                            <input type="password" class="form-control rounded-4" name="passwordConf" id="floatingPasswordConfirmation" placeholder="Mot de passe" required>
                            <label for="floatingPasswordConfirmation">Confirmer le mot de passe</label>
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
                    
                <% } %>

                <% if(request.getParameter("listPlante") != null){ %>
                <script>showCollapse("plantes-collapse");</script>
                    
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
                            <label for="floatingFirstName">Température</label>
                        </div>

                        <div class="form-floating mb-3">
                          <input type="number" class="form-control rounded-4" name="humidite" id="floatingHumidite" placeholder="Humidité" required>
                          <label for="floatingHumidite">Humidité</label>
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
                            <%  SalleCulture salle = new SalleCulture();
                                List<SalleCulture> listSalleCulture = salle.getAll();
                                
                                for (SalleCulture s : listSalleCulture) { %>
                            <tr>
                                <th scope="row"><%= s.getId()%></th>
                                <td><%= s.getNom()%></td>
                                <td><%= s.getTemperature()%></td>
                                <td><%= s.getHumidite()%></td>
                                <td>
                                    <button type="button" class="btn btn-primary" onclick="editSalleCulture(this)">edit</button>
                                    <a class="btn btn-danger" href="SalleCultureServelet?action=remove&id=<%= s.getId()%>">remove</a>
                                </td>
                            </tr>  
                            <% } %>
                        </tbody>
                      </table>
                <% } %>

                <!-- journalCulture -->
                <% if(request.getParameter("addJournalCulture") != null){ %>
                <script>showCollapse("journalCulture-collapse");</script>

                <% } %>

                <% if(request.getParameter("listJournalCulture") != null){ %>
                <script>showCollapse("journalCulture-collapse");</script>

                <% } %>
            </div>
        </div>
    </main>
    <script src="bootstrap/dist/js/bootstrap.bundle.min.js" ></script>
    <script src="inc/js/sidebars.js"></script>
    <script src="inc/js/password-toggle.js"></script> 
</body>
</html>

<%-- 
    Document   : login
    Created on : 18 févr. 2024, 09:34:35
    Author     : PC
--%>
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
            var inpPseudo = document.getElementById('pseudo');
            if("<%= error %>" === "auth_1"){
                inpPseudo.classList.add('is-invalid');
                inpPassword.className = 'form-control rounded-4';
            }
            else if ("<%= error %>" === "auth_2"){
                inpPassword.classList.add('is-invalid');
                inpPseudo.className = 'form-control rounded-4';
            }
            else {
                alert('<%= error%>','danger');
            }
        };
    </script>
 <% } %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="inc/css/login.css">
    <link rel="stylesheet" href="bootstrap/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="bootstrap-icons/font/bootstrap-icons.css">
    <script src="bootstrap/dist/js/bootstrap.min.js"></script>
    <title>Connexion</title>
</head>
<body class="text-center">
    <div id="alertPlaceholder"></div>
    <div class="modal modal-signin py-5" tabindex="-1" role="dialog" id="modalSignin">
        <div class="modal-dialog" role="document">
          <div class="modal-content rounded-5 shadow">
            <div class="modal-header p-5 pb-4 border-bottom-0">
              <!-- <h5 class="modal-title">Modal title</h5> -->
              <h2 class="fw-bold mb-0">Création d'un compte</h2>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
      
            <div class="modal-body p-5 pt-0">  
              <form id="formSingin" method="post" action="UtilisateurServelet?action=create">
                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="nom" id="floatingName" placeholder="Nom" required>
                    <label for="floatingName">Nom</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="text" class="form-control rounded-4" name="prenom" id="floatingFirstName" placeholder="Prénom" required>
                    <label for="floatingFirstName">Prénom</label>
                </div>

                <div class="form-floating mb-3">
                  <input type="text" class="form-control rounded-4" name="pseudo" id="floatingPseudo" placeholder="Pseudo" required>
                  <label for="floatingPseudo">Pseudo</label>
                </div>
                    
                <div class="form-floating mb-3">
                  <input type="email" class="form-control rounded-4" name="email" id="floatingEmail" placeholder="E-mail" required>
                  <label for="floatingEmail">E-mail</label>
                </div>

                <div class="form-floating mb-3">
                  <input type="password" class="form-control rounded-4" name="password" id="floatingPassword" placeholder="Mot de passe" required>
                  <label for="floatingPassword">Mot de passe</label>
                </div>

                <div class="form-floating mb-3">
                    <input type="password" class="form-control rounded-4" name="passwordConf" id="floatingPasswordConfirmation" placeholder="Mot de passe" required>
                    <label for="floatingPasswordConfirmation">Confirmer le mot de passe</label>
                </div>

                <button class="w-100 mb-2 btn btn-lg rounded-4 btn-success" type="submit">Valider</button>
              </form>
            </div>
          </div>
        </div>
      </div>

    <main class="form-signin">
        <img class="mb-5" src="img/medical-cannabis.png" width="140" height="140" alt="medical-cannabis"/>
        <form id="formLogin" method="post" action="UtilisateurServelet?action=login">
            <div class="d-flex flex-column align-items-center gap-4 mb-4">
                <div class="input-group">
                    <span class="input-group-text" id="basic-addon1">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                            <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"></path>
                            <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"></path>
                        </svg>
                    </span>
                    <input type="text" class="form-control" name="pseudo" id="pseudo" placeholder="Pseudo" aria-describedby="validationServerPseudoFeedback" required>
                    <div id="validationServerPseudoFeedback" class="invalid-feedback">
                        Pseudo incorrecte.
                    </div>
                </div>

                <div class="input-group has-password-toggle position-relative">
                    <span class="input-group-text" id="basic-addon1">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-key" viewBox="0 0 16 16">
                            <path d="M0 8a4 4 0 0 1 7.465-2H14a.5.5 0 0 1 .354.146l1.5 1.5a.5.5 0 0 1 0 .708l-1.5 1.5a.5.5 0 0 1-.708 0L13 9.207l-.646.647a.5.5 0 0 1-.708 0L11 9.207l-.646.647a.5.5 0 0 1-.708 0L9 9.207l-.646.647A.5.5 0 0 1 8 10h-.535A4 4 0 0 1 0 8zm4-3a3 3 0 1 0 2.712 4.285A.5.5 0 0 1 7.163 9h.63l.853-.854a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.646-.647a.5.5 0 0 1 .708 0l.646.647.793-.793-1-1h-6.63a.5.5 0 0 1-.451-.285A3 3 0 0 0 4 5z"/>
                            <path d="M4 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
                        </svg>
                    </span>
                    <input class="form-control" type="password" name="password" id="password" placeholder="Mot de passe" aria-describedby="validationServerPasswordFeedback" required>
                    <span class="input-group-text password-toggle" id="passwordToggle">
                        <i class="bi bi-eye"></i>
                    </span>
                    <div id="validationServerPasswordFeedback" class="invalid-feedback">
                        Mot de passe incorrecte.
                    </div>
                </div>
            </div>
            <button type="submit" class="btn btn-success">Connexion</button>
        </form>
        <br>
        <br>
        <a class="text-success" href="#" data-bs-toggle="modal" data-bs-target="#modalSignin">S'inscrire</a>      
    </main>      
</body>
<footer>
    <script src="inc/js/password-toggle.js"></script> 
    <script type="module" src="popper/dist/popper.min.js"></script>

</footer>
</html>

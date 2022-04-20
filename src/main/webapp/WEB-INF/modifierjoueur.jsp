<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="fr">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="starter-template.css">
    <title>Modifier un joueur</title>
  </head>
  <body>
    <%@ include file="menu.jsp" %>

<main role="main" class="container">

  <div class="starter-template">
    <h1>Modifier un joueur</h1>
    <p class="lead"><c:if test="${ !empty erreur }"><p style="color:red;"><c:out value="${ erreur }" /></p></c:if>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
  </div>
   <div style="width:40%; margin:auto;"></div>

<form class="needs-validation " novalidate method="post" action="ModifierJoueur">
  <div class="form-row">
    <div class="col-md-4 mb-3">
		<label for="modifId">ID du joueur</label>
		<input type="number" class="form-control" style="width:400px;" id="modifId" name="modifId" value="${joueur.id}" readonly="readonly" required>
      </div>
  </div>
  <div class="form-row">
    <div class="col-md-4 mb-3">
      <label for="modifNom">Nom du joueur</label>
      <input type="text" class="form-control" style="width:400px;" id="modifNom" name="modifNom" value="${joueur.nom}" required>
		  <div class="valid-feedback">
        Tr�s bien!
      </div>
    </div>
  </div> 
  <div class="form-row">
	<div class="col-md-4 mb-3">
	  <label for="modifPrenom">Pr�nom du joueur</label>
	  <input type="text" class="form-control" style="width:400px;" id="modifPrenom" name="modifPrenom" value="${joueur.prenom}" required>
	  <div class="valid-feedback">
       Tr�s bien!
     </div>
	</div>
   </div> 
  <div class="form-row">
    <div class="col-md-3 mb-3">
      <label for="modifSexe">Sexe</label>
      <select class="custom-select" id="modifSexe" name="modifSexe" style="width:400px;" required> 
      	<c:if test="${joueur.sexe == 'F'}" var="selected">
    		<option selected value='F'>Femme</option>
			<option value='H'>Homme</option>
      	</c:if>
      	<c:if test="${!selected}">
     	     <option  value='F'>Femme</option>
			<option selected value='H'>Homme</option>
      	</c:if>
      </select>
	   <div class="valid-feedback">
       Tr�s bien!
     </div>
      <div class="invalid-feedback">
        Veuillez choisir un sexe!
      </div>
    </div>  
    </div>
  
  <button class="btn btn-primary center" type="submit">Editer</button>
</form>

   <div>

</main><!-- /.container -->

<script>
// Example starter JavaScript for disabling form submissions if there are invalid fields
(function() {
  'use strict';
  window.addEventListener('load', function() {
    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    var forms = document.getElementsByClassName('needs-validation');
    // Loop over them and prevent submission
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);
})();
</script>


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
  </body>
</html>
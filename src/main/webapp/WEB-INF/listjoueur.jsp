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
    <title>Liste des joueurs</title>
  </head>
  <body>
<%@ include file="menu.jsp" %>
<main role="main" class="container">
  
  <div class="starter-template">
    <h1>Liste des joueurs</h1>
  <img class="mb-4" src="tennis-5782695_1920.jpg" style="width:500px; height:auto;"  alt="image1">
  </div>


</main><!-- /.container -->
<div class="container">

<div style="    padding: 1.5rem;    margin-right: 0;    margin-left: 0;    border-width: .2rem;">
<a class="btn btn-primary" href="/Appli_tennis/AjouterJoueur" role="button">Ajouter un joueur</a>
</div>

<form class="needs-validation "  novalidate method="post" action="ListJoueur">
<p>Selectionnez un genre </p>
<select class="custom-select" id="inputChoixSexe" name="inputChoixSexe" style="width:400px;" required>
      <option <c:if test="${ choixSexe.equals('Tous')}"> selected</c:if> value="Tous">Tous</option>
      <option <c:if test="${ choixSexe.equals('F')}"> selected</c:if> value="F">Femmes</option>
	  <option <c:if test="${ choixSexe.equals('H')}"> selected</c:if> value="H">Hommes</option>
</select>
<button class="btn btn-primary center" type="submit">Valider la s?lection</button>
</form>

    <p><c:if test="${liste[0] == null}"><strong>Aucun joueur ne correspond ? votre recherche</strong></c:if></p>
    
    <c:if test="${liste[0] != null }">
    
<table class="table">
  <thead>
    <tr>
      <th scope="col" style="width:10%">#</th>
      <th scope="col" style="width:25%">Nom</th>
      <th scope="col" style="width:20%">Prenom</th>
      <th scope="col" style="width:20%">Sexe</th>
	  <th scope="col" style="width:20%"></th>
    </tr>
  </thead>
  <tbody>
   
	    <c:forEach var="joueur" items="${liste}">
		    <c:if test="${joueur.getSexe().equals(choixSexe)}">
			    <tr>
			      <th ><c:out value="${joueur.getId()}"></c:out></th>
			      <td ><c:out value="${joueur.getNom()}"></c:out></td>
			      <td ><c:out value="${joueur.getPrenom()}"></c:out></td>
			      <td ><c:out value="${joueur.getSexe()}"></c:out></td>
				  <td>
				    <a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/ModifierJoueur?id=${joueur.getId()}" role="button">Modifier</a>
					<a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/SupprimerJoueur?id=${joueur.getId()}" role="button">Supprimer</a>
				  </td>
			    </tr>
		    </c:if>
		    <c:if test="${choixSexe.equals('Tous')}">
			    <tr>
			      <th ><c:out value="${joueur.getId()}"></c:out></th>
			      <td ><c:out value="${joueur.getNom()}"></c:out></td>
			      <td ><c:out value="${joueur.getPrenom()}"></c:out></td>
			      <td ><c:out value="${joueur.getSexe()}"></c:out></td>
				  <td>
				    <a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/ModifierJoueur?id=${joueur.getId()}" role="button">Modifier</a>
					<a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/SupprimerJoueur?id=${joueur.getId()}" role="button">Supprimer</a>
				  </td>
			    </tr>
		    </c:if>
	    </c:forEach>
  </tbody>
</table>
	</c:if>
</div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
  </body>
</html>



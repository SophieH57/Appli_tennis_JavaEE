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
    <title>Liste des matchs</title>
  </head>
  <body>
<%@ include file="menu.jsp" %>
<main role="main" class="container">

  <div class="starter-template">
    <h1>Liste des matchs</h1>
    <p class="lead">Bienvenue .... Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolor.</p>
  </div>

</main><!-- /.container -->
<div class="container">

<div style="    padding: 1.5rem;    margin-right: 0;    margin-left: 0;    border-width: .2rem;">
<a class="btn btn-primary" href="/Appli_tennis/AjouterMatch" role="button">Ajouter un match</a>
</div>

<table class="table">
  <thead>
    <tr>
      <th scope="col" style="width:5%">ID du Match</th>
      <th scope="col" style="width:15%">Tournoi</th>
      <th scope="col" style="width:5%">Année</th>
	  <th scope="col" style="width:15%">Type de l'épreuve</th>
	  <th scope="col" style="width:15%">Nom du vainqueur</th>
	  <th scope="col" style="width:15%">Prénom du vainqueur</th>
  	  <th scope="col" style="width:15%">Nom du finaliste</th>
	  <th scope="col" style="width:15%">Prénom du finaliste</th>
    </tr>
  </thead>
  <tbody>
   
    <c:forEach var="match" items="${listeMatchs}">
    <tr>
      <th ><c:out value="${match.idMatch}"></c:out></th>
      <td ><c:out value="${match.tournoi.nomTournoi}"></c:out></td>
      <td ><c:out value="${match.annee}"></c:out></td>
      <td ><c:out value="${match.typeEpreuve}"></c:out></td>
      <td ><c:out value="${match.vainqueur.nom}"></c:out></td>
      <td ><c:out value="${match.vainqueur.prenom}"></c:out></td>
      <td ><c:out value="${match.finaliste.nom}"></c:out></td>
      <td ><c:out value="${match.finaliste.prenom}"></c:out></td>
	  <td>
	    <a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/ModifierMatch?id=${match.idMatch}" role="button">Modifier</a>
		<a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/SupprimerMatch?id=${match.idMatch}" role="button">Supprimer</a>
	  </td>
    </tr>
    </c:forEach>

  </tbody>
</table>
</div>

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
  </body>
</html>
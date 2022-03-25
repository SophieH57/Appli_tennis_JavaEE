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
    <title>Liste des tournois</title>
  </head>
  <body>
<%@ include file="menu.jsp" %>
<main role="main" class="container">

  <div class="starter-template">
    <h1>Liste des tournois</h1>
    <p class="lead">Bienvenue .... Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolor.</p>
  </div>

</main><!-- /.container -->
<div class="container">

<div style="    padding: 1.5rem;    margin-right: 0;    margin-left: 0;    border-width: .2rem;">
<a class="btn btn-primary" href="/Appli_tennis/AjouterTournoi" role="button">Ajouter un tournoi</a>
</div>

<p> <c:if test="${listeTournois[0] == null}"><strong>Aucun tournoi ne correspond à votre recherche</strong></c:if></p>

<c:if test="${listeTournois[0] != null}">
	<table class="table">
	  <thead>
	    <tr>
	      <th scope="col" style="width:10%">#</th>
	      <th scope="col" style="width:25%">Code</th>
	      <th scope="col" style="width:20%">Nom</th>
		  <th scope="col" style="width:20%"></th>
	    </tr>
	  </thead>
	  <tbody>
	   
	    <c:forEach var="tournoi" items="${listeTournois}">
	    <tr>
	      <th ><c:out value="${tournoi.idTournoi}"></c:out></th>
	      <td ><c:out value="${tournoi.codeTournoi}"></c:out></td>
	      <td ><c:out value="${tournoi.nomTournoi}"></c:out></td>
		  <td>
		    <a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/ModifierTournoi?id=${tournoi.idTournoi}" role="button">Modifier</a>
			<a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/SupprimerTournoi?id=${tournoi.idTournoi}" role="button">Supprimer</a>
		  </td>
	    </tr>
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
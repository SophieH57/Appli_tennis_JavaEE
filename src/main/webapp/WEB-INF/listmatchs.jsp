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
    <img class="mr-10" src="match1.jpg" style="width:200px; height:auto;"  alt="">
    <img class="mr-10" src="match2.jpg" style="width:200px; height:auto;"  alt="">
    <img class="mr-10" src="match3.jpg" style="width:200px; height:auto;"  alt="">
  </div>
  

</main><!-- /.container -->
<div class="container">

<div style="    padding: 1.5rem;    margin-right: 0;    margin-left: 0;    border-width: .2rem;">
<a class="btn btn-primary mb-10" href="/Appli_tennis/AjouterMatch" role="button">Ajouter un match</a>
</div>

<form class="needs-validation "  novalidate method="post" action="ListMatchs" style="display : flex;">
<div>
	<p>Selectionner vainqueurs et/ou finalistes</p>
	<select class="custom-select" id="validationCustom04" name="inputChoix" style="width:300px; margin-right: 10px;" required>
	    <option <c:if test="${ choixTypeJoueur.equals('Tous')}"> selected</c:if> value="Tous">Tous</option>
	    <option <c:if test="${ choixTypeJoueur.equals('Vainqueurs')}"> selected</c:if> value="Vainqueurs">Vainqueurs</option>
	  	<option <c:if test="${ choixTypeJoueur.equals('Finalistes')}"> selected</c:if> value="Finalistes">Finalistes</option>
	</select>
</div>

<div>
	<p>Sélectionner une année</p>
	<select class="custom-select" id="validationCustom04" name="choixAnnee" style="width:300px; margin-right: 10px;" required>
        <option <c:if test="${anneeChoisie.equals('Toutes')}">selected</c:if> value="Toutes">Toutes</option>
        <c:forEach var="annee" items="${listeAnnees}">
        	<option <c:if test="${anneeChoisie.equals(annee)}">selected</c:if> value="${annee}"><c:out value="${annee}"></c:out></option>
        </c:forEach>
	</select>
</div>

<div>
	<p>Sélectionner un type d'épreuve</p>
	<select class="custom-select" id="validationCustom04" name="choixEpreuve" style="width:300px; margin-right: 10px;" required>
        <option <c:if test="${epreuveChoisie.equals('Toutes')}">selected</c:if> value="Toutes">Toutes</option>
   		<option <c:if test="${ epreuveChoisie.equals('F')}"> selected</c:if> value="F">F</option>
	  	<option <c:if test="${ epreuveChoisie.equals('H')}"> selected</c:if> value="H">H</option>
	</select>
</div>
		<button class="btn btn-primary center" type="submit">Valider la sélection</button>
</form>

<p> <c:if test="${listeMatchs[0] == null}"><strong>Aucun match ne correspond à votre recherche</strong></c:if></p>

<c:if test="${listeMatchs[0] != null}">
	<table class="table">
	  <thead>
	    <tr>
	      <th scope="col" style="width:15%">Id Match</th>
	      <th scope="col" style="width:15%">Tournoi</th>
	      <th scope="col" style="width:5%">Année</th>
		  <th scope="col" style="width:15%">Type de l'épreuve</th>
		  <c:if test="${choixTypeJoueur == 'Vainqueurs'}">
		  	<th scope="col" style="width:15%">Vainqueur</th>
		  </c:if>
		  <c:if test="${choixTypeJoueur == 'Finalistes'}">
	  	  	<th scope="col" style="width:15%">Finaliste</th>
	  	  </c:if>
	  	  <c:if test="${choixTypeJoueur == 'Tous'}">
	  	  	<th scope="col" style="width:15%">Vainqueur</th>
	  	  	<th scope="col" style="width:15%">Finaliste</th>
	  	  </c:if>
	    </tr>
	  </thead>
	  <tbody>
	   
	  
	    <c:forEach var="match" items="${listeMatchs}">
	    <!-- Affichage avec toutes les épreuves -->
	    	<c:if test="${epreuveChoisie.equals('Toutes')}">
		    	<!--  Affichage en fonction de l'année choisie -->
		    	<c:if test="${anneeChoisie.equals(Integer.toString(match.annee))}">
				    <tr>  
				      <td ><c:out value="${match.idMatch}"></c:out></td>
				      <td ><c:out value="${match.tournoi.nomTournoi}"></c:out></td>
				      <td ><c:out value="${match.annee}"></c:out></td>
				      <td ><c:out value="${match.typeEpreuve}"></c:out></td>
			    	  <c:if test="${choixTypeJoueur == 'Vainqueurs'}">
					  	<td ><c:out value="${match.vainqueur.nom} ${match.vainqueur.prenom}"></c:out></td>
					  </c:if>
				      <c:if test="${choixTypeJoueur == 'Finalistes'}">
				  	  	<td ><c:out value="${match.finaliste.nom} ${match.finaliste.prenom}"></c:out></td>
				  	  </c:if>
				  	  <c:if test="${choixTypeJoueur == 'Tous'}">
					  	<td ><c:out value="${match.vainqueur.nom} ${match.vainqueur.prenom}"></c:out></td>
				  	  	<td ><c:out value="${match.finaliste.nom} ${match.finaliste.prenom}"></c:out></td>
				  	  </c:if>
					  <td>
					    <a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/ModifierMatch?id=${match.idMatch}" role="button">Modifier</a>
						<a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/SupprimerMatch?id=${match.idMatch}" role="button">Supprimer</a>
					  </td>
				    </tr>
			    </c:if>
		    </c:if>
	    </c:forEach>
		
		<c:forEach var="match" items="${listeMatchs}">    
	    <!-- Affichage avec toutes les années -->
		    <c:if test="${anneeChoisie.equals('Toutes')}">
		    <!-- Choix du type d'épreuve -->
		    <c:if test="${epreuveChoisie == match.typeEpreuve}">
				    <tr>  
				      <td ><c:out value="${match.idMatch}"></c:out></td>
				      <td ><c:out value="${match.tournoi.nomTournoi}"></c:out></td>
				      <td ><c:out value="${match.annee}"></c:out></td>
				      <td ><c:out value="${match.typeEpreuve}"></c:out></td>
			    	  <c:if test="${choixTypeJoueur == 'Vainqueurs'}">
					  	<td ><c:out value="${match.vainqueur.nom} ${match.vainqueur.prenom}"></c:out></td>
					  </c:if>
				      <c:if test="${choixTypeJoueur == 'Finalistes'}">
				  	  	<td ><c:out value="${match.finaliste.nom} ${match.finaliste.prenom}"></c:out></td>
				  	  </c:if>
				  	  <c:if test="${choixTypeJoueur == 'Tous'}">
					  	<td ><c:out value="${match.vainqueur.nom} ${match.vainqueur.prenom}"></c:out></td>
				  	  	<td ><c:out value="${match.finaliste.nom} ${match.finaliste.prenom}"></c:out></td>
				  	  </c:if>
					  <td>
					    <a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/ModifierMatch?id=${match.idMatch}" role="button">Modifier</a>
						<a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/SupprimerMatch?id=${match.idMatch}" role="button">Supprimer</a>
					  </td>
				    </tr>
			    </c:if>
		    </c:if>
	    </c:forEach>
	
	<c:forEach var="match" items="${listeMatchs}">
	    <!-- Affichage avec toutes les épreuves -->
	    	<c:if test="${epreuveChoisie.equals('Toutes')}">
		    	<!--  Affichage avec toutes les années -->
		    	<c:if test="${anneeChoisie.equals('Toutes')}">
				    <tr>  
				      <td ><c:out value="${match.idMatch}"></c:out></td>
				      <td ><c:out value="${match.tournoi.nomTournoi}"></c:out></td>
				      <td ><c:out value="${match.annee}"></c:out></td>
				      <td ><c:out value="${match.typeEpreuve}"></c:out></td>
			    	  <c:if test="${choixTypeJoueur == 'Vainqueurs'}">
					  	<td ><c:out value="${match.vainqueur.nom} ${match.vainqueur.prenom}"></c:out></td>
					  </c:if>
				      <c:if test="${choixTypeJoueur == 'Finalistes'}">
				  	  	<td ><c:out value="${match.finaliste.nom} ${match.finaliste.prenom}"></c:out></td>
				  	  </c:if>
				  	  <c:if test="${choixTypeJoueur == 'Tous'}">
					  	<td ><c:out value="${match.vainqueur.nom} ${match.vainqueur.prenom}"></c:out></td>
				  	  	<td ><c:out value="${match.finaliste.nom} ${match.finaliste.prenom}"></c:out></td>
				  	  </c:if>
					  <td>
					    <a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/ModifierMatch?id=${match.idMatch}" role="button">Modifier</a>
						<a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/SupprimerMatch?id=${match.idMatch}" role="button">Supprimer</a>
					  </td>
				    </tr>
			    </c:if>
		    </c:if>
		</c:forEach>
		
		<c:forEach var="match" items="${listeMatchs}">
	    <!-- Affichage avec choix d'un type d'épreuve -->
	    	<c:if test="${epreuveChoisie.equals(match.typeEpreuve)}">
		    	<!--  Affichage avec choix d'une année -->
		    	<c:if test="${anneeChoisie.equals(Integer.toString(match.annee))}">
				    <tr>  
				      <td ><c:out value="${match.idMatch}"></c:out></td>
				      <td ><c:out value="${match.tournoi.nomTournoi}"></c:out></td>
				      <td ><c:out value="${match.annee}"></c:out></td>
				      <td ><c:out value="${match.typeEpreuve}"></c:out></td>
			    	  <c:if test="${choixTypeJoueur == 'Vainqueurs'}">
					  	<td ><c:out value="${match.vainqueur.nom} ${match.vainqueur.prenom}"></c:out></td>
					  </c:if>
				      <c:if test="${choixTypeJoueur == 'Finalistes'}">
				  	  	<td ><c:out value="${match.finaliste.nom} ${match.finaliste.prenom}"></c:out></td>
				  	  </c:if>
				  	  <c:if test="${choixTypeJoueur == 'Tous'}">
					  	<td ><c:out value="${match.vainqueur.nom} ${match.vainqueur.prenom}"></c:out></td>
				  	  	<td ><c:out value="${match.finaliste.nom} ${match.finaliste.prenom}"></c:out></td>
				  	  </c:if>
					  <td>
					    <a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/ModifierMatch?id=${match.idMatch}" role="button">Modifier</a>
						<a type="submit" class="btn btn-outline-primary" href="/Appli_tennis/SupprimerMatch?id=${match.idMatch}" role="button">Supprimer</a>
					  </td>
				    </tr>
			    </c:if>
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
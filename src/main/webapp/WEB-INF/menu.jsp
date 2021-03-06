<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">  
  <img class="mb-4" src="plogo.png" style="width:25px;"  alt="">
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
 <div class="collapse navbar-collapse" id="navbarsExampleDefault">
    <ul class="navbar-nav mr-auto">
      
      
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Ajouter</a>
        <div class="dropdown-menu" aria-labelledby="dropdown01">
          <a class="dropdown-item" href="/Appli_tennis/AjouterJoueur">Ajouter un joueur</a>
          <a class="dropdown-item" href="/Appli_tennis/AjouterTournoi">Ajouter un tournoi</a>
          <a class="dropdown-item" href="/Appli_tennis/AjouterMatch">Ajouter un  match</a>
        </div>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Lister</a>
        <div class="dropdown-menu" aria-labelledby="dropdown01">
          <a class="dropdown-item" href="/Appli_tennis/ListJoueur">Lister les joueurs</a>
          <a class="dropdown-item" href="/Appli_tennis/ListTournois">Lister les tournois</a>
          <a class="dropdown-item" href="/Appli_tennis/ListMatchs">Lister les  matchs</a>
        </div>
      </li>
	
       <li class="nav-item">
	    <form class="form-inline my-2 my-lg-0" method="post" action="Deconnexion">
	    <input type="hidden" name="utilisateur" value="${utilisateur}"/>
         <button class="btn btn-secondary my-2 my-sm-0" type="submit" name="deconnexion" value="Deconnexion">Deconnexion</button>
         </form>
      </li>
     
    </ul>
    <form class="form-inline my-2 my-lg-0" action="Rechercher" method="post">
      <input type="hidden" name="page" value="${page}"/>
      <h1 class="h3 mb-3 font-weight-normal mr-5" style="color:#FFF"> Bonjour ${utilisateur.login }</h1>
      <input class="form-control mr-sm-2" type="text" name="txtsearch" placeholder="Search" aria-label="Search">
      <button class="btn btn-secondary my-2 my-sm-0" type="submit" >Rechercher</button>
    </form>
  </div>
</nav>

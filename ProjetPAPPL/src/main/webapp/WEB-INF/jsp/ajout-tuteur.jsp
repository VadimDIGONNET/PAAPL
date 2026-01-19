<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter une Entreprise</title>
    <link href="css/ajout.css" type="text/css" rel="stylesheet" />
    <div class="logo-container"> <a href="#"> 
        <img src="img/LogoECN.png" alt="Logo Centrale Nantes" class="fixed-logo"> </a>
    </div>
</head>
<body>

<div class="container">
    <h2>Nouveaux tuteurs</h2>
    
    <form action="savetuteur.do" method="POST">
        <input type="hidden" name="id" value="${tuteur.id != null ? tuteur.id : -1}">

        <div class="form-group">
            <label for="nom">Nom du tuteur :</label>
            <input type="text" id="nom" name="nom" value="${tuteur.nom}" required>
        </div>

        <div class="form-group">
            <label for="prenom">Prenom du tuteur :</label>
            <input type="text" id="prenom" name="prenom" value="${tuteur.prenom}">
        </div>

        <div class="form-group">
            <label for="nbsujet">Nombre de sujet souhaité :</label>
            <input type="number" id="nbsujet" name="nbsujet" value="${tuteur.nbSujetSouhaite}">
        </div>

        <button type="submit">Enregistrer le tuteur</button>
        <a href="main.do" style="margin-left: 10px; color: #666; text-decoration: none;">Annuler</a>
    </form>
</div>

</body>
</html>
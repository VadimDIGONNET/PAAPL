<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un eleve</title>
    <link href="css/ajout.css" type="text/css" rel="stylesheet" />
    <div class="logo-container"> <a href="#"> 
        <img src="img/LogoECN.png" alt="Logo Centrale Nantes" class="fixed-logo"> </a>
    </div>
</head>
<body>

<div class="container">
    <h2>Nouvel Eleve</h2>
    
    <form action="saveeleve.do" method="POST">
           <input type="hidden" name="id" value="${eleve.id}" />
        <div class="form-group">
            <label for="nom">Nom </label>
            <input type="text" id="nom" name="nom" value="${eleve.nom}" required>
        </div>

        <div class="form-group">
            <label for="adresse">Prenom :</label>
            <input type="text" id="prenom" name="prenom" value="${eleve.prenom}" required>
        </div>

        <div class="form-group">
            <label for="codePostal">Nationalite</label>
            <input type="text" id="nationalite" name="nationalite" value="${eleve.nationalite}">
        </div>

        <div class="form-group">
            <label for="tel">email :</label>
            <input type="text" id="email" name="email" value="${eleve.email}">
        </div>

        <div class="form-group">
            <label for="siteWeb">TD :</label>
            <input type="text" id="td" name="td" value="${eleve.td}">
        </div>

        <div class="form-group">
            <label for="description">Numero Sujet</label>
<input id="sujet" type="number" name="numero_sujet" 
       value="${eleve.numeroSujet}" />        </div>

        <button type="submit">Enregistrer l'eleve</button>
        <a href="main.do" style="margin-left: 10px; color: #666; text-decoration: none;">Annuler</a>
    </form>
</div>

</body>
</html>

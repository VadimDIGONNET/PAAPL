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
    <h2>Nouvelle Entreprise</h2>
    
    <form action="saveentreprise.do" method="POST">

        <div class="form-group">
            <label for="nom">Nom de l'entreprise  :</label>
            <input type="text" id="nom" name="nom" value="${entreprise.nom}" required>
        </div>

        <div class="form-group">
            <label for="adresse">Adresse :</label>
            <input type="text" id="adresse" name="adresse" value="${entreprise.adresse}">
        </div>

        <div class="form-group">
            <label for="codePostal">Code Postal :</label>
            <input type="number" id="codePostal" name="codePostal" value="${entreprise.codePostal}">
        </div>

        <div class="form-group">
            <label for="tel">Téléphone :</label>
            <input type="number" id="tel" name="tel" value="${entreprise.tel}">
        </div>

        <div class="form-group">
            <label for="siteWeb">Site Web :</label>
            <input type="text" id="siteWeb" name="siteWeb" value="${entreprise.siteWeb}"placeholder="https://...">
        </div>

        <div class="form-group">
            <label for="description">Description :</label>
            <textarea type="text" id="description" name="description" value="${entreprise.description}" rows="4"></textarea>
        </div>

        <button type="submit">Enregistrer l'entreprise</button>
        <a href="index.do" style="margin-left: 10px; color: #666; text-decoration: none;">Annuler</a>
    </form>
</div>

</body>
</html>

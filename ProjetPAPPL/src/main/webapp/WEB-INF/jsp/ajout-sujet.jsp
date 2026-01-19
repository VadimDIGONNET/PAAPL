<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter une Entreprise</title>
    <style>
        body { font-family: sans-serif; margin: 40px; line-height: 1.6; }
        .form-group { margin-bottom: 15px; }
        label { display: block; font-weight: bold; margin-bottom: 5px; }
        input[type="text"], input[type="number"], textarea {
            width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px;
        }
        button { 
            background-color: #007bff; color: white; padding: 10px 15px; 
            border: none; border-radius: 4px; cursor: pointer; 
        }
        button:hover { background-color: #0056b3; }
        .container { max-width: 500px; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 8px; }
        .icon {
            width: 1.5rem; /* S'adapte à la taille de police globale */
          }
    </style>
</head>
<body>

<div class="container">
    <h2>Nouveau sujet</h2>
    
    <form action="saveentreprise.do" method="POST">

        <div class="form-group">
            <label for="nom">Nom de l'entreprise :</label>
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
            <input id="description" type="text" name="description" value="${entreprise.description}">
        </div>

        <button type="submit">Enregistrer l'entreprise</button>
        <a href="index.do" style="margin-left: 10px; color: #666; text-decoration: none;">Annuler</a>
    </form>
</div>

</body>
</html>
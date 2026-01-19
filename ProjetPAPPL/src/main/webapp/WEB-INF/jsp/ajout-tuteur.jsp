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
    <h2>Nouveaux tuteurs</h2>
    
    <form action="savetuteur.do" method="POST">

        <div class="form-group">
            <label for="nom">Nom du tuteur :</label>
            <input type="text" id="nom" name="nom" value="${tuteur.nom}" required>
        </div>

        <div class="form-group">
            <label for="adresse">Prenom du tuteur :</label>
            <input type="text" id="adresse" name="adresse" value="${tuteur.prenom}">
        </div>

        <div class="form-group">
            <label for="codePostal">Nombre de sujet souhaité par le tuteur :</label>
            <input type="number" id="codePostal" name="codePostal" value="${tuteur.nbsujet}">
        </div>

        <button type="submit">Enregistrer le tuteur</button>
        <button href="index.do" style="margin-left: 10px; color: #666; text-decoration: none;">Annuler</button>
    </form>
</div>

</body>
</html>
<%-- 
    Document   : ajout-correspondant
    Created on : 19 janv. 2026, 14:44:42
    Author     : dodi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un Correspondant</title>
    <style>
        body { font-family: sans-serif; margin: 40px; line-height: 1.6; }
        .form-group { margin-bottom: 15px; }
        label { display: block; font-weight: bold; margin-bottom: 5px; }
        input[type="text"], input[type="email"], input[type="number"], select, textarea {
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
    <h2>
        <c:choose>
            <c:when test="${correspondant.id != null}">
                Modifier le correspondant
            </c:when>
            <c:otherwise>
                Ajouter un nouveau correspondant (Partenaire)
            </c:otherwise>
        </c:choose>
    </h2>
    
    <form action="savecorrespondant.do" method="POST">
        <input type="hidden" name="id" value="${correspondant.id != null ? correspondant.id : -1}">
        
        <div class="form-group">
            <label for="nom">Nom :</label>
            <input type="text" id="nom" name="nom" value="${correspondant.nom}" required>
        </div>
        
        <div class="form-group">
            <label for="prenom">Prénom :</label>
            <input type="text" id="prenom" name="prenom" value="${correspondant.prenom}" required>
        </div>
        
        <div class="form-group">
            <label for="tel">Téléphone :</label>
            <input type="number" id="tel" name="tel" value="${correspondant.tel}">
        </div>
        
        <div class="form-group">
            <label for="email">Email :</label>
            <input type="email" id="email" name="email" value="${correspondant.email}" required>
        </div>
        
        <div class="form-group">
            <label for="entreprise">Entreprise :</label>
            <select id="entreprise" name="entreprise" required>
                <option value="">-- Sélectionner une entreprise --</option>
                <c:forEach var="entreprise" items="${entrepriseList}">
                    <option value="${entreprise.nom}" 
                            <c:if test="${correspondant.entreprise != null && correspondant.entreprise.nom == entreprise.nom}">
                                selected
                            </c:if>>
                        ${entreprise.nom}
                    </option>
                </c:forEach>
            </select>
        </div>
        
        <button type="submit">Enregistrer le correspondant</button>
        <a href="main.do" style="margin-left: 10px; color: #666; text-decoration: none;">Annuler</a>
    </form>
        <br>
        <div class="col-md-4 bg-primary m-2" style="">
        <div class="col-md-12">
            <form action="main.do" method="POST" class="m-2">
                <button type="submit" name="retour au menu" class="btn btn-primary btn-lg">
                    Retourner au menu
                </button>
            </form>   
        </div>
    </div>
</div>
</body>
</html>
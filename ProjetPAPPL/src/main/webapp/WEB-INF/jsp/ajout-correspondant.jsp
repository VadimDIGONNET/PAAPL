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
    <link href="css/ajout.css" type="text/css" rel="stylesheet" />
    <div class="logo-container"> <a href="#"> 
        <img src="img/LogoECN.png" alt="Logo Centrale Nantes" class="fixed-logo"> </a>
    </div>
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
        
</div>
</body>
</html>
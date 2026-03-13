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
        <h2>
            <c:choose>
                <c:when test="${sujet.numero != null}">
                    Modifier le sujet
                </c:when>
                <c:otherwise>
                    Ajouter un nouveau sujet
                </c:otherwise>
            </c:choose>
        </h2>

        <form action="savesujet.do" method="POST">
            <input type="hidden" name="id" value=${sujet.numero}>


            <div class="form-group">
                <label for="nom">Intitulé du sujet :</label>
                <input type="text" id="intitule" name="intitule" value="${sujet.intitule}" required>
            </div>

            <div class="form-group">
                <label for="nom">Numero de sujet :</label>
                <input type="number" id="numeroSujet" name="numeroSujet" value="${sujet.numeroDuSujet}" required>
            </div>

            <div class="form-group">
                <label for="entreprise">Entreprise :</label>
                <select id="entreprise" name="entreprise" required>
                    <option value="">-- Sélectionner une entreprise --</option>
                    <c:forEach var="entreprise" items="${entrepriseList}">
                        <option value="${entreprise.nom}"
                                <c:if test="${sujet.descriptionSujet.nomEntreprise.nom != null && sujet.descriptionSujet.nomEntreprise.nom == entreprise.nom}">
                                    selected
                                </c:if>
                                >
                            ${entreprise.nom}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="correspondant">Correspondant :</label>
                <select id="correspondant" name="correspondant" required>
                    <option value="">-- Sélectionner un correspondant--</option>
                    <c:forEach var="correspondant" items="${correspondantList}">
                        <option value="${correspondant.nom}"
                                <c:if test="${sujet.idCorrespondant.nom != null && sujet.idCorrespondant.nom == correspondant.nom}">
                                    selected
                                </c:if>
                                >
                            ${correspondant.nom}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="adresse"> Description du sujet :</label>
                <input type="text" id="contexte" name="contexte" value="${DescriptionSujet.contexte}">
            </div>

            <div class="form-group">
                <label for="adresse">Travail à réaliser :</label>
                <input type="text" id="travail" name="travail" value="${DescriptionSujet.travailRealiser}">
            </div>

            <div class="form-group">
                <label for="adresse"> Livrables attendus :</label>
                <input type="text" id="livrable" name="livrable" value="${DescriptionSujet.livrable}">
            </div>

            <div class="form-group">
                <label for="confidentiel"> Confidentialité :</label>
                <input type="text" id="confidentiel" name="confidentiel" value="${DescriptionSujet.confidentiel}">
            </div>

            <div class="form-group">
                <label for="annee"> Année :</label>
                <input type="text" id="annee" name="annee" value="${Sujet.annee}">
            </div>

            <button type="submit">Enregistrer le sujet</button>
            <a href="main.do" style="margin-left: 10px; color: #666; text-decoration: none;">Annuler</a>
        </form>
    </div>
</body>
</html>
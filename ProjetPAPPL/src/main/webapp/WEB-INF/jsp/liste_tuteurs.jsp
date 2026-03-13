<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Liste des tuteurs</title>
        <link href="css/liste.css" type="text/css" rel="stylesheet" />
    <div class="logo-container"> <a href="#"> 
            <img src="img/LogoECN.png" alt="Logo Centrale Nantes" class="fixed-logo"> </a>
    </div>
</head>
<body>

    <h2>Liste des tuteurs</h2>

    <form action="ajouttuteur.do" method="post">
        <button type="submit" name="ajouter un tuteur" class="btn btn-primary btn-lg">
            Ajouter un tuteur
        </button>
    </form>
    <table>
        <thead>
            <tr>
                <th>Nom</th>
                <th>Prenom</th>
                <th>Nombre de sujet souhaite</th>
                <th>Année</th>
                <th>Sujets souhaités</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${tuteurList}">
                <tr>
                    <td scope="col">${item.nom}</td>
                    <td>${item.prenom}</td>
                    <td>${item.nbSujetSouhaite}</td>
                    <td class="text-center">${item.annee.annee}</td>
                    <td>
                        <c:forEach var="sujet" items="${item.sujetCollection}">
                            ${sujet.numeroDuSujet}
                        </c:forEach>
                    </td>
                    <td class="text-center">
                        <form action="editTuteur" method="POST">
                            <input type="hidden" name="id" value="${item.id}" />
                            <button name="edit" class="btn" formaction="edittuteur.do"><img src="img/edit.png" alt="edit" class="icon" /></button>
                            <button name="delete" class="btn" formaction="deletetuteur.do"><img src="img/delete.png" alt="delete" class="icon" /></button>
                            <button name="reutiliser tuteur" class="btn" formaction="reutilisertuteur.do">Réutiliser le tuteur</button>
                        </form>
                    </td>

                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="col-md-4 bg-primary m-2" style="">
        <div class="col-md-12">
            <form action="main.do" method="POST" class="m-2">
                <button type="submit" name="retour au menu" class="btn btn-primary btn-lg">
                    Retourner au menu
                </button>
            </form>   
        </div>
    </div>
</body>
</html>

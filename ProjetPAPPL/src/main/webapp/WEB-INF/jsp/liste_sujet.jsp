<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Liste des sujets</title>
        <link href="css/liste.css" type="text/css" rel="stylesheet" />
    <div class="logo-container"> <a href="#"> 
            <img src="img/LogoECN.png" alt="Logo Centrale Nantes" class="fixed-logo"> </a>
    </div>
</head>
<body>

    <h2>Liste des sujets</h2>

    <table>
        <thead>
            <tr>
                <th>Numéro de sujet</th>
                <th>Intitulé du sujet</th>
                <th>Contexte</th>
                <th>Travail à réaliser</th>
                <th>Livrable</th>
                <th>Nom de l'entreprise</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="item" items="${sujetList}">
                <c:set var="sujet" value="${item[0]}" />
                <c:set var="desc" value="${item[1]}" />
                <tr>
                    <td>${sujet.numeroDuSujet}</td>
                    <td>${sujet.intitule}</td>
                    <td>${desc.contexte}</td>
                    <td>${desc.travailRealiser}</td>
                    <td>${desc.livrable}</td>
                    <td >${desc.nomEntreprise.nom}</td>
                    <td>
                        <form action="editSujet" method="POST">
                            <input type="hidden" name="id" value="${sujet.numero}" />
                            <button name="edit" class="btn" formaction="editsujet.do"><img src="img/edit.png" alt="edit" class="icon" /></button>
                            <button name="delete" class="btn" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce sujet ?');" formaction="deletesujet.do">
                                <img src="img/delete.png" alt="delete" class="icon" /> // faire le onclick avec bootstrap
                            </button>
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


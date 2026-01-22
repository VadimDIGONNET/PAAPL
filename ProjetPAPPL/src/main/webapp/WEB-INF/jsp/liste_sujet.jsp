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
            <tr>
                <td>${item.sujet.numeroDuSujet}</td>
                <td>${item.sujet.intitule}</td>
                <td>${item.contexte}</td>
                <td>${item.travailRealiser}</td>
                <td>${item.livrable}</td>
                <td >${item.nomEntreprise.nom}</td>
                <td>
                    
                    
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


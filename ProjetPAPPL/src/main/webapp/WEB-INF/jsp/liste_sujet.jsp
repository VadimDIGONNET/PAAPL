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
            <th>Contexte</th>
            <th>Travail à réaliser</th>
            <th>Livrable</th>
            <th>Nom de l'entreprise</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="sujet : ${sujets}">
            <td th:text="${sujet.contexte}"></td>
            <td th:text="${sujet.travailARealiser}"></td>
            <td th:text="${sujet.livrable}"></td>
            <td th:text="${sujet.nomEntreprise}"></td>
        </tr>
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


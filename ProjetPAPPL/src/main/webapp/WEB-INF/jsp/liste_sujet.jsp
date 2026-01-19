<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Liste des sujets</title>
    <link href="css/liste.css" type="text/css" rel="stylesheet" />
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

</body>
</html>


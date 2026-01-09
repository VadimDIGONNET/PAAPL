<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Liste des entreprises</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #000;
            padding: 8px;
            vertical-align: top;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>

<h2>Liste des entreprises</h2>

<table>
    <thead>
        <tr>
            <th>Nom</th>
            <th>Adresse</th>
            <th>Code postal</th>
            <th>Téléphone</th>
            <th>Email</th>
            <th>Description</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="myList : ${entreprises}">
            <td th:text="${myList.nom}"></td>
            <td th:text="${myList.adresse}"></td>
            <td th:text="${myList.codePostal}"></td>
            <td th:text="${myList.numeroTelephone}"></td>
            <td th:text="${myList.email}"></td>
            <td th:text="${myLst.description}"></td>
        </tr>
    </tbody>
</table>

</body>
</html>

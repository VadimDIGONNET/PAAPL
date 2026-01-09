<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
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
            <th>site web</th>
            <th>Description</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${entrepriseList}">
                                        <tr>
                                            <td scope="col">${item.nom}</td>
                                            <td>${item.adresse}</td>
                                            <td>${item.codePostal}</td>
                                            <td>${item.tel}</td>
                                            <td>${item.siteWeb}</td>
                                            <td>${item.description}</td>
                                            <td class="text-center"></td>
                                        </tr>
                                        </c:forEach>
    </tbody>
</table>

</body>
</html>

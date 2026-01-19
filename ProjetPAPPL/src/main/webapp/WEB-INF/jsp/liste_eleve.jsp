<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Liste des eleves</title>
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
        .icon {
            width: 1.5rem; /* S'adapte à la taille de police globale */
          }
    </style>
</head>
<body>

<h2>Liste des eleves</h2>

<table>
    <thead>
        <tr>
            <th>Nom</th>
            <th>Prenom</th>
            <th>nationalite</th>
            <th>email</th>
            <th>td</th>
            <th>numero_sujet</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${eleveList}">
                                        <tr>
                                            <td scope="col">${item.nom}</td>
                                            <td>${item.prenom}</td>
                                            <td>${item.nationalite}</td>
                                            <td>${item.email}</td>
                                            <td>${item.td}</td>
                                            <td>${item.numeroSujet}</td>
                                            <td class="text-center"></td>
                                            <td class="text-center">
    <form method="POST">
        <input type="hidden" name="id" value="${item.id}" />
        
        <button name="edit" class="btn" formaction="editeleve.do">
            <img src="img/edit.png" alt="edit" class="icon" />
        </button>
        
        <button name="delete" class="btn" formaction="deleteeleve.do">
            <img src="img/delete.png" alt="delete" class="icon" />
        </button>
    </form>
</td>
                                          
                                        </tr>
                                        </c:forEach>
    </tbody>
</table>

</body>
</html>

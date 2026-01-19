<%-- 
    Document   : correspondant
    Created on : 19 janv. 2026, 14:45:17
    Author     : dodi
--%>

<%-- 
    Document   : correspondant
    Created on : 19 janv. 2026, 11:35:46
    Author     : dodi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Liste des Correspondants (Partenaires)</title>
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
<h2>Liste des Correspondants (Partenaires)</h2>

<form action="ajoutcorrespondant.do" method="post" style="margin-bottom: 15px;">
    <button type="submit">Ajouter un correspondant</button>
</form>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Prénom</th>
            <th>Téléphone</th>
            <th>Email</th>
            <th>Entreprise</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${correspondantList}">
            <tr>
                <td scope="col">${item.id}</td>
                <td>${item.nom}</td>
                <td>${item.prenom}</td>
                <td>${item.tel}</td>
                <td>${item.email}</td>
                <td>
                    <c:choose>
                        <c:when test="${item.entreprise != null}">
                            ${item.entreprise.nom}
                        </c:when>
                        <c:otherwise>
                            -
                        </c:otherwise>
                    </c:choose>
                </td>
                <td class="text-center"></td>
                <td class="text-center">
                    <form action="editCorrespondant" method="POST">
                        <input type="hidden" name="id" value="${item.id}" />
                        <button name="edit" class="btn" formaction="editcorrespondant.do"><img src="img/edit.png" alt="edit" class="icon" /></button>
                        <button name="delete" class="btn" formaction="deletecorrespondant.do"><img src="img/delete.png" alt="delete" class="icon" /></button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<br>
<form action="login.do" method="post">
    <button type="submit" style="background-color: #6c757d;">Retour au menu principal</button>
</form>


</body>
</html>
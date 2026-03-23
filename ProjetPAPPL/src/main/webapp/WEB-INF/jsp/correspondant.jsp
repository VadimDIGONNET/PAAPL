
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Liste des correspondants</title>
        <link rel="stylesheet"
              href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
        <script type="text/javascript" src="js/jquery-3.7.1.js"></script>

        <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>

        <link href="css/liste.css" type="text/css" rel="stylesheet" />
    <div class="logo-container"> <a href="#"> 
            <img src="img/LogoECN.png" alt="Logo Centrale Nantes" class="fixed-logo"> </a>
    </div>
</head>
<body>
    <h2>Liste des Correspondants </h2>

    <form action="ajoutcorrespondant.do" method="post">
        <button type="submit" name="ajouter un correspondant" class="btn btn-primary btn-lg">
            Ajouter un correspondant
        </button>
    </form>

    <table id="tableCorrespondants">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Téléphone</th>
                <th>Email</th>
                <th>Entreprise</th>
                <th></th>
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
    <form action="main.do" method="post">
        <button type="submit" name="retour au menu" class="btn btn-primary btn-lg">
            Retourner au menu
        </button>
    </form>
    <script>
        $(document).ready(function () {
            $('#tableCorrespondants').DataTable({
                language: {
                    url: "//cdn.datatables.net/plug-ins/1.13.6/i18n/fr-FR.json"
                }
            });
        });
    </script>
</body>
</html>
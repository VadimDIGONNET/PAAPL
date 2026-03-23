<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Liste des partenaires</title>
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

    <h2>Liste des partenaires</h2>

    <form action="ajoutentreprise.do" method="post">
        <button type="submit" name="ajouter une entreprise" class="btn btn-primary btn-lg">
            Ajouter une entreprise
        </button>
    </form>
    <table id="tablePartenaires">
        <thead>
            <tr>
                <th>Nom</th>
                <th>Adresse</th>
                <th>Code postal</th>
                <th>Téléphone</th>
                <th>site web</th>
                <th>Description</th>
                <th></th>
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
                    <td class="text-center">
                        <form action="editEntreprise" method="POST">
                            <input type="hidden" name="nom" value="${item.nom}" />
                            <button name="edit" class="btn" formaction="editentreprise.do"><img src="img/edit.png" alt="edit" class="icon" /></button>
                            <button name="delete" class="btn" formaction="deleteentreprise.do" onclick= " return confirm('ATTENTION : Supprimer cette entreprise supprimera DEFINITIVEMENT tous les sujets et correspondants liés.\n\nVoulez-vous vraiment continuer ?');"><img src="img/delete.png" alt="delete" class="icon" /></button>
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
    <script>
        $(document).ready(function () {
            $('#tablePartenaires').DataTable({
                language: {
                    url: "//cdn.datatables.net/plug-ins/1.13.6/i18n/fr-FR.json"
                }
            });
        });
    </script>
</body>
</html>

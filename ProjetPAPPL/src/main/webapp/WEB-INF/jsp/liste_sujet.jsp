<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Liste des sujets</title>
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

    <h2>Liste des sujets</h2>

    <form action="createsujet.do" method="post">
        <button type="submit" name="ajouter un sujet" class="btn btn-primary btn-lg">
            Ajouter un sujet
        </button>
    </form>
    <table id="tableSujets">
        <thead>
            <tr>
                <th>Numéro de sujet</th>
                <th>Intitulé du sujet</th>
                <th>Contexte</th>
                <th>Travail à réaliser</th>
                <th>Livrable</th>
                <th>Nom de l'entreprise</th>
                <th>Année</th>
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
                    <td>
                        ${desc.contexte}
                    </td>
                    <td>${desc.travailRealiser}</td>
                    <td>${desc.livrable}</td>
                    <td >${desc.nomEntreprise.nom}</td>
                    <td>${sujet.annee.annee}</td>

                    <td>
                        <form action="editSujet" method="POST">
                            <input type="hidden" name="id" value="${sujet.numero}" />
                            <button name="edit" class="btn" formaction="editsujet.do"><img src="img/edit.png" alt="edit" class="icon" /></button>
                            <button name="delete" class="btn" onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce sujet ?');" formaction="deletesujet.do">
                                <img src="img/delete.png" alt="delete" class="icon" />
                            </button>
                            <button name="export" class="btn" formaction="ExportProjet.do" formmethod="POST">
                                <input type="hidden" name="id" value="${sujet.numero}" />
                                Exporter Word
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
            <form action="Exportsujetgeneral.do" method="POST">
                <button type="submit" class="btn btn-primary btn-lg">
                    Exporter tous les sujets (Word)
                </button>
            </form>
            <form action="Exportmodelevide.do" method="POST">
                <button type="submit" class="btn btn-primary btn-lg">
                    Exporter feuille modele
                </button>
            </form>
        </div>
    </div>
    <script>
        $(document).ready(function () {
            $('#tableSujets').DataTable({
                "language": {
                    "url": "//cdn.datatables.net/plug-ins/1.13.6/i18n/fr-FR.json"
                }
            });
        });
    </script>
</body>
</html>


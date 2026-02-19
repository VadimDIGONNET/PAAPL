<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Liste des eleves</title>
    <link href="css/liste.css" type="text/css" rel="stylesheet" />
    <div class="logo-container"> <a href="#"> 
        <img src="img/LogoECN.png" alt="Logo Centrale Nantes" class="fixed-logo"> </a>
    </div>
</head>
<body>

<h2>Liste des eleves</h2>

<form action="ajouteleve.do" method="post">
   <button type="submit" name="ajouter un élève" class="btn btn-primary btn-lg">
                    Ajouter un élève
    </button>
</form>

<table>
    <thead>
        <tr>
            <th>Nom</th>
            <th>Prenom</th>
            <th>nationalite</th>
            <th>email</th>
            <th>td</th>
            <th>numero_sujet</th>
            <th>Année</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${eleveList}">
                                        <tr>
                                            <td>${item.nom}</td>
                                            <td>${item.prenom}</td>
                                            <td>${item.nationalite}</td>
                                            <td>${item.email}</td>
                                            <td>${item.td}</td>
                                            <td>${item.numeroSujet.numeroDuSujet}</td>
                                            <td>${item.annee.annee}</td>
                                            <td class="text-center">
    <form action="editEleve"method="POST">
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

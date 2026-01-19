<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<h2>Liste des entreprises</h2>

<table>
    <thead>
        <tr>
            <th>Nom</th>
            <th>Prenom</th>
            <th>Nombre sujet souhaite</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="item" items="${tuteurList}">
                                        <tr>
                                            <td scope="col">${item.nom}</td>
                                            <td>${item.prenom}</td>
                                            <td>${item.nbSujetSouhaite}</td>
                                            <td class="text-center"></td>
                                            <td class="text-center">
                                                <form action="editTuteur" method="POST">
                                                    <input type="hidden" name="nom" value="${item.nom}" />
                                                    <button name="edit" class="btn" formaction="editTuteur.do"><img src="img/edit.png" alt="edit" class="icon" /></button>
                                                    <button name="delete" class="btn" formaction="deletetuteur.do"><img src="img/delete.png" alt="delete" class="icon" /></button>
                                                </form>
                                            </td>
                                          
                                        </tr>
                                        </c:forEach>
    </tbody>
</table>

</body>
</html>

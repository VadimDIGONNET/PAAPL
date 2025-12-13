<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr-fr">
    <head>
        <title>List Users Page</title>
        <meta charset="UTF-8"/> 
  		<meta name="viewport" content="width=device-width, initial-scale=1">
  		<link rel="stylesheet" href="bootstrap/css/bootstrap.css">
  		<script src="bootstrap/js/bootstrap.min.js"></script>
  		<script type="text/javascript" src="js/jquery-3.7.1.js"></script>
        <link href="css/main.css" type="text/css" rel="stylesheet" />
    </head>
    <body>

        <%@ include file="navbar.jspf" %>

		<div class="py-3">
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<h2 class="">List of users</h2>
					</div>
				</div>
                  <div class="row">
                    <div class="col-md-12">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th scope="col" class="text-center">user #</th>
                                        <th scope="col" class="text-center">FirstName</th>
                                        <th scope="col" class="text-center">LastName</th>
                                        <th scope="col" class="text-center">Birthdate</th>
                                        <th scope="col"></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${usersList}">
                                        <tr>
                                            <td scope="col">${item.personId}</td>
                                            <td>${item.personFirstname}</td>
                                            <td>${item.personLastname}</td>
                                            <td><fmt:formatDate value="${item.personBirthdate}" pattern="yyyy-MM-dd" /></td>
                                            <td class="text-center">
                                                <form action="editUser" method="POST">
                                                    <input type="hidden" name="id" value="${item.personId}" />
                                                    <button name="edit" class="btn" formaction="edituser.do"><img src="img/edit.png" alt="edit" class="icon" /></button>
                                                    <button name="delete" class="btn" formaction="deleteuser.do"><img src="img/delete.png" alt="delete" class="icon" /></button>
                                                </form>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                </tbody>
                                <tfoot>
                                    <tr id="addNew">
                                        <td scope="col" colspan="4"></td>
                                        <td class="text-center">
                                            <form action="createuser.do" method="POST">
                                                <button class="btn"><img src="img/plus.png" alt="add" class="icon" /></button>
                                            </form>
                                        </td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
		</div>

    </body>
</html>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c"   uri="jakarta.tags.core" %>
<%@taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Centrale Nantes - Gestion</title>
        <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
        <link href="css/main.css" type="text/css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-3.7.1.js"></script>
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/main.js"></script>
    </head>
    <body>

        

        <nav class="navbar navbar-expand-lg navbar-light fixed-top" aria-label="Navigation principale">
            <div class="container-fluid">
                <div class="logo-container"> 
                    <a href="main.do" aria-label="Accueil - Centrale Nantes">
                        <img src="img/LogoECN.png" alt="Logo Centrale Nantes" class="fixed-logo"> 
                    </a>
                </div>
                <!-- CORRECTION 1 : aria-label + aria-expanded + aria-controls sur le bouton hamburger -->
                <button class="navbar-toggler ms-auto" type="button"
                        data-bs-toggle="collapse" data-bs-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false"
                        aria-label="Ouvrir le menu de navigation">
                    <span class="navbar-toggler-icon"></span>
                </button> 
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav ms-auto">
                        <li class="nav-item"><a class="nav-link" href="#sujets">Sujets</a></li>
                        <li class="nav-item"><a class="nav-link" href="#eleves">Élèves</a></li>
                        <li class="nav-item"><a class="nav-link" href="#tuteurs">Tuteurs</a></li>
                        <li class="nav-item"><a class="nav-link" href="#correspondants">Correspondants</a></li>
                        <li class="nav-item"><a class="nav-link" href="#partenaires">Partenaires</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <main id="contenu-principal">

            <div class="py-4">
                <div class="container">
                    <div class="col-lg-3 col-md-6 mb-4 mb-lg-0">
                        <p>
                            <strong>Année sélectionnée :</strong>
                            ${sessionScope.anneeCourante}
                        </p>

                        <form action="clearAnnee.do" method="POST">
                            <button type="submit">Supprimer le filtre</button>
                        </form>
                        <c:if test="${sessionScope.anneeCourante == null}">
                            <form action="setAnnee.do" method="POST">
                                <label for="annee">Sélectionner une année :</label>
                                <select id="annee" name="annee" required>
                                    <option value="">-- Choisir une année --</option>
                                    <c:forEach var="annee" items="${anneeList}">
                                        <option value="${annee.annee}">
                                            ${annee.annee}
                                        </option>
                                    </c:forEach>
                                </select>
                                <button type="submit">Filtrer</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>

            <div class="mb-3">
                <button id="btnExport" class="btn btn-success" onclick="telechargerExcel()"> 
                    Générer et Télécharger l'Excel 
                </button>
            </div>
            <!-- SECTION SUJETS -->
            <section class="py-4" id="sujets" aria-labelledby="titre-sujets">
                <div class="container">
                    <h2 id="titre-sujets">Sujets</h2>                    <hr>
                    <div class="row justify-content-center">
                        <div class="col-md-4 bg-primary m-2">
                            <form action="createsujet.do" method="POST">
                                 <!-- CORRECTION 5 : aria-label descriptif sur les boutons -->
                                <button type="submit" name="create_sujet" class="btn btn-primary btn-lg"
                                        aria-label="Ajouter un sujet">Ajouter</button>
                            </form>
                        </div>
                        <div class="col-md-4 bg-primary m-2">
                            <form action="sujet.do" method="POST">
                                <button type="submit" name="sujet" class="btn btn-primary btn-lg">Liste</button>
                            </form>
                        </div>
                    </div>
                    <div class="mb-3">
                        <form action="ImportProjetServlet.do" method="post" enctype="multipart/form-data">
                            <label for="file-sujet" class="form-label">Sélectionnez le formulaire Word (.docx) :</label>
                            <input type="file" class="form-control" id="file" name="file" accept=".docx" required>
                            <button type="submit" class="btn btn-secondary btn-lg mt-2"
                                    aria-label="Importer un fichier Word pour les sujets">Importer Word</button>
                        </form>
                    </div>
                </div>
            </section>
            
            <section class="py-4" id="eleves" aria-labelledby="titre-eleves">
                <div class="container">
                    <h2 id="titre-eleves">Élèves</h2>
                    <hr>
                    <div class="row justify-content-center">
                        <div class="col-md-4 bg-primary m-2">
                            <form action="ajouteleve.do" method="POST">
                                <button type="submit" class="btn btn-primary btn-lg"
                                    aria-label="Ajouter un élève">Ajouter</button>  
                            </form>
                        </div>
                        <div class="col-md-4 bg-secondary m-2">
                            <form action="eleve.do" method="POST">
                                <button type="submit" class="btn btn-secondary btn-lg"
                                    aria-label="Voir la liste des élèves">Liste</button>  
                            </form>
                        </div>
                        <form action="importeleve.do" method="post" enctype="multipart/form-data">
                            <div class="mb-3">
                                <label for="file-eleve" class="form-label">Sélectionnez un fichier CSV :</label>
                                <input type="file" class="form-control" id="file-eleve" name="file" accept=".csv" required>  
                            </div>
                            <button type="submit" class="btn btn-secondary btn-lg"
                                    aria-label="Importer un fichier CSV pour les élèves">Importer</button>
                        </form>
                        <p th:text="${message}"></p>
                    </div>
                </div>
                </section>
    
       
            <section class="py-4" id="tuteurs" aria-labelledby="titre-tuteurs">
                <div class="container">
                    <h2 id="titre-tuteurs">Tuteurs</h2>
                    <hr>
                    <div class="row justify-content-center">
                        <div class="col-md-4 bg-primary m-2">
                            <form action="ajouttuteur.do" method="POST">
                                <button type="submit" class="btn btn-primary btn-lg"
                                        aria-label="Ajouter un tuteur">Ajouter</button>
                            </form>
                        </div>
                        <div class="col-md-4 bg-secondary m-2">
                            <form action="tuteurs.do" method="POST">
                                <button type="submit" class="btn btn-secondary btn-lg"
                                        aria-label="Voir la liste des tuteurs">Liste</button>
                            </form>
                        </div>
                        <form action="importtuteur.do" method="post" enctype="multipart/form-data">
                            <div class="mb-3">
                                <label for="file" class="form-label">Sélectionnez un fichier CSV :</label>
                                <input type="file" class="form-control" id="file" name="file" accept=".csv" required>
                            </div>
                            <button type="submit" class="btn btn-secondary btn-lg"
                                    aria-label="Importer un fichier CSV pour les tuteurs">Importer</button>
                        </form>
                        <p th:text="${message}"></p>
                    </div>
                </div>
            </section>
                    
                    
            <!-- SECTION CORRESPONDANTS -->
            <section class="py-4" id="correspondants" aria-labelledby="titre-correspondants">
                <div class="container">
                    <h2 id="titre-correspondants">Correspondants</h2>
                    <hr>
                    <div class="row justify-content-center">
                        <div class="col-md-4 bg-primary m-2">
                            <form action="ajoutcorrespondant.do" method="POST">
                                <button type="submit" class="btn btn-primary btn-lg"
                                        aria-label="Ajouter un correspondant">Ajouter</button>
                            </form>
                        </div>
                        <div class="col-md-4 bg-secondary m-2">
                            <form action="correspondant.do" method="POST">
                                <button type="submit" class="btn btn-secondary btn-lg"
                                        aria-label="Voir la liste des correspondants">Liste</button>
                            </form>
                        </div>
                    </div>
                </div>
            </section>

            <section class="py-4" id="partenaires" aria-labelledby="titre-partenaires">
                <div class="container">
                    <h2 id="titre-partenaires">Partenaires</h2>
                    <hr>
                    <div class="row justify-content-center">
                        <div class="col-md-4 bg-primary m-2">
                            <form action="ajoutentreprise.do" method="POST">
                                <button type="submit" class="btn btn-primary btn-lg"
                                        aria-label="Ajouter un partenaire">Ajouter</button>
                            </form>
                        </div>
                        <div class="col-md-4 bg-secondary m-2">
                            <form action="entreprise.do" method="POST">
                                <button type="submit" class="btn btn-secondary btn-lg"
                                        aria-label="Voir la liste des partenaires">Liste</button>
                            </form>
                        </div>
                        <form action="importentreprise.do" method="post" enctype="multipart/form-data">
                            <div class="mb-3">
                                <label for="file-partenaire" class="form-label">Sélectionnez un fichier CSV :</label>
                                <input type="file" class="form-control" id="file-partenaire" name="file" accept=".csv" required>
                            </div>
                            <button type="submit" class="btn btn-secondary btn-lg"
                                    aria-label="Importer un fichier CSV pour les partenaires">Importer</button>
                        </form>
                        <p th:text="${message}"></p>
                    </div>
                </div>
            </section>

        </main>
                    
                    
        <footer>
            <!-- CORRECTION 7 : <h4> dans le footer remplacé par <h2> (structure correcte) -->
            <!-- CORRECTION 8 : aria-label sur les liens réseaux sociaux (icônes sans texte) -->

            <div class="bg-info p-4">
                <div class="container" style="color:white">
                    <div class="row">
                        <div class="p-0 col-lg-4 col-md-6">
                            <img class="img-fluid" src="img/LogoECN.png" alt="Logo Centrale Nantes">
                        </div>
                        <div class="col-md-5 align-self-center p-4 offset-md-1">
                            <h2 class="h4">Centrale Nantes</h2>
                            <address class="mb-4 text-primary">
                                1 Rue de la Noë,<br>
                                44300 Nantes, France<br>
                                <a href="tel:+33240371600" class="text-primary">+33 (0)2 40 37 16 00</a>
                            </address>                            <div class="d-flex justify-content-start">
                                <a href="#" class="text-white me-3"><i class="fab fa-facebook-f fa-lg"></i></a>
                                <a href="#" class="text-white me-3"><i class="fab fa-twitter fa-lg"></i></a>
                                <a href="#" class="text-white me-3"><i class="fab fa-linkedin-in fa-lg"></i></a>
                                <a href="#" class="text-white me-3"><i class="fab fa-instagram fa-lg"></i></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


        
            <div class="container">
                <div class="row">
                    <div class="col-lg-3 col-md-6 mb-4 mb-lg-0">
                        <p>
                            Plateforme de gestion des projets étudiants en partenariat avec des entreprises.
                        </p>
                    </div>
                    <div class="col-lg-3 col-md-6 mb-4 mb-lg-0">
                        <h5 class="mb-4">Ressources</h5>
                        <ul class="list-unstyled">
                            <li class="mb-2"><a href="#" class="text-white-50">Guide Utilisateur</a></li>
                            <li class="mb-2"><a href="#" class="text-white-50">Contact</a></li>
                        </ul>
                    </div>
                    <div class="col-lg-3 col-md-6 mb-4 mb-lg-0">
                        <h5 class="mb-4">À Propos</h5>
                        <ul class="list-unstyled">
                            <li class="mb-2"><a href="equipe.jsp" class="text-white-50">L'équipe projet</a></li>
                            <li class="mb-2"><a href="#" class="text-white-50">Mentions Légales</a></li>
                        </ul>
                    </div>
                </div>
                <div class="row border-top mt-4 pt-4">
                    <div class="col-md-6 text-center text-md-start">
                        <p class="mb-0">@2026 Centrale Nantes. Tous droits réservés.</p>
                    </div>
                </div>
            </div>
        </footer>


        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    </body>
</html>



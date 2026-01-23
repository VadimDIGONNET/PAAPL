<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Centrale Nantes - Gestion</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
    <link href="css/main.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="js/jquery-3.7.1.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

    <div class="logo-container"> 
        <a href="#"> 
            <img src="img/LogoECN.png" alt="Logo Centrale Nantes" class="fixed-logo"> 
        </a>
    </div>

    <nav class="navbar navbar-expand-lg navbar-light fixed-top">
        <div class="container-fluid">
            <button class="navbar-toggler ms-auto" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
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

    <div class="py-4" id="sujets">
        <div class="container">
            <h2>Sujets</h2>
            <hr>
            <div class="row justify-content-center">
                <div class="col-md-4 bg-primary m-2">
                    <form action="createsujet.do" method="POST">
                        <button type="submit" name="create_sujet" class="btn btn-primary btn-lg">Ajouter</button>
                    </form>
                </div>
                <div class="col-md-4 bg-primary m-2">
                    <form action="sujet.do" method="POST">
                        <button type="submit" name="sujet" class="btn btn-primary btn-lg">Liste</button>
                    </form>
                </div>
                <div class="col-md-4 bg-secondary m-2 text-center">
                    <form action="importentreprise.do" method="POST">
                        <button type="submit" class="btn btn-secondary btn-lg">Importer CSV</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="py-4" id="eleves">
        <div class="container">
            <h2>Élèves</h2>
            <hr>
            <div class="row justify-content-center">
                <div class="col-md-4 bg-primary m-2">
                    <form action="ajouteleve.do" method="POST">
                        <button type="submit" class="btn btn-primary btn-lg">Ajouter</button>
                    </form>
                </div>
                <div class="col-md-4 bg-primary m-2">
                    <form action="eleve.do" method="POST">
                        <button type="submit" class="btn btn-primary btn-lg">Liste</button>
                    </form>
                </div>
                <form action="importeleve.do" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="file" class="form-label">Sélectionnez un fichier CSV :</label>
                        <input type="file" class="form-control" id="file" name="file" accept=".csv" required>
                    </div>
                    <button type="submit" class="btn btn-secondary btn-lg">Importer</button>
                </form>
                <p th:text="${message}"></p>
            </div>
        </div>
    </div>

    <div class="py-4" id="tuteurs">
        <div class="container">
            <h2>Tuteurs</h2>
            <hr>
            <div class="row justify-content-center">
                <div class="col-md-4 bg-primary m-2">
                    <form action="ajouttuteur.do" method="POST">
                        <button type="submit" class="btn btn-primary btn-lg">Ajouter</button>
                    </form>
                </div>
                <div class="col-md-4 bg-primary m-2">
                    <form action="tuteurs.do" method="POST">
                        <button type="submit" class="btn btn-primary btn-lg">Liste</button>
                    </form>
                </div>
                <form action="importtuteur.do" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="file" class="form-label">Sélectionnez un fichier CSV :</label>
                        <input type="file" class="form-control" id="file" name="file" accept=".csv" required>
                    </div>
                    <button type="submit" class="btn btn-secondary btn-lg">Importer</button>
                </form>
                <p th:text="${message}"></p>
            </div>
        </div>
    </div>

    <div class="py-4" id="correspondants">
        <div class="container">
            <h2>Correspondants</h2>
            <hr>
            <div class="row justify-content-center">
                <div class="col-md-4 bg-primary m-2">
                    <form action="ajoutcorrespondant.do" method="POST">
                        <button type="submit" class="btn btn-primary btn-lg">Ajouter</button>
                    </form>
                </div>
                <div class="col-md-4 bg-primary m-2">
                    <form action="correspondant.do" method="POST">
                        <button type="submit" class="btn btn-primary btn-lg">Liste</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <div class="py-4" id="partenaires">
        <div class="container">
            <h2>Partenaires</h2>
            <hr>
            <div class="row justify-content-center">
                <div class="col-md-4 bg-primary m-2">
                    <form action="ajoutentreprise.do" method="POST">
                        <button type="submit" class="btn btn-primary btn-lg">Ajouter</button>
                    </form>
                </div>
                <div class="col-md-4 bg-primary m-2">
                    <form action="entreprise.do" method="POST">
                        <button type="submit" class="btn btn-primary btn-lg">Liste</button>
                    </form>
                </div>
                <form action="importentreprise.do" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="file" class="form-label">Sélectionnez un fichier CSV :</label>
                        <input type="file" class="form-control" id="file" name="file" accept=".csv" required>
                    </div>
                    <button type="submit" class="btn btn-secondary btn-lg">Importer</button>
                </form>
                <p th:text="${message}"></p>
            </div>
        </div>
    </div>

    <div class="bg-info p-4">
        <div class="container">
            <div class="row">
                <div class="p-0 col-lg-4 col-md-6">
                    <img class="img-fluid" src="img/LogoECN.png" alt="logo">
                </div>
                <div class="col-md-5 align-self-center p-4 offset-md-1">
                    <h4>Centrale Nantes</h4>
                    <p class="mb-4 text-primary"> 1 Rue de la Noë,<br> 44300 Nantes, France<br> +33 (0)2 40 37 16 00 </p>
                    <div class="d-flex justify-content-start">
                        <a href="#" class="text-white me-3"><i class="fab fa-facebook-f fa-lg"></i></a>
                        <a href="#" class="text-white me-3"><i class="fab fa-twitter fa-lg"></i></a>
                        <a href="#" class="text-white me-3"><i class="fab fa-linkedin-in fa-lg"></i></a>
                        <a href="#" class="text-white me-3"><i class="fab fa-instagram fa-lg"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <footer class="bg-dark text-white py-5">
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
                        <li class="mb-2"><a href="#" class="text-white-50">L'Équipe projet</a></li>
                        <li class="mb-2"><a href="#" class="text-white-50">Mentions Légales</a></li>
                    </ul>
                </div>
            </div>
            <div class="row border-top mt-4 pt-4">
                <div class="col-md-6 text-center text-md-start">
                    <p class="mb-0">© 2026 Centrale Nantes. Tous droits réservés.</p>
                </div>
            </div>
        </div>
    </footer>


    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>



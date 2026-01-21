<!DOCTYPE html>
<html>

<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- PAGE settings -->
  <link rel="icon" href="https://templates.pingendo.com/assets/Pingendo_favicon.ico">
  <title>Styleguide</title>
  <meta name="author" content="Pingendo">
  <!-- CSS dependencies -->
  <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
  <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
  <script type="text/javascript" src="js/jquery-3.7.1.js"></script>
  <script src="bootstrap/js/bootstrap.min.js"></script>
  <link href="css/main.css" type="text/css" rel="stylesheet" />
  <div style="text-align: center; margin: 20px 0;">  
</head>

<body >
    <div class="logo-container"> <a href="#"> 
          <img src="img/LogoECN.png" alt="Logo Centrale Nantes" class="fixed-logo"> </a>
    </div>
    <!-- Barre de navigation fixe (menu hamburger) -->
    <nav class="navbar navbar-expand-lg navbar-light fixed-top">
      <div class="container-fluid">
        <button class="navbar-toggler ms-auto" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button> 
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav ms-auto">
            <li class="nav-item">
              <a class="nav-link" href="#sujets">Sujets</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#eleves">Élèves</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#tuteurs">Tuteurs</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#correspondants">Correspondants</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#partenaires">Partenaires</a>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  
  <div class="py-4" id="sujets">
    <div class="container">
      <div class="row">
        <div class="col-12" id="sujet">
          <h2>Sujets</h2>
          <hr>
          <div class="row justify-content-center">
            <div class="col-md-4 bg-primary m-2">
            <div class="col-md-12">
              <form action="createsujet.do" method="POST" class="m-2">
                <button type="submit" name="create_sujet" class="btn btn-primary btn-lg">
                  Ajouter
                </button>
              </form>
            </div>
          </div>
            <div class="col-md-4 bg-primary m-2" style="">
              <div class="col-md-12">
                  <form action="sujet.do" method="POST" class="m-2">
                    <button type="submit" name="sujet" class="btn btn-primary btn-lg">
                    Liste
                    </button>
                    </form>
              </div>
            </div>
            <div class="col-md-8 bg-dark m-2" style="">
            </div>
              
            <div class="col-md-4 bg-secondary m-2 text-center">
                <div class="col-md-12">
                    <form action="importentreprise.do" method="POST" class="m-2">
                      <button type="submit" class="btn btn-secondary btn-lg">
                        Importer CSV
                      </button>
                    </form>
                </div>
            </div>
              
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="py-4" id="eleves">
    <div class="container">
      <div class="row">
        <div class="col-12" >
          <h2>Élèves</h2>
          <hr>
          <div class="row justify-content-center">
            <div class="col-md-4 bg-primary m-2" style="">
              <div class="col-md-12">
                  <form action="ajouteleve.do" method="POST" class="m-2">
                    <button type="submit" name="create entreprise" class="btn btn-primary btn-lg">
                        Ajouter
                    </button>
                </form>   
              </div>
            </div>
            <div class="col-md-4 bg-primary m-2" style="">
              <div class="col-md-12">
                  <form action="eleve.do" method="POST" class="m-2">
                    <button type="submit" name="affiche entreprise" class="btn btn-primary btn-lg">
                        Liste
                    </button>
                </form>
              </div>
            </div>
            <div class="col-md-8 bg-dark m-2" style="">
            </div>
              
            <div class="col-md-4 bg-secondary m-2 text-center">
                <div class="col-md-12">
                    <form action="importentreprise.do" method="POST" class="m-2">
                      <button type="submit" class="btn btn-secondary btn-lg">
                        Importer CSV
                      </button>
                    </form>
                </div>
            </div>
            
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="py-4" id="tuteurs">
    <div class="container">
      <div class="row">
        <div class="col-12" >
          <h2>Tuteurs</h2>
          <hr>
          <div class="row justify-content-center">
            <div class="col-md-4 bg-primary m-2" style="">
              <div class="col-md-12">
                  <form action="ajouttuteur.do" method="POST" class="m-2">
                    <button type="submit" name="create tuteur" class="btn btn-primary btn-lg">
                        Ajouter
                    </button>
                </form>   
              </div>
            </div>
            <div class="col-md-4 bg-primary m-2" style="">
              <div class="col-md-12">
                  <form action="tuteurs.do" method="POST" class="m-2">
                    <button type="submit" name="affiche tuteurs" class="btn btn-primary btn-lg">
                        Liste
                    </button>
                </form>
              </div>

            </div>
            <div class="col-md-8 bg-dark m-2" style="">
            </div>
              
            <div class="col-md-4 bg-secondary m-2 text-center">
                <div class="col-md-12">
                    <form action="importentreprise.do" method="POST" class="m-2">
                      <button type="submit" class="btn btn-secondary btn-lg">
                        Importer CSV
                      </button>
                    </form>
                </div>
            </div>
            
          </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="py-4" id="correspondants">
    <div class="container">
      <div class="row">
        <div class="col-12" >
          <h2>Correspondants</h2>
          <hr>
          <div class="row justify-content-center">
            <div class="col-md-4 bg-primary m-2" style="">
              <div class="col-md-12">
                  <form action="ajoutcorrespondant.do" method="post" class="m-2">
                  <button type="submit" class="btn btn-primary btn-lg">Ajouter</button>
                </form>
            </div>
            </div>   
            <div class="col-md-4 bg-primary m-2" style="">
              <div class="col-md-12">
                  <form action="correspondant.do" method="post" class="m-2">
                    <button type="submit" class="btn btn-primary btn-lg"">Liste</button>
                  </form>
            </div>
            </div>
            <div class="col-md-8 bg-dark m-2" style="">
            </div>
              
            <div class="col-md-4 bg-secondary m-2 text-center">
                <div class="col-md-12">
                    <form action="importentreprise.do" method="POST" class="m-2">
                      <button type="submit" class="btn btn-secondary btn-lg">
                        Importer CSV
                      </button>
                    </form>
                </div>
            </div>
              
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="py-4" id="partenaires">
    <div class="container">
      <div class="row">
        <div class="col-12" >
          <h2>Partenaires&nbsp;</h2>
          <hr> 
          <div class="row justify-content-center">
              
            <div class="col-md-4 bg-primary m-2" style="">
              <div class="col-md-12">
                  <form action="ajoutentreprise.do" method="POST" class="m-2">
                    <button type="submit" name="create entreprise" class="btn btn-primary btn-lg">
                        Ajouter
                    </button>
                </form>   
              </div>
            </div>
              
            <div class="col-md-4 bg-primary m-2" style="">
              <div class="col-md-12">
                  <form action="entreprise.do" method="POST" class="m-2">
                    <button type="submit" name="affiche entreprise" class="btn btn-primary btn-lg">
                        Liste
                    </button>
                </form>
              </div>
            </div>
              
            <div class="col-md-8 bg-dark m-2" style="">
            </div>
            
            <div class="col-md-4 bg-secondary m-2 text-center">
                <div class="col-md-12">
                    <form action="importentreprise.do" method="POST" class="m-2">
                      <button type="submit" class="btn btn-secondary btn-lg">
                        Importer CSV
                      </button>
                    </form>
                </div>
            </div>

          </div>
        </div>
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
                  <div class="row text-center">
                      <div class="col-md-2 col-3">
                          <a href="#" target="_blank"><i class="fa fa-facebook text-primary fa-2x"></i></a>
                      </div>
                      <div class="col-md-2 col-3">
                          <a href="#" target="_blank"><i class="fa fa-twitter text-primary fa-2x"></i></a>
                      </div>
                      <div class="col-md-2 col-3">
                          <a href="#" target="_blank"><i class="fa fa-instagram text-primary fa-2x"></i></a>
                      </div>
                      <div class="col-md-2 col-3">
                          <a href="#" target="_blank"><i class="fa fa-linkedin text-primary fa-2x"></i></a>
                      </div>
                  </div>
              </div>
          </div>
      </div>
    </div>

    <div class="py-5 bg-light">
      <div class="container">
          <div class="row pt-5 border-top">
              <div class="col-12 col-md text-center">
                  <i class="fa fa-lg fa-graduation-cap d-block mb-2"></i>
                  <small class="d-block mb-3 text-muted">© 2026 - Projet Centrale Nantes</small>
              </div>
              <div class="col-6 col-md">
                  <h5><b>Ressources</b></h5>
                  <ul class="list-unstyled">
                      <li><a class="text-muted" href="#">Guide Utilisateur</a></li>
                      <li><a class="text-muted" href="#">Contact Support</a></li>
                  </ul>
              </div>
              <div class="col-6 col-md">
                  <h5><b>À Propos</b></h5>
                  <ul class="list-unstyled">
                      <li><a class="text-muted" href="#">Équipe Projet </a></li>
                      <li><a class="text-muted" href="#">Mentions Légales</a></li>
                  </ul>
              </div>
          </div>
      </div>
    </div>
  

  <script src="http://code.jquery.com/jquery-3.2.1.js" integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE=" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous" style=""></script>
 
</body>

</html>



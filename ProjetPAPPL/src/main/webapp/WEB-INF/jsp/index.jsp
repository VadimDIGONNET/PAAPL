<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr-fr">
  <head>
    <title>Library Login</title>
    <meta charset="UTF-8"/> 
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
    <script type="text/javascript" src="js/jquery-3.7.1.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <link href="css/main.css" type="text/css" rel="stylesheet" />
  </head>
  <body>
    <div class="py-5">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <h2 class="">Library Login</h2>
          </div>
        </div>
        <div class="row">
          <div class="col-md-12">
            <form id="c_form-h" action="login.do" method="POST">
              <div class="form-group row">
                <label for="inputlogin" class="col-2 col-form-label">Login</label>
                <div class="col-10">
                  <input type="text" class="form-control" id="inputlogin" placeholder="login" name="login" required="required">
                </div>
              </div>
              <div class="form-group row">
                <label for="inputpassword" class="col-2 col-form-label">Password</label>
                <div class="col-10">
                  <input type="password" class="form-control" id="inputpassword" placeholder="password" name="password" required="required">
                </div>
              </div>
              <button type="submit" class="btn btn-success">Submit</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>

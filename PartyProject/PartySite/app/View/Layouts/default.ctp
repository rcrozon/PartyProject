<!DOCTYPE html>
<!-- saved from url=(0051)http://getbootstrap.com/examples/starter-template/# -->
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="http://getbootstrap.com/assets/ico/favicon.ico">

    <title>Starter Template for Bootstrap</title>

    <!-- Bootstrap core CSS -->
     <link rel="stylesheet" href="<?php echo $this->Html->url('/css/bootstrap.min.css');?>">
  
    <link rel="stylesheet" href="<?php echo $this->Html->url('/css/headerCss.css');?>">

    <!-- Custom styles for this template -->


    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  <style type="text/css"></style></head>

  <body>

    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
               <a href="/" class="logo">
           <?php
     echo $this->Html->image('logo.png') ;
?>
        </a>
        </div>
        <div class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
          
           <?php if(AuthComponent::user('id')): ?>
                       
                        <li><?php echo $this->Html->link("Mon compte",array('action'=>'edit','controller'=>'clients')); ?></li>
                         <li><?php echo $this->Html->link("Administration",array('action'=>'edit','controller'=>'clients')); ?></li>
                          <li><?php echo $this->Html->link("Se déconnecter",'/clients/logout'); ?></li>
                        <?php else: ?>   
                        <li><?php echo $this->Html->link("Se connecter",array('action'=>'login','controller'=>'clients')); ?></li>   
                        <li><?php echo $this->Html->link("S'inscrire",array('action'=>'signup','controller'=>'clients')); ?></li>
                        <?php endif; ?>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
 

    <div class="container">

<div class="row">
  <div class="col-md-12">
   <?php echo $content_for_layout; ?>

</div>
</div>
</div>

     
      
  




    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  
      
<?php echo $this->Html->script('jquery-1.11.0.min.js');?>

<?php echo $this->Html->script('bootstrap.min.js');?>
</body></html>
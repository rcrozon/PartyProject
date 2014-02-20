<!DOCTYPE html>
<!-- saved from url=(0051)http://getbootstrap.com/examples/starter-template/# -->
<html lang="en">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
    <link rel="stylesheet" href="<?php echo $this->Html->url('/css/mosaic.css');?>">

    <!--<link rel="stylesheet" href="<?php echo $this->Html->url('/css/bootstrap.css');?>">-->
    <link rel="stylesheet" href="<?php echo $this->Html->url('/css/sb-admin.css');?>">
    <link rel="stylesheet" href="<?php echo $this->Html->url('/font-awesome/css/font-awesome.min.css');?>">

    <?php echo $this->Html->script('jquery-1.11.0.min.js');?>
    <?php echo $this->Html->script('mosaic.1.0.1.js');?>
    <?php echo $this->Html->script('less'); ?>
    <?php echo $scripts_for_layout; ?>

    <!-- Custom styles for this template -->


    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css"></style>
  </head>
  
  <body class="home">  
    <div id="wrapper2">
      <!-- Sidebar -->
      <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <div class=logo style="max-height: 40px;
    max-width: 180px;">
            <?php echo $this->Html->link(
                  $this->Html->image("logo.png", array("alt" => "Home")),
                  "/",
                  array('escape' => false)
              ); ?>
            </div>
        </div>

        <ul class="nav navbar-nav navbar-right navbar-user">
          <li class="dropdown user-dropdown">
            <?php if(AuthComponent::user('id')): ?>
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i>
                <?php echo ucfirst(AuthComponent::user('username')); ?> <b class="caret"></b></a>
            <?php else: ?>
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Doe <b class="caret"></b></a>
            <?php endif; ?>
            <ul class="dropdown-menu">
              <?php if(AuthComponent::user('id')): ?>
                <li><?php echo $this->Html->link('<i class="fa fa-user"></i> Profile',
                  array('action'=>'edit','controller'=>'clients'), array('escape' => false)); ?></li>
                <?php if(AuthComponent::user('admin')=='1'): ?>
                  <li><?php echo $this->Html->link('<i class="fa fa-gear"></i> Administration',
                    array('action'=>'index','controller'=>'admin/concerts'), array('escape' => false)); ?></li>
                <?php endif; ?>
                <li class="divider"></li>
                <li><?php echo $this->Html->link('<i class="fa fa-power-off"></i> Log Out',
                  array('action'=>'logout','controller'=>'clients'), array('escape' => false)); ?></li>
              <?php else: ?>
                <li><?php echo $this->Html->link('<i class="fa fa-user"></i> Register',
                  array('action'=>'signup','controller'=>'clients'), array('escape' => false)); ?></li>
                <li class="divider"></li>
                <li><?php echo $this->Html->link('<i class="fa fa-power-off"></i> Log In',
                  array('action'=>'login','controller'=>'clients'), array('escape' => false)); ?></li>
              <?php endif; ?>
            </ul>
          </li>
        </ul>
      </nav>

      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <?php echo $this->Session->Flash(); ?>
            <?php echo $this->Session->Flash('Auth'); ?>
            <?php echo $content_for_layout; ?>
          </div>
        </div>
      </div>
    </div>
  </body>
  <!-- JavaScript -->
  <?php echo $this->Html->script('jquery-1.10.2'); ?>
  <?php echo $this->Html->script('bootstrap'); ?>
</html>
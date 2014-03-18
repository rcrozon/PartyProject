<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr"></html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset utf-8"/>
    <title>
        <?php echo $title_for_layout; ?>
    </title>
    <link rel="stylesheet/less" href="<?php echo $this->Html->url('/css/login2.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/bootstrap.min.login.css');?>">
            <link rel="stylesheet" href="<?php echo $this->Html->url('/font-awesome/css/font-awesome.min.css');?>">

 <?php echo $this->Html->script('jquery-1.10.2'); ?>

    <?php echo $this->Html->script('less'); ?>
    <?php echo $this->Html->script('jquery.easing.1.2'); ?>
     <?php echo $this->Html->script('swfobject'); ?>
     <?php echo $this->Html->script('jquery.anythingslider'); ?>
     <?php echo $this->Html->script('prettify'); ?>
        <?php echo $this->Html->script('less'); ?>  

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
            <div class=logo style="max-height: 40px; max-width: 180px;">
            <?php echo $this->Html->link(
                  $this->Html->image("logo.png", array("alt" => "Home")),
                  "/",
                  array('escape' => false)
              ); ?>
            </div>
        </div>

        <div class="collapse navbar-collapse navbar-ex1-collapse">
          <ul class="nav navbar-nav navbar-right navbar-user">
            <?php if(AuthComponent::user('id')): ?>
            
            <li><?php echo $this->Html->link('<i class="fa fa-search"></i> Find concerts',
                  array('action'=>'searchConcert','controller'=>'concerts'), array('escape' => false)); ?></li>
              <li class="dropdown user-dropdown">
            <li><?php echo $this->Html->link('<i class="fa fa-ticket"></i> My Tickets',
                  array('action'=>'listMyAllReservations','controller'=>'reservations'), array('escape' => false)); ?></li>
              <li class="dropdown user-dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i>
                  <?php echo ucfirst(AuthComponent::user('username')); ?> <b class="caret"></b></a>
                <ul class="dropdown-menu">
                    <li><?php echo $this->Html->link('<i class="fa fa-user"></i> Profile',
                      array('action'=>'edit','controller'=>'clients'), array('escape' => false)); ?></li>
                    <?php if(AuthComponent::user('admin')=='1'): ?>
                      <li><?php echo $this->Html->link('<i class="fa fa-gear"></i> Administration',
                        array('action'=>'index','controller'=>'admin/concerts'), array('escape' => false)); ?></li>
                    <?php endif; ?>
                    <li class="divider"></li>
                    <li><?php echo $this->Html->link('<i class="fa fa-power-off"></i> Log Out',
                      array('action'=>'logout','controller'=>'clients'), array('escape' => false)); ?></li>
                </ul>
              </li>
            <?php else: ?>
                <li><?php echo $this->Html->link('<i class="fa fa-user"></i> Register',
                  array('action'=>'signup','controller'=>'clients'), array('escape' => false)); ?></li>
                <li><?php echo $this->Html->link('<i class="fa fa-power-off"></i> Log In',
                  array('action'=>'login','controller'=>'clients'), array('escape' => false)); ?></li>
               
</li>
            <?php endif; ?>
          </ul>
        </div><!-- /.navbar-collapse -->
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
    <!-- JavaScript -->
    <!--<?php echo $this->Html->script('jquery-1.10.2'); ?>-->
    <?php echo $this->Html->script('bootstrap'); ?>
  </body>
</html>
 
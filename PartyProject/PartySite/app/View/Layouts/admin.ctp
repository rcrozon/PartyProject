<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr"></html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset utf-8"/>
        <title>
            <?php echo $title_for_layout; ?>
        </title>
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/bootstrap.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/sb-admin.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/font-awesome/css/font-awesome.min.css');?>">
        <!--<link rel="stylesheet" href="<?php echo $this->Html->url('http://cdn.oesmith.co.uk/morris-0.4.3.min.css');?>">-->
        <?php echo $this->Html->script('less'); ?>
        <?php echo $scripts_for_layout; ?>
    </head>
    <body>  
        <!--<div class="topbar">
            <a href="/" class="logo">
                <?php echo $this->Html->image('logo.png'); ?>
            </a>
            <ul class="account">
                <?php if(AuthComponent::user('id')): ?>
                    <li><?php echo $this->Html->link("Se déconnecter",'/clients/logout'); ?></li>
                    <li><?php echo $this->Html->link("Mon compte",array('action'=>'edit','controller'=>'clients')); ?></li>
                    <li><?php echo $this->Html->link("Administration",array('action'=>'edit','controller'=>'clients')); ?></li>
                <?php else: ?>   
                    <li><?php echo $this->Html->link("Se connecter",array('action'=>'login','controller'=>'clients')); ?></li>   
                    <li><?php echo $this->Html->link("S'inscrire",array('action'=>'signup','controller'=>'clients')); ?></li>
                <?php endif; ?>
            </ul>
            <div class="topbar-inner">
                <div class="container">
                    <h3><a href="#">Title</a></h3>
                    <ul class="nav">
                        <li><a href="#" title=""></a></li>
                    </ul>
                </div>            
            </div>      
        </div>

        <div class="container">
            <?php echo $content_for_layout; ?>
        </div>-->
        <div id="wrapper">
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
                  <a class="navbar-brand" href="index">SB Admin</a>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-ex1-collapse">
                  <ul class="nav navbar-nav side-nav">
                    <li class="active"><a href="#"><i class="fa fa-dashboard"></i> Dashboard</a></li>
                    <li><a href="#"><i class="fa fa-bar-chart-o"></i> Charts</a></li>
                    <li><a href="#"><i class="fa fa-table"></i> Tables</a></li>
                    <li><a href="#"><i class="fa fa-edit"></i> Forms</a></li>
                    <li><a href="#"><i class="fa fa-font"></i> Typography</a></li>
                    <li><a href="#"><i class="fa fa-desktop"></i> Bootstrap Elements</a></li>
                    <li><a href="#"><i class="fa fa-wrench"></i> Bootstrap Grid</a></li>
                    <li><a href="#"><i class="fa fa-file"></i> Blank Page</a></li>
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-caret-square-o-down"></i> Dropdown <b class="caret"></b>
                      </a>
                      <ul class="dropdown-menu">
                        <li><a href="#">Dropdown Item</a></li>
                        <li><a href="#">Another Item</a></li>
                        <li><a href="#">Third Item</a></li>
                        <li><a href="#">Last Item</a></li>
                      </ul>
                    </li>
                  </ul>

                  <ul class="nav navbar-nav navbar-right navbar-user">
                    <li class="dropdown messages-dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> Messages <span class="badge">7</span> <b class="caret"></b></a>
                      <ul class="dropdown-menu">
                        <li class="dropdown-header">7 New Messages</li>
                        <li class="message-preview">
                          <a href="#">
                            <span class="avatar"><img src="http://placehold.it/50x50"></span>
                            <span class="name">John Smith:</span>
                            <span class="message">Hey there, I wanted to ask you something...</span>
                            <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                          </a>
                        </li>
                        <li class="divider"></li>
                        <li class="message-preview">
                          <a href="#">
                            <span class="avatar"><img src="http://placehold.it/50x50"></span>
                            <span class="name">John Smith:</span>
                            <span class="message">Hey there, I wanted to ask you something...</span>
                            <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                          </a>
                        </li>
                        <li class="divider"></li>
                        <li class="message-preview">
                          <a href="#">
                            <span class="avatar"><img src="http://placehold.it/50x50"></span>
                            <span class="name">John Smith:</span>
                            <span class="message">Hey there, I wanted to ask you something...</span>
                            <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                          </a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="#">View Inbox <span class="badge">7</span></a></li>
                      </ul>
                    </li>
                    <li class="dropdown alerts-dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> Alerts <span class="badge">3</span> <b class="caret"></b></a>
                      <ul class="dropdown-menu">
                        <li><a href="#">Default <span class="label label-default">Default</span></a></li>
                        <li><a href="#">Primary <span class="label label-primary">Primary</span></a></li>
                        <li><a href="#">Success <span class="label label-success">Success</span></a></li>
                        <li><a href="#">Info <span class="label label-info">Info</span></a></li>
                        <li><a href="#">Warning <span class="label label-warning">Warning</span></a></li>
                        <li><a href="#">Danger <span class="label label-danger">Danger</span></a></li>
                        <li class="divider"></li>
                        <li><a href="#">View All</a></li>
                      </ul>
                    </li>
                    <li class="dropdown user-dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> John Smith <b class="caret"></b></a>
                      <ul class="dropdown-menu">
                        <li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
                        <li><a href="#"><i class="fa fa-envelope"></i> Inbox <span class="badge">7</span></a></li>
                        <li><a href="#"><i class="fa fa-gear"></i> Settings</a></li>
                        <li class="divider"></li>
                        <li><a href="#"><i class="fa fa-power-off"></i> Log Out</a></li>
                      </ul>
                    </li>
                  </ul>
                </div>
            </nav>

            <div id="page-wrapper">
                <?php echo $this->Session->Flash(); ?>
                <?php echo $content_for_layout; ?>
            </div>
        </div>
    </body>
    <!--<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery. min.js"></script> -->
    <!-- JavaScript -->
    <?php echo $this->Html->script('jquery-1.10.2'); ?>
    <?php echo $this->Html->script('bootstrap'); ?>
    <!-- Page Specific Plugins -->
    <!--<script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
    <script src="http://cdn.oesmith.co.uk/morris-0.4.3.min.js"></script>
    <script src="js/morris/chart-data-morris.js"></script>
    <script src="js/tablesorter/jquery.tablesorter.js"></script>
    <script src="js/tablesorter/tables.js"></script>->
</html>
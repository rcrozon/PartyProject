<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr"></html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset utf-8"/>
        <title>
            <?php echo $title_for_layout; ?>
        </title>
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/token-input.css');?>" type="text/css">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/token-input-facebook.css');?>" type="text/css">

        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/bootstrap.min.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/bootstrap.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/bootstrap-responsive.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/datepicker.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/bootstrap-timepicker.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/opa-icons.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/charisma-app.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/sb-admin.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/font-awesome/css/font-awesome.min.css');?>">
        <link rel="stylesheet" href="<?php echo $this->Html->url('/css/morris.css');?>">

        <?php echo $this->Html->script('jquery-1.10.2'); ?>
        <?php echo $this->Html->script('flot/jquery.flot'); ?>
        <?php echo $this->Html->script('raphael-min'); ?>
        <?php echo $this->Html->script('morris'); ?>
        <?php echo $this->Html->script('jquery.tokeninput'); ?>
        <?php echo $this->Html->script('less'); ?>
        <?php echo $this->Html->script('bootstrap-datepicker.min'); ?>
        <?php echo $this->Html->script('bootstrap-timepicker.min'); ?>
        <?php echo $scripts_for_layout; ?>
    </head>
    <body>  
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
                    <?php echo $this->Html->link(
                        $this->Html->image("logo.png", array("alt" => "Home")),
                        "/",
                        array('escape' => false)
                    ); ?>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-ex1-collapse">
                  <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <?php echo $this->Html->link('<i class="fa fa-dashboard"></i> Dashboard',
                            array('action'=>'index','controller'=>'concerts'), array('escape' => false)); ?>
                    </li>
                    <li><a href="#"><i class="fa fa-bar-chart-o"></i> Charts</a></li>
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-table"></i> Tables <b class="caret"></b>
                      </a>
                      <ul class="dropdown-menu">
                        <li>
                            <?php echo $this->Html->link("Partys Table",array('action'=>'table_concert','controller'=>'concerts'));?>
                        </li>
                        <li>
                            <?php echo $this->Html->link("Artists Table",array('action'=>'table_artist','controller'=>'concerts'));?>
                        </li>
                        <li>
                            <?php echo $this->Html->link("Styles Table",array('action'=>'table_style','controller'=>'concerts'));?>
                        </li>
                        <li>
                            <?php echo $this->Html->link("Reservations Table",array('action'=>'table_reservation','controller'=>'concerts'));?>
                        </li>
                        <li>
                            <?php echo $this->Html->link("Clients Table",array('action'=>'table_client','controller'=>'concerts'));?>
                        </li>
                      </ul>
                    </li>
                    <li class="dropdown">
                      <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <i class="fa fa-edit"></i> Forms <b class="caret"></b>
                      </a>
                      <ul class="dropdown-menu">
                        <li>
                            <?php 
                                echo $this->Html->link("Add party",array('action'=>'addConcert','controller'=>'concerts'));
                            ?>
                        </li>
                      </ul>
                    </li>
                    <li class="divider" style="height: 1px;width:100%; margin: 9px 0;overflow: hidden;background-color: #e5e5e5;"></li>
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
                    <li class="divider" style="height: 1px;width:100%; margin: 9px 0;overflow: hidden;background-color: #e5e5e5;"></li>
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
                        <?php if(AuthComponent::user('id')): ?>
                          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i>
                            <?php echo ucfirst(AuthComponent::user('username')); ?> <b class="caret"></b></a>
                        <?php endif; ?>
                        <ul class="dropdown-menu">
                          <?php if(AuthComponent::user('id')): ?>
                            <li><?php echo $this->Html->link('<i class="fa fa-user"></i> Profile',
                              array('action'=>'edit','controller'=>'../clients'), array('escape' => false)); ?></li>
                            <li><?php echo $this->Html->link('<i class="fa fa-gear"></i> Site',
                              "/", array('escape' => false)); ?></li>
                            <li><a href="#"><i class="fa fa-envelope"></i> Inbox <span class="badge">7</span></a></li>
                            <li><a href="#"><i class="fa fa-gear"></i> Settings</a></li>
                            <li class="divider"></li>
                            <li><?php echo $this->Html->link('<i class="fa fa-power-off"></i> Log Out',
                              array('action'=>'logout','controller'=>'clients'), array('escape' => false)); ?></li>
                        </ul>
                        <?php endif; ?>
                    </li>

                  </ul>
                </div>
            </nav>

            <div id="page-wrapper">
                <?php echo $this->Session->Flash(); ?>
                <?php echo $content_for_layout; ?>
            </div>
        </div>
        <!-- JavaScript -->
        <?php echo $this->Html->script('bootstrap'); ?>        
        <!-- Page Specific Plugins -->
        <!--<script src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
        <script src="http://cdn.oesmith.co.uk/morris-0.4.3.min.js"></script>
        <script src="js/morris/chart-data-morris.js"></script>
        <script src="js/tablesorter/jquery.tablesorter.js"></script>
        <script src="js/tablesorter/tables.js"></script>-->
    </body>
</html>
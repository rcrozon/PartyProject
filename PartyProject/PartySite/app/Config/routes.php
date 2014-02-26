<?php
/**
 * Routes configuration
 *
 * In this file, you set up routes to your controllers and their actions.
 * Routes are very important mechanism that allows you to freely connect
 * different URLs to chosen controllers and their actions (functions).
 *
 * CakePHP(tm) : Rapid Development Framework (http://cakephp.org)
 * Copyright (c) Cake Software Foundation, Inc. (http://cakefoundation.org)
 *
 * Licensed under The MIT License
 * For full copyright and license information, please see the LICENSE.txt
 * Redistributions of files must retain the above copyright notice.
 *
 * @copyright     Copyright (c) Cake Software Foundation, Inc. (http://cakefoundation.org)
 * @link          http://cakephp.org CakePHP(tm) Project
 * @package       app.Config
 * @since         CakePHP(tm) v 0.2.9
 * @license       http://www.opensource.org/licenses/mit-license.php MIT License
 */
/**
 * Here, we are connecting '/' (base path) to controller called 'Pages',
 * its action called 'display', and we pass a param to select the view file
 * to use (in this case, /app/View/Pages/home.ctp)... 
 */
 
Router::mapResources('Concerts');
Router::parseExtensions();
	
Router::connect('/', array('controller' => 'Concerts', 'action' => 'showLastConcerts', 'home'));
Router::connect('/page/:slug-:id',array('controller'=>'Concerts','action'=>'showConcert'),array('pass'=> array('id','slug'), 'id'=>'[0-9]+','slug' =>'[a-z0-9\-]+'));

Router::connect('/page/:idC',array('controller'=>'Reservations','action'=>'addReservations'),array('pass'=> array('idC'), 'idC' =>'[0-9]+'));



Router::connect('/page/:id',array('controller'=>'Mobiles','action'=>'getConcertByID'),array('pass'=> array('id'), 'id'=>'[0-9]+'));
Router::connect('/page/:login',array('controller'=>'Mobiles','action'=>'getClientByName'),array('pass'=> array('login'), 'login' =>'[a-z0-9\-]+'));

Router::connect('/page/:id',array('controller'=>'Reservations','action'=>'create_pdf'),array('pass'=> array('id'), 'id'=>'[0-9]+'));
Router::connect('/page/:id-:idC',array('controller'=>'Reservations','action'=>'listReservations'),array('pass'=> array('id','idC'), 'id' =>'[0-9]+','idC' =>'[0-9]+'));




/**
 * ...and connect the rest of 'Pages' controller's URLs./**
 * Load all plugin routes. See the CakePlugin documentation on
 * how to customize the loading of plugin routes.
 */








	CakePlugin::routes();

/**
 * Load the CakePHP default routes. Only remove this if you do not want to use
 * the built-in default routes.
 */
	require CAKE . 'Config' . DS . 'routes.php';

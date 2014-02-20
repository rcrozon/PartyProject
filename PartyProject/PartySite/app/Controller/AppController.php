<?php
/**
 * Application level Controller
 *
 * This file is application-wide controller file. You can put all
 * application-wide controller-related methods here.
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
 * @package       app.Controller
 * @since         CakePHP(tm) v 0.2.9
 * @license       http://www.opensource.org/licenses/mit-license.php MIT License
 */

App::uses('Controller', 'Controller');
    
class AppController extends Controller {
   	public $uses = array('Client','Concert','Tarif','AssocTarif');

    public $components = array('Session','Cookie','Auth');

    function beforeFilter(){
    	//debug($this->request);
		$this->Auth->authenticate = array(
		    AuthComponent::ALL => array('userModel' => 'Client'),
		    'Basic',
		    'Form'
		);
		//$this->Auth->allow();
        $this->Auth->authorize = 'controller';
        if((empty($this->params['admin']) || $this->params['admin'] != 'true') /*&& $this->action != 'login'*/) {
            $this->Auth->allow();
        }
    }
		
        /*if(AuthComponent::user('admin')=='1') {
            $user['role'] = 'admin';
            //$this->save($this->request->data);
        }*/

		//if(AuthComponent::user('admin')=='1') {
		/*if(isset($this->request->params['admin']) && $this->request->params['admin'] == 'true') {
			if(AuthComponent::user('admin')=='1') {
				$this->layout = 'admin';
			}
			else {
				$this->layout = 'default';
			}
		} else {
			$this->layout = 'default';
		}*/


    /*public function beforeFilter() {
        if(isset($this->Auth)) {
            $this->Auth->userModel = 'Client';
            $this->Auth->fields = array('username' => 'login', 'password' => 'password');
            //$this->Auth->userScope = array('User.disabled' => 0);
            $this->Auth->loginAction = array('controller' => 'clients', 'action' => 'login');
            //$this->Auth->loginRedirect = '/admin/articles';
            $this->Auth->loginError = "Identifiant ou mot de passe incorrects.";
            $this->Auth->logoutRedirect = '/';
            $this->Auth->authError = "Vous n'avez pas accès à cette page.";
            $this->Auth->autoRedirect = false;
            $this->Auth->authorize = 'controller';
         
            if((empty($this->params['admin']) || $this->params['admin'] != 'true') && $this->action != 'login') {
                echo "PLOP";
                $this->Auth->allow();
            }
        }
    }*/

    public function isAuthorized() {
        return true;
    }
	
    public function beforeRender() {
        if(isset($this->request->params['admin']) && $this->request->params['admin'] == 'true') {
            if(AuthComponent::user('admin')=='1') {
                $this->layout = 'admin';
            }
            else {
                $this->layout = 'error';
            }
        } else {
            $this->layout = 'default';
        }
    }

    /*public function isAuthorized($user = null) {
        // Chacun des utilisateur enregistré peut accéder aux fonctions publiques
        if (empty($this->request->params['admin'])) {
            return true;
        }

        //echo $user['role'];

        // Seulement les administrateurs peuvent accéder aux fonctions d'administration
        if (isset($this->request->params['admin'])) {
            return (bool)(AuthComponent::user('admin') == '1');
        }

        // Par défaut n'autorise pas
        return false;
    }*/

	//function beforeFilter(){
        //$type = $this->Session->read('Auth.User.admin');
        //debug($type);
        //if($type == null || $type == '0') {
        //    $this->Auth->allow('index','view','showLastConcerts','showConcert');
        //}
        //else {
        //    $this->Auth->allow('*');
        //}
    //}
}

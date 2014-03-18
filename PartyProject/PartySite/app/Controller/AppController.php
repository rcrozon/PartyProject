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
   	public $uses = array('Client','Concert','Tarif','AssocTarif', 'Artist','Reservation','AssocArtist','Style','AssocStyle','AssocTicket','TicketInfo');

    public $components = array('Session','Cookie','Auth');

    function beforeFilter(){
		$this->Auth->authenticate = array(
		    AuthComponent::ALL => array('userModel' => 'Client'),
		    'Basic',
		     'Form' => array(
                'passwordHasher' => array(
                    'className' => 'Custom'
                ))
		);
        $this->Auth->authorize = 'Controller';
        if((empty($this->params['admin']) || $this->params['admin'] != 'true') /*&& $this->action != 'login'*/) {
            $this->Auth->allow();
        }
    }

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
        } 
    }
}

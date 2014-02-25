<?php
class ConcertsController extends AppController{

	public $components = array('RequestHandler');

    /*function beforeFilter(){
        $type = $this->Session->read('Auth.User.admin');
        //debug($type);
        if($type == '0') {
            echo "NO ADMIN";
            //$this->Auth->allow('index','view','showLastConcerts','showConcert');
        }
        else if($type == null) {
            echo "NOTHING";
            $this->Auth->allow('index','view','showLastConcerts','showConcert');
        }
        else {
            echo "ADMIN";
            $this->Auth->allow('*');
        }
    }*/
    
        /*if(isset($this->request->params['admin']) && $this->request->params['admin'] == 'true') {
            if(AuthComponent::user('admin')=='1') {
                $this->layout = 'admin';
                $this->Auth->allow('*');
            }
            else {
                $this->layout = 'default';
                $this->Auth->allow('index','view','showLastConcerts','showConcert');
            }
        } else {
            $this->layout = 'default';
            $this->Auth->allow('index','view','showLastConcerts','showConcert');
        }*/
    /*function beforeFilter(){
        $type = $this->Session->read('Auth.User.admin');
        if($type == null || $type == '0') {
            $this->Auth->allow('index','view','showLastConcerts','showConcert');
            $this->layout = 'default';
            //$this->Session->setFlash("PLOP", "notif", array('type'=>'error'));
        }
        else if($type == '1') {
            $this->Auth->allow('*');
            if(isset($this->request->params['admin']) && $this->request->params['admin'] == 'true') {
                $this->layout = 'admin';
            }
            else {
                $this->layout = 'default';
            }
        }
    }*/

	public function index() {
        $posts = $this->Concert->find('all');
        $this->set(array(
            'posts' => $posts,
            '_serialize' => array('posts')
        ));
    }

    public function view($id) {
        $post = $this->Concert->findById($id);
        $this->set(array(
            'post' => $post,
            '_serialize' => array('post')
        ));
    }

	function admin_addConcert() {
        //$this->layout = 'login';
        if($this->request->is('post')) {
            $d = $this->request->data; 
            $id_client = $this->Session->read('Auth.User.id');
            $d['Concert']['id_creator'] = $id_client;

            $extension = strtolower(pathinfo($d['Concert']['image_file']['name'], PATHINFO_EXTENSION));
            $uploadFolder = "img\Concerts";
            $uploadPath = WWW_ROOT . $uploadFolder; 
            $full_image_path = $uploadPath . '\\' . $d['Concert']['image_file']['name'];


            if(!empty($d['Concert']['image_file']['tmp_name']) && 
                in_array($extension, array('jpg', 'jpeg', 'png', 'gif'))) {

                if(move_uploaded_file($d['Concert']['image_file']['tmp_name'], $full_image_path)) {
                    //$this->Concert->saveFields('image', $d['Concert']['image_file']['name']);
                    $d['Concert']['image'] = $d['Concert']['image_file']['name'];
                }
                else{
                    echo'Sorry we can\'t load this image';
                }
            }

            if($this->Concert->save($d,true,array('name_concert','location','nb_seats','image','start_datetime','end_datetime','id_creator'))) {
                $this->Session->setFlash("Your party has been well created", "notif", array('type'=>'success'));
                $this->request->data = array();
                //echo $this->Concert->getLastInsertId();
                $this->redirect(array('controller' => 'Tarifs', 'action' => 'addTarif', $this->Concert->getLastInsertId()));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }
        }
    }

    function admin_index() {
        
    }

    function admin_table_concert() {
        $this->paginate = array('Concert' => array(
            'limit' => 15
        ));
        $d['concerts'] = $this->Paginate('Concert'/*, array(name_concert => '')*/);
        $this->set($d);
    }

    function admin_tariff($id) {
        $d = $this->Concert->find('first', array(
            'conditions' => array('Concert.id' => $id)
        ));
        //debug($d['Concert']['name_concert']);
        $d['idConcert'] = $d['Concert']['id'];
        $this->set($d);

        $d['partyName'] = $d['Concert']['name_concert'];
        $this->set($d);

        $d = $this->AssocTarif->find('all', array(
            'conditions' => array('AssocTarif.id_concert' => $id)
        ));
        //debug($d);

        for ($i = 0; $i <= sizeof($d)-1; $i++) {
            $id_tarif = $d[$i]['AssocTarif']['id_tarif'];
            //debug($id_tarif);
            $result[$i] = $this->Tarif->find('all',array('conditions' => array('Tarif.id' => $id_tarif)));
        }

        //debug($result);
        //$result = Hash::extract($result, 'Tarif'); 
        //debug($result);
        $d['tariffs'] = $result;
        $this->set($d);
        
    }

    function admin_delete($id) {
        $this->Session->setFlash('The party has been successfully deleted', 'notif',array('type'=>'success'));
        $this->Concert->delete($id);
        $this->redirect($this->referer());
    }

    function admin_tarif_delete($id) {
        $this->Session->setFlash('The tariff has been successfully deleted', 'notif',array('type'=>'success'));
        $this->Tarif->delete($id);
        $this->redirect($this->referer());
    }

    function admin_edit($id) {
        if($this->request->is('put')) {
            //debug($this->request->data);

            $d = $this->request->data;
            $extension = strtolower(pathinfo($d['Concert']['image_file']['name'], PATHINFO_EXTENSION));
            $uploadFolder = "img\Concerts";
            $uploadPath = WWW_ROOT . $uploadFolder; 
            $full_image_path = $uploadPath . '\\' . $d['Concert']['image_file']['name'];


            if(!empty($d['Concert']['image_file']['tmp_name']) && 
                in_array($extension, array('jpg', 'jpeg', 'png', 'gif'))) {

                if(move_uploaded_file($d['Concert']['image_file']['tmp_name'], $full_image_path)) {
                    //$this->Concert->saveFields('image', $d['Concert']['image_file']['name']);
                    $d['Concert']['image'] = $d['Concert']['image_file']['name'];
                }
                else{
                    echo'Sorry we can\'t load this image';
                }
            }

            if($this->Concert->save($d,true,
                array('name_concert','location','nb_seats','image','start_datetime','end_datetime','full','online'))) {
                    $this->Session->setFlash('The party has been successfully updated', 'notif', array('type'=>'success'));
                    $this->redirect(array('action' => 'table_concert'));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }

            //$this->Concert->save($this->request->data);
            //$this->Session->setFlash('The party has been successfully updated', 'notif');
            //$this->redirect(array('action' => 'table_concert'));
        }
        $this->Concert->id = $id;
        $this->request->data = $this->Concert->read();
        $d['partyName'] = $this->Concert->data['Concert']['name_concert'];
        $this->set($d);
    } 

    function admin_tarif_edit($id, $partyName, $idConcert) {
        if($this->request->is('put')) {
            $d = $this->request->data;

            if($this->Tarif->save($d,true,array('label','price'))) {
                    $this->Session->setFlash('The tariff has been successfully updated', 'notif', array('type'=>'success'));
                    $this->redirect(array('action' => 'tariff', $idConcert));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }

            //$this->Concert->save($this->request->data);
            //$this->Session->setFlash('The party has been successfully updated', 'notif');
            //$this->redirect(array('action' => 'table_concert'));
        }
        $this->Tarif->id = $id;
        $this->request->data = $this->Tarif->read();
        $d['partyName'] = $partyName;
        $this->set($d);
    }


    function showLastConcerts (){
          $d = $this->Concert->find('all', array('order' => array('Concert.id DESC')));
          $this->set('showLastConcerts',$d);
    }

    function showConcert (){
        $id = $this->params['named']['id'];   
        $d = $this->Concert->find('first',array(
            'conditions' => array('Concert.id' => $id)
        ));
     
        $d = Hash::extract($d, 'Concert'); 
        //Get id of Tarif
        $v = $this->AssocTarif->find('all',array('conditions' => array('AssocTarif.id_concert' => $id)));
       
       for ($i = 0; $i <= sizeof($v)-1; $i++) {
			//$idC = $showAssocTarif[$i]['AssocTarif']['id_concert'];
			$idT = $v[$i]['AssocTarif']['id_tarif'];
			$result[$i] = $this->Tarif->find('all',array('conditions' => array('Tarif.id' => $idT)));
        }
        $this->set('showConcert',$d);
        $this->set('showTarif',$result);
    }
}
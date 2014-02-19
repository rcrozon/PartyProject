<?php
class ConcertsController extends AppController{

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
                $this->Session->setFlash("Your party has been well created", "notif");
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

    function admin_delete($id) {
        $this->Session->setFlash('The party has been successfully deleted', 'notif'/*,array('type'=>'alert-danger')*/);
        $this->Concert->delete($id);
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
                    $this->Session->setFlash('The party has been successfully updated', 'notif');
                    $this->redirect(array('action' => 'table_concert'));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'alert-danger'));
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
<?php
class ConcertsController extends AppController{

	function addConcert() {
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
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }
        }
	}
}
    
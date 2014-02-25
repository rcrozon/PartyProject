<?php
class ConcertsController extends AppController{

	public $components = array('RequestHandler');

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
        if($this->request->is('post')) {
            $d = $this->request->data; 
            # # # # # # # # # # # # # # # # # # # # # # # # #
            # On récupère et concatène les noms des artistes 
            # # # # # # # # # # # # # # # # # # # # # # # # #
            $artists = $d['Concert']['name'];
            $artists = explode("|", $artists);
            $tab = array();
            foreach ($artists as $k => $v) {
                $name_artists = $this->Artist->find('all', array(
                    'conditions' => array('Artist.id' => $v)
                ));
                $tab[] = $name_artists[0]['Artist']['name'];
            }
            $concat_art = "";
            foreach ($tab as $k => $v) {
                $concat_art = $concat_art . $v . ", ";
            }
            $concat_art = substr($concat_art, 0, -2);
            $d['Concert']['artists'] = $concat_art;
            # # # # # # # # # # # # #
            # On récupère le créateur 
            # # # # # # # # # # # # #
            $id_client = $this->Session->read('Auth.User.id');
            $d['Concert']['id_creator'] = $id_client;
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            # On récupère l'image chargée et on la met sur le seveur
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            $extension = strtolower(pathinfo($d['Concert']['image_file']['name'], PATHINFO_EXTENSION));
            $uploadFolder = "img\Concerts";
            $uploadPath = WWW_ROOT . $uploadFolder; 
            $full_image_path = $uploadPath . '\\' . $d['Concert']['image_file']['name'];
            if(!empty($d['Concert']['image_file']['tmp_name']) && 
                in_array($extension, array('jpg', 'jpeg', 'png', 'gif'))) {

                if(move_uploaded_file($d['Concert']['image_file']['tmp_name'], $full_image_path)) {
                    $d['Concert']['image'] = $d['Concert']['image_file']['name'];
                }
                else{
                    echo'Sorry we can\'t load this image';
                }
            }
            # # # # # # # # # # # # # # # # # # # # # # # # #
            # Sauvegarde des données dans la base de donnée
            # # # # # # # # # # # # # # # # # # # # # # # # #
            if($this->Concert->save($d,true,array('name_concert','location','nb_seats','image','start_datetime','end_datetime','id_creator','artists'))) {
                $this->Session->setFlash("Your party has been well created", "notif", array('type'=>'success'));
                $this->request->data = array();
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
        $d['concerts'] = $this->Paginate('Concert');
        $this->set($d);
    }

    function admin_tariff($id) {
        $d = $this->Concert->find('first', array(
            'conditions' => array('Concert.id' => $id)
        ));

        $d['idConcert'] = $d['Concert']['id'];
        $this->set($d);

        $d['partyName'] = $d['Concert']['name_concert'];
        $this->set($d);

        $d = $this->AssocTarif->find('all', array(
            'conditions' => array('AssocTarif.id_concert' => $id)
        ));

        for ($i = 0; $i <= sizeof($d)-1; $i++) {
            $id_tarif = $d[$i]['AssocTarif']['id_tarif'];
            $result[$i] = $this->Tarif->find('all',array('conditions' => array('Tarif.id' => $id_tarif)));
        }

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
            $d = $this->request->data;
            # # # # # # # # # # # # # # # # # # # #
            # Partie artists : comme addConcert()
            # # # # # # # # # # # # # # # # # # # #
            $artists = $d['Concert']['name'];
            $artists = explode("|", $artists);
            $tab = array();
            foreach ($artists as $k => $v) {
                $name_artists = $this->Artist->find('all', array(
                    'conditions' => array('Artist.id' => $v)
                ));
                $tab[] = $name_artists[0]['Artist']['name'];
            }
            $concat_art = "";
            foreach ($tab as $k => $v) {
                $concat_art = $concat_art . $v . ", ";
            }
            $concat_art = substr($concat_art, 0, -2);
            $d['Concert']['artists'] = $concat_art;

            # # # # # # # # # # # 
            # Partie load image
            # # # # # # # # # # # 
            $extension = strtolower(pathinfo($d['Concert']['image_file']['name'], PATHINFO_EXTENSION));
            $uploadFolder = "img\Concerts";
            $uploadPath = WWW_ROOT . $uploadFolder; 
            $full_image_path = $uploadPath . '\\' . $d['Concert']['image_file']['name'];
            if(!empty($d['Concert']['image_file']['tmp_name']) && 
                in_array($extension, array('jpg', 'jpeg', 'png', 'gif'))) {

                if(move_uploaded_file($d['Concert']['image_file']['tmp_name'], $full_image_path)) {
                    $d['Concert']['image'] = $d['Concert']['image_file']['name'];
                }
                else{
                    echo'Sorry we can\'t load this image';
                }
            }
            # # # # # # # 
            # Sauvegarde
            # # # # # # # 
            if($this->Concert->save($d,true,
                array('name_concert','location','nb_seats','image','start_datetime','end_datetime','full','online', 'artists'))) {
                    $this->Session->setFlash('The party has been successfully updated', 'notif', array('type'=>'success'));
                    $this->redirect(array('action' => 'table_concert'));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }
        }
        # # # # # # # # # # # # # # # # # # 
        # Envoi le nom du concert à la vue
        # # # # # # # # # # # # # # # # # # 
        $this->Concert->id = $id;
        $this->request->data = $this->Concert->read();
        $d['partyName'] = $this->Concert->data['Concert']['name_concert'];
        $this->set($d);
        # # # # # # # # # # # # # # # # # # # # # # # # #
        # Envoi les artistes pré-sélectionné à la vue
        # # # # # # # # # # # # # # # # # # # # # # # # #
        $tmp = $this->Concert->data['Concert']['artists'];
        if(!empty($tmp)) {
            $tmp = explode(", ", $tmp);

            $tab = array();
            foreach ($tmp as $k => $v) {
                $id_artists = $this->Artist->find('all', array(
                    'conditions' => array('Artist.name' => $v)
                ));
                $tab[] = $id_artists[0]['Artist']['id'];
            }

            $prepoulated = "";
            for ($i = 0; $i <= sizeof($tab)-1; $i++) {
                $prepoulated = $prepoulated . "{id: ".$tab[$i].", name: ".'"'.$tmp[$i].'"'."},\n";
            }
            $prepoulated = substr($prepoulated, 0, -2);
            $d['artistsName'] = $prepoulated;
            $this->set($d);
        }
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
			$idT = $v[$i]['AssocTarif']['id_tarif'];
			$result[$i] = $this->Tarif->find('all',array('conditions' => array('Tarif.id' => $idT)));
        }
        $this->set('showConcert',$d);
        $this->set('showTarif',$result);
    }
}
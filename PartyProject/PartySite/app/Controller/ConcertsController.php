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

    function admin_index() {
        $nbClient = $this->Client->find('count');
        $d['nbClient'] = $nbClient;
        $this->set($d);
        # # # # # # # # # # # # # # # # # # # # # #
        $nbAdmin = $this->Client->find('count', array(
            'conditions' => array('Client.admin' => 1)
        ));
        $d['nbAdmin'] = $nbAdmin;
        $this->set($d);
        # # # # # # # # # # # # # # # # # # # # # #
        $reserv = $this->Reservation->find('all');
        $total = 0;
        foreach ($reserv as $k => $v) {
            $tarif = $this->Tarif->find('first', array(
                'conditions' => array('Tarif.id' => $v['Reservation']['id_tarif'])
            ));
            $total += $tarif['Tarif']['price'];
        }
        $d['sales'] = $total;
        $this->set($d);
        # # # # # # # # # # # # # # # # # # # # # #
        $nbParty = $this->Concert->find('count');
        $d['nbParty'] = $nbParty;
        $this->set($d);
        # # # # # # # # # # # # # # # # # # # # # #
        $todayDate = date('Y-m-d');
        $nbTodayNewClient = 0;
        $allClient = $this->Client->find('all');
        foreach ($allClient as $k => $v) {
            $dateClient = $v['Client']['created'];
            $YMD = date('Y-m-d', strtotime($dateClient));
            if($YMD == $todayDate)
                $nbTodayNewClient++;
        }
        $d['nbTodayNewClient'] = $nbTodayNewClient;
        $this->set($d);
        # # # # # # # # # # # # # # # # # # # # # #
        $nbTodayNewAdmin = 0;
        $admin = $this->Client->find('all', array(
            'conditions' => array('Client.admin' => 1)
        ));
        foreach ($admin as $k => $v) {
            $dateAdmin = $v['Client']['created'];
            $YMD = date('Y-m-d', strtotime($dateAdmin));
            if($YMD == $todayDate)
                $nbTodayNewAdmin++;
        }
        $d['nbTodayNewAdmin'] = $nbTodayNewAdmin;
        $this->set($d);
        # # # # # # # # # # # # # # # # # # # # # #
        $nbTodaySales = 0;
        $total = 0;
        foreach ($reserv as $k => $v) {
            $dateReserv = $v['Reservation']['created'];
            $YMD = date('Y-m-d', strtotime($dateReserv));
            if($YMD == $todayDate) {
                $tarif = $this->Tarif->find('first', array(
                    'conditions' => array('Tarif.id' => $v['Reservation']['id_tarif'])
                ));
                $total += $tarif['Tarif']['price'];
            }
        }
        $d['nbTodaySales'] = $total;
        $this->set($d);
        # # # # # # # # # # # # # # # # # # # # # #
        $nbTodayNewParty = 0;
        $party = $this->Concert->find('all');
        foreach ($party as $k => $v) {
            $dateParty = $v['Concert']['created'];
            $YMD = date('Y-m-d', strtotime($dateParty));
            if($YMD == $todayDate)
                $nbTodayNewParty++;
        }
        $d['nbTodayNewParty'] = $nbTodayNewParty;
        $this->set($d);
        # # # # # # # # # # # # # # # # # # # # # #
        $nbReserv = $this->Reservation->find('count');
        $nbScanned = $this->Reservation->find('count', array(
            'conditions' => array('Reservation.scan' => 1)
        ));
        if($nbReserv != 0) {
            $percentScanned = ($nbScanned * 100) / $nbReserv;
            $percentNotScanned = (($nbReserv - $nbScanned) * 100) / $nbReserv;
        }
        else {
            $percentScanned = 0;
            $percentNotScanned = 0;        
        }
        $d['percentScanned'] = $percentScanned;
        $this->set($d);
        $d['percentNotScanned'] = $percentNotScanned;
        $this->set($d);
        # # # # # # # # # # # # # # # # # # # # # #
        $year = date('Y');
        $month = date('m');
        $d['year'] = $year;
        $this->set($d);
        $d['month'] = $month;
        $this->set($d);

        $nbTodaySales = 0;
        $dataDay = "";
        for($i = 1; $i <= 31; $i++) {
            $result[$i] = 0;
        }
        foreach ($reserv as $k => $v) {
            $dateReserv = $v['Reservation']['created'];
            $YMD = date('Y-m-d', strtotime($dateReserv));
            $Y = date('Y', strtotime($dateReserv));
            $M = date('m', strtotime($dateReserv));
            $D = date('d', strtotime($dateReserv));
            for($i = 1; $i <= 31; $i++) {
                if($Y == $year && $D == $month) {
                    if($D == $i) {
                        $tarif = $this->Tarif->find('first', array(
                            'conditions' => array('Tarif.id' => $v['Reservation']['id_tarif'])
                        ));
                        $result[$i] += $tarif['Tarif']['price'];
                    }
                }
            }
        }
        for($i = 1; $i <= 31; $i++) {
            $dataDay = $dataDay.'{"period": "'.$year.'-'.$month.'-0'.$i.'","nbSales": '.$result[$i].'},';
        }
        $dataDay = substr($dataDay, 0, -1);
        $d['dataDay'] = $dataDay;
        $this->set($d);
    }

	function admin_addConcert() {
        if($this->request->is('post')) {
            $d = $this->request->data; 
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            # On récupère les ID des artistes pour mettre à jour la table AssocArtists 
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            $artists = $d['Concert']['name'];
            if(!empty($artists)) {
                $artists = explode("|", $artists);
                $tab = array();
                foreach ($artists as $k => $v) {
                    $name_artists = $this->Artist->find('all', array(
                        'conditions' => array('Artist.id' => $v)
                    ));
                    $tabName[] = $name_artists[0]['Artist']['name'];
                    $tabID[] = $name_artists[0]['Artist']['id'];
                }
            }
            # # # # # # # # # # # # #
            # On récupère le créateur 
            # # # # # # # # # # # # #
            $id_client = $this->Session->read('Auth.User.id');
            $d['Concert']['id_creator'] = $id_client;
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            # On récupère l'image chargée et on la met sur le seveur
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            $extension = strtolower(pathinfo($d['Concert']['image_file']['name'], PATHINFO_EXTENSION));
            $uploadFolder = "img/Concerts";
            $uploadPath = WWW_ROOT . $uploadFolder; 
            $full_image_path = $uploadPath . '/' . $d['Concert']['image_file']['name'];
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
            if($this->Concert->save($d,true,array('name_concert','location','nb_seats','image','start_datetime','end_datetime','id_creator'/*,'artists'*/))) {
                $this->Session->setFlash("Your party has been well created", "notif", array('type'=>'success'));
                $this->request->data = array();

                $idConcert = $this->Concert->getLastInsertId();
                foreach ($tabID as $k => $v) {
                    $data = array('id_artist' => $v, 'id_concert' => $idConcert);
                    $this->AssocArtist->create();
                    $this->AssocArtist->save($data);
                }
                
                $this->redirect(array('controller' => 'Tarifs', 'action' => 'addTarif', $this->Concert->getLastInsertId()));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            } 
        }
    }

    function admin_add_artist($id) {
        if($this->request->is('post')) {
            $d = $this->request->data; 
            //debug($d);
            $artists = $d['AssocArtist']['name'];
            if(!empty($artists)) {
                $artists = explode("|", $artists);
                $tab = array();
                foreach ($artists as $k => $v) {
                    $name_artists = $this->Artist->find('all', array(
                        'conditions' => array('Artist.id' => $v)
                    ));
                    $tabName[] = $name_artists[0]['Artist']['name'];
                    $tabID[] = $name_artists[0]['Artist']['id'];
                }
                foreach ($tabID as $k => $v) {
                    $data = array('id_artist' => $v, 'id_concert' => $id);
                    $this->AssocArtist->create();
                    $this->AssocArtist->save($data);
                }
                $this->Session->setFlash("Artists has been well added", "notif", array('type'=>'success'));
                $this->redirect(array('controller' => 'Concerts', 'action' => 'artist', $id));
            }
        }
        $d = $this->Concert->find('first', array(
            'conditions' => array('Concert.id' => $id)
        ));
        $d['partyName'] = $d['Concert']['name_concert'];
        $this->set($d);
    }

    function admin_add_table_artist() {
        if($this->request->is('post')) {
            $d = $this->request->data; 

            if($this->Artist->save($d,true,array('name'))) {
                $this->Session->setFlash("Artist has been well added", "notif", array('type'=>'success'));
                $this->redirect(array('controller' => 'Concerts', 'action' => 'table_artist'));
            } else {
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }
        }
    }

    function admin_add_tariff($id) {
        if($this->request->is('post')) {
            $d = $this->request->data;

            if($this->Tarif->save($d,true,array('label','price'))) {
                $this->Session->setFlash('The tariff has been successfully added', 'notif', array('type'=>'success'));

                $idTarif = $this->Tarif->getLastInsertId();
                $data = array('id_tarif' => $idTarif, 'id_concert' => $id);
                $this->AssocTarif->create();
                $this->AssocTarif->save($data);

                $this->redirect(array('action' => 'tariff', $id));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }
        }
        $d = $this->Concert->find('first', array(
            'conditions' => array('Concert.id' => $id)
        ));
        $d['partyName'] = $d['Concert']['name_concert'];
        $this->set($d);
    }

    function admin_table_concert() {
        $this->paginate = array('Concert' => array(
            'limit' => 5
        ));
        $d['concerts'] = $this->Paginate('Concert');
        $this->set($d);
    }

    function admin_table_reservation() {
        $this->paginate = array('Reservation' => array(
            'limit' => 5
        ));
        $d['reserv'] = $this->Paginate('Reservation');
        $this->set($d);
    }

    function admin_table_artist() {
        $this->paginate = array('Artist' => array(
            'limit' => 5
        ));
        $d['artist'] = $this->Paginate('Artist');
        $this->set($d);
    }

    function admin_table_client() {
        $this->paginate = array('Client' => array(
            'limit' => 5
        ));
        $d['client'] = $this->Paginate('Client');
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

    function admin_artist($id) {
        $d = $this->Concert->find('first', array(
            'conditions' => array('Concert.id' => $id)
        ));

        $d['idConcert'] = $d['Concert']['id'];
        $this->set($d);

        $d['partyName'] = $d['Concert']['name_concert'];
        $this->set($d);

        $d = $this->AssocArtist->find('all', array(
            'conditions' => array('AssocArtist.id_concert' => $id)
        ));

        for ($i = 0; $i <= sizeof($d)-1; $i++) {
            $id_artist = $d[$i]['AssocArtist']['id_artist'];
            $result[$i] = $this->Artist->find('all',array('conditions' => array('Artist.id' => $id_artist)));
        }

        if(!empty($result)) {
            $d['artists'] = $result;
            $this->set($d);
        }
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

    function admin_reserv_delete($id) {
        $this->Session->setFlash('The reservation has been successfully deleted', 'notif',array('type'=>'success'));
        $this->Reservation->delete($id);
        $this->redirect($this->referer());
    }

    function admin_artist_delete($id, $idConcert) {
        $this->Session->setFlash('The artist has been successfully deleted', 'notif',array('type'=>'success'));
        $d = $this->AssocArtist->find('all', array(
            'conditions' => array('AssocArtist.id_artist' => $id, 'AssocArtist.id_concert' => $idConcert)
        ));
        $id_assoc_artist = $d[0]['AssocArtist']['id'];
        $this->AssocArtist->delete($id_assoc_artist);
        $this->redirect($this->referer());
    }

    function admin_artist_table_delete($id) {
        $this->Session->setFlash('The artist has been successfully deleted', 'notif',array('type'=>'success'));
        $this->Artist->delete($id);
        $this->redirect($this->referer());
    }

    function admin_client_table_delete($id) {
        $this->Session->setFlash('The cleint has been successfully deleted', 'notif',array('type'=>'success'));
        $this->Client->delete($id);
        $this->redirect($this->referer());
    }

    function admin_edit($id) {
        if($this->request->is('put')) {
            $d = $this->request->data;
            # # # # # # # # # # # # # # # # # # # #
            # Partie artists : comme addConcert()
            # # # # # # # # # # # # # # # # # # # #
            $artists = $d['Concert']['name'];
            if(!empty($artists)) {
                $artists = explode("|", $artists);
                $tab = array();
                foreach ($artists as $k => $v) {
                    $name_artists = $this->Artist->find('all', array(
                        'conditions' => array('Artist.id' => $v)
                    ));
                    $tabName[] = $name_artists[0]['Artist']['name'];
                    $tabID[] = $name_artists[0]['Artist']['id'];
                }
            }
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

                    $d = $this->AssocArtist->find('all', array(
                        'conditions' => array('AssocArtist.id_concert' => $id)
                    ));
                    for ($i = 0; $i <= sizeof($d)-1; $i++) {
                        $this->AssocArtist->delete($d[$i]['AssocArtist']['id']);
                    }
                    $idConcert = $id;
                    foreach ($tabID as $k => $v) {
                        $data = array('id_artist' => $v, 'id_concert' => $idConcert);
                        $this->AssocArtist->create();
                        $this->AssocArtist->save($data);
                    }
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
        $d = $this->AssocArtist->find('all', array(
            'conditions' => array('AssocArtist.id_concert' => $id)
        ));
        for ($i = 0; $i <= sizeof($d)-1; $i++) {
            $id_artist = $d[$i]['AssocArtist']['id_artist'];
            $result[$i] = $this->Artist->find('all',array('conditions' => array('Artist.id' => $id_artist)));
        }
        if(!empty($result)) {
            $prepoulated = "";
            for ($i = 0; $i <= sizeof($result)-1; $i++) {
                $prepoulated = $prepoulated . "{id: ".$result[$i][0]['Artist']['id'].", name: ".'"'.$result[$i][0]['Artist']['name'].'"'."},\n";
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

    function admin_reserv_edit($id) {
        if($this->request->is('put')) {
            $d = $this->request->data;

            if($this->Reservation->save($d,true,array('id_concert','id_client', 'id_tarif', 'scan'))) {
                    $this->Session->setFlash('The reservation has been successfully updated', 'notif', array('type'=>'success'));
                    $this->redirect(array('action' => 'table_reservation'));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }
        }

        $this->Reservation->id = $id;
        $this->request->data = $this->Reservation->read();
    }

    function admin_artist_edit($id, $partyName, $idConcert) {
        if($this->request->is('put')) {
            $d = $this->request->data;

            if($this->Artist->save($d,true,array('name'))) {
                    $this->Session->setFlash('The artist has been successfully updated', 'notif', array('type'=>'success'));
                    $this->redirect(array('action' => 'artist', $idConcert));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }
        }

        $this->Artist->id = $id;
        $this->request->data = $this->Artist->read();
        $d['partyName'] = $partyName;
        $this->set($d);
    }

    function admin_artist_table_edit($id) {
        if($this->request->is('put')) {
            $d = $this->request->data;

            if($this->Artist->save($d,true,array('name'))) {
                    $this->Session->setFlash('The artist has been successfully updated', 'notif', array('type'=>'success'));
                    $this->redirect(array('action' => 'table_artist'));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }
        }
        $this->Artist->id = $id;
        $this->request->data = $this->Artist->read();
    }

    function admin_client_table_edit($id) {
        if($this->request->is('put')) {
            $d = $this->request->data;

            if($this->Client->save($d,true,array('username','mail','first_name','last_name','admin'))) {
                    $this->Session->setFlash('The client has been successfully updated', 'notif', array('type'=>'success'));
                    $this->redirect(array('action' => 'table_client'));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }
        }
        $this->Client->id = $id;
        $this->request->data = $this->Client->read();
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
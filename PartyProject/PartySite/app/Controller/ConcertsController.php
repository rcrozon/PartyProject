<?php
class ConcertsController extends AppController{

	public $components = array('RequestHandler');

    public function beforeRender() {
        if(isset($this->request->params['admin']) && $this->request->params['admin'] == 'true') {
            if(AuthComponent::user('admin')=='0') {
                $this->redirect(array('controller' => 'missing_controller', 'action' => ''));
            }
            else if(AuthComponent::user('admin')=='1') {
                $this->layout = 'admin';
            }
        }
    }

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
        $reserv = $this->Reservation->find('all');
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
                if($Y == $year && $M == $month) {
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
        # # # # # # # # # # # # # # # # # # # # # #
        $party = $this->Concert->find('all');
        $tab_reserv = array();
        $tab_idConcert = array();
        foreach ($party as $k => $v) {
            $id_concert = $v['Concert']['id'];
            $nb_reserv = $this->Reservation->find('count', array(
                'conditions' => array('Reservation.id_concert' => $id_concert)
            ));
            $tab_reserv[] = $nb_reserv;
            $tab_idConcert[] = $id_concert;
        }
        $top = 5;
        $rank = 1;
        $result = "";
        while($top != 0) {
            $max = -1;
            $indice = -1;
            for($i = 0; $i <= sizeof($tab_reserv)-1; $i++) {
                if($tab_reserv[$i] != -1) {
                    if($tab_reserv[$i] > $max) {
                        $max = $tab_reserv[$i];
                        $indice = $i;
                    }
                }
            }
            if($indice != -1) {
                $nameConcert = $this->Concert->find('first', array(
                    'conditions' => array('Concert.id' => $tab_idConcert[$indice])
                ));
                $result = $result."<tr><td>".$rank."</td><td>".$nameConcert['Concert']['name_concert']."</td><td style=".'"'."padding-left:25px;".'"'.">".$tab_reserv[$indice]."</td></tr>";
                $tab_reserv[$indice] = -1;
                $rank++;
            }
            $top--;
        }
        $result = "<table><th>Rank</th><th>Name</th><th>Nb Sales</th>".$result."</table>";
        $d['top5'] = $result;
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
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            # On récupère les ID des styles pour mettre à jour la table AssocStyles
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            $styles = $d['Concert']['style'];
            if(!empty($styles)) {
                $styles = explode("|", $styles);
                $tab = array();
                foreach ($styles as $k => $v) {
                    $name_styles = $this->Style->find('all', array(
                        'conditions' => array('Style.id' => $v)
                    ));
                    $tabNameStyle[] = $name_styles[0]['Style']['name'];
                    $tabIDStyle[] = $name_styles[0]['Style']['id'];
                }
            }
            # # # # # # # # # # # # #
            # On récupère le créateur 
            # # # # # # # # # # # # #
            $id_client = $this->Session->read('Auth.User.id');
            $d['Concert']['id_creator'] = $id_client;
            # # # # # # # # # # # # # # # # # #
            # On récupère les dates et heures
            # # # # # # # # # # # # # # # # # #
            $start_date = $d['start_date'];
            $end_date = $d['end_date'];
            $start_hour = $d['start_hour'];
            $end_hour = $d['end_hour'];

            $start_datetime = new DateTime($start_date." ".$start_hour);
            $end_datetime = new DateTime($end_date." ".$end_hour);

            $d['Concert']['start_datetime'] = $start_datetime->format('Y-m-d H:i:s');
            $d['Concert']['end_datetime'] = $end_datetime->format('Y-m-d H:i:s');
            # # # # # # # # # # # # # # # # # # # # # # # # #
            # Sauvegarde des données dans la base de donnée
            # # # # # # # # # # # # # # # # # # # # # # # # #
            if($this->Concert->isUploadedFile($d['Concert']['image_file'])/*is_uploaded_file($d['Concert']['image_file']['tmp_name'])*/) {
                # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
                # On récupère l'image chargée et on la met sur le seveur
                # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
                $extension = strtolower(pathinfo($d['Concert']['image_file']['name'], PATHINFO_EXTENSION));
                $uploadFolder = "img/Concerts";
                $uploadPath = WWW_ROOT . $uploadFolder;
                $image = $d['Concert']['image_file']['name'];
                $image = str_replace(" ", "-", $image);
                $image = str_replace('.'.$extension, "-".date('Y-m-d-H-i-s').'.'.$extension, $image);
                $d['Concert']['image_file']['name'] = $image;
                $full_image_path = $uploadPath . '/' . $d['Concert']['image_file']['name'];
                if(!empty($d['Concert']['image_file']['tmp_name']) && 
                    in_array($extension, array('jpg', 'jpeg', 'png', 'gif'))) {

                    if(move_uploaded_file($d['Concert']['image_file']['tmp_name'], $full_image_path)) {
                        $d['Concert']['image'] = $d['Concert']['image_file']['name'];
                    }
                    else{
                        echo 'Sorry we can\'t load this image';
                    }
                }
                # # # # # # # # # # # # # 
                # Sauvegarde des données
                # # # # # # # # # # # # # 
                if($this->Concert->save($d,true,array('name_concert','location','nb_seats','image','start_datetime','end_datetime','id_creator'))) {
                    $this->Session->setFlash("Your party has been well created", "notif", array('type'=>'success'));
                    $this->request->data = array();

                    $idConcert = $this->Concert->getLastInsertId();
                    foreach ($tabID as $k => $v) {
                        $data = array('id_artist' => $v, 'id_concert' => $idConcert);
                        $this->AssocArtist->create();
                        $this->AssocArtist->save($data);
                    }

                    foreach ($tabIDStyle as $k => $v) {
                        $data = array('id_style' => $v, 'id_concert' => $idConcert);
                        $this->AssocStyle->create();
                        $this->AssocStyle->save($data);
                    }
                    
                    $this->redirect(array('controller' => 'Tarifs', 'action' => 'addTarif', $this->Concert->getLastInsertId()));
                } else{
                    $this->Session->setFlash("Thanks to correct your mistakes", "notif", array('type'=>'error'));
                }
            }
            else {
                $this->Session->setFlash("Please upload an image", "notif", array('type'=>'error'));
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

    function admin_add_table_style() {
        if($this->request->is('post')) {
            $d = $this->request->data; 

            if($this->Style->save($d,true,array('name'))) {
                $this->Session->setFlash("Style has been well added", "notif", array('type'=>'success'));
                $this->redirect(array('controller' => 'Concerts', 'action' => 'table_style'));
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

    function admin_table_style() {
        $this->paginate = array('Style' => array(
            'limit' => 5
        ));
        $d['style'] = $this->Paginate('Style');
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
        App::uses('Folder', 'Utility');
        App::uses('File', 'Utility');
        $uploadFolder = "img/Concerts";
        $uploadPath = WWW_ROOT . $uploadFolder;
        $concert = $this->Concert->find('first', array(
            'conditions' => array('Concert.id' => $id)
        ));
        $img = $concert['Concert']['image'];
        $dir = new Folder($uploadPath);
        $files = $dir->find($img);
        foreach ($files as $file) {
            $file = new File($uploadPath.DS.$file);
            $file->delete();
            $file->close();
        }

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

    function admin_style_table_delete($id) {
        $this->Session->setFlash('The style has been successfully deleted', 'notif',array('type'=>'success'));
        $this->Style->delete($id);
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
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            # On récupère les ID des styles pour mettre à jour la table AssocStyles
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            $styles = $d['Concert']['style'];
            if(!empty($styles)) {
                $styles = explode("|", $styles);
                $tab = array();
                foreach ($styles as $k => $v) {
                    $name_styles = $this->Style->find('all', array(
                        'conditions' => array('Style.id' => $v)
                    ));
                    $tabNameStyle[] = $name_styles[0]['Style']['name'];
                    $tabIDStyle[] = $name_styles[0]['Style']['id'];
                }
            }
            # # # # # # # # # # # # # # # # # #
            # Suppression de l'ancienne image #
            # # # # # # # # # # # # # # # # # #
            if(!empty($d['Concert']['image_file']['name'])) {
                App::uses('Folder', 'Utility');
                App::uses('File', 'Utility');
                $uploadFolder = "img/Concerts";
                $uploadPath = WWW_ROOT . $uploadFolder;
                $concert = $this->Concert->find('first', array(
                    'conditions' => array('Concert.id' => $id)
                ));
                $img = $concert['Concert']['image'];
                $dir = new Folder($uploadPath);
                $files = $dir->find($img);
                foreach ($files as $file) {
                    $file = new File($uploadPath.DS.$file);
                    $file->delete();
                    $file->close();
                }
            }
            # # # # # # # # # # # # #
            # Partie load new image
            # # # # # # # # # # # # #
            $extension = strtolower(pathinfo($d['Concert']['image_file']['name'], PATHINFO_EXTENSION));
            $uploadFolder = "img/Concerts";
            $uploadPath = WWW_ROOT . $uploadFolder;
            $image = $d['Concert']['image_file']['name'];
            $image = str_replace(" ", "-", $image);
            $image = str_replace('.'.$extension, "-".date('Y-m-d-H-i-s').'.'.$extension, $image);
            $d['Concert']['image_file']['name'] = $image;
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
                    # # # # # # # # # # # # # # # # 
                    # Sauvegarde dans AssocArtist #
                    # # # # # # # # # # # # # # # # 
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
                    # # # # # # # # # # # # # # # # 
                    # Sauvegarde dans AssocStyle  #
                    # # # # # # # # # # # # # # # # 
                    $d = $this->AssocStyle->find('all', array(
                        'conditions' => array('AssocStyle.id_concert' => $id)
                    ));
                    for ($i = 0; $i <= sizeof($d)-1; $i++) {
                        $this->AssocStyle->delete($d[$i]['AssocStyle']['id']);
                    }
                    $idConcert = $id;
                    foreach ($tabIDStyle as $k => $v) {
                        $data = array('id_style' => $v, 'id_concert' => $idConcert);
                        $this->AssocStyle->create();
                        $this->AssocStyle->save($data);
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
        # # # # # # # # # # # # # # # # # # # # # # # # #
        # Envoi les styles pré-sélectionné à la vue
        # # # # # # # # # # # # # # # # # # # # # # # # #
        $result = "";
        $d = $this->AssocStyle->find('all', array(
            'conditions' => array('AssocStyle.id_concert' => $id)
        ));
        for ($i = 0; $i <= sizeof($d)-1; $i++) {
            $id_style = $d[$i]['AssocStyle']['id_style'];
            $result[$i] = $this->Style->find('all',array('conditions' => array('Style.id' => $id_style)));
        }
        if(!empty($result)) {
            $prepoulatedStyle = "";
            for ($i = 0; $i <= sizeof($result)-1; $i++) {
                $prepoulatedStyle = $prepoulatedStyle . "{id: ".$result[$i][0]['Style']['id'].", name: ".'"'.$result[$i][0]['Style']['name'].'"'."},\n";
            }
            $prepoulatedStyle = substr($prepoulatedStyle, 0, -2);
            $d['stylesName'] = $prepoulatedStyle;
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

    function admin_style_table_edit($id) {
        if($this->request->is('put')) {
            $d = $this->request->data;

            if($this->Style->save($d,true,array('name'))) {
                    $this->Session->setFlash('The style has been successfully updated', 'notif', array('type'=>'success'));
                    $this->redirect(array('action' => 'table_style'));
            } else{
                $this->Session->setFlash("Thanks to correct your mistakes","notif",array('type'=>'error'));
            }
        }
        $this->Style->id = $id;
        $this->request->data = $this->Style->read();
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

        /* Recuperation des styles */
        $results = $this->AssocStyle->find('all',array(
            'conditions' => array('AssocStyle.id_concert' => $id)));
        for ($i = 0; $i < sizeof($results); $i++) {
            
            $styles[$i] = $this->Style->find('first',array('conditions' => array('Style.id' => 
                $results[$i]['AssocStyle'])));
        }
         /* Recuperation des artistes */
         $results = $this->AssocArtist->find('all',array(
            'conditions' => array('AssocArtist.id_concert' => $id)));
        for ($i = 0; $i < sizeof($results); $i++) {            
            $artists[$i] = $this->Artist->find('first',array('conditions' => array('Artist.id' => 
                $results[$i]['AssocArtist'])));
        }
     
        $d = Hash::extract($d, 'Concert'); 
        //Get id of Tarif
        $v = $this->AssocTarif->find('all',array('conditions' => array('AssocTarif.id_concert' => $id)));
       
       for ($i = 0; $i <= sizeof($v)-1; $i++) {
			$idT = $v[$i]['AssocTarif']['id_tarif'];
			$result[$i] = $this->Tarif->find('all',array('conditions' => array('Tarif.id' => $idT)));
        }

        $this->set('showConcert',$d);
        $this->set('styles',$styles);
        $this->set('artists',$artists);

        $this->set('showTarif',$result);
    }
	  function searchConcert (){
                $this->layout = 'search';

        




               
                 if($this->request->is('post')) {



                    $d = $this->request->data;
                    if(!empty($d['Search'])){



                         $allconcerts= $this->Concert->find('all', array(
                        'conditions' => array("Concert.name_concert LIKE" => "%".$d['Search']['search']."%")
                    ));
                        $this->set("allconcerts",$allconcerts);

                    }
                    else{
                   
                    $sql = "SELECT DISTINCT concerts.id FROM concerts  
                    JOIN assoc_tarifs ON assoc_tarifs.id_concert = concerts.id
                    JOIN tarifs ON tarifs.id =  assoc_tarifs.id_tarif
                    JOIN assoc_artists ON assoc_artists.id_concert = concerts.id
                    JOIN artists ON artists.id =  assoc_artists.id_artist
                    JOIN assoc_styles ON assoc_styles.id_concert = concerts.id
                    JOIN styles ON styles.id =  assoc_styles.id_style
                    ";
 
 /*******************TRAITEMENT PRICE***********************************************/

                    $prices = explode(",", $d['Concert']['price']);

                    $sql .= "AND tarifs.price > ". $prices[0];
                    $sql .= " AND tarifs.price < ". $prices[1];

              
                
/*********************ARTISTS TRAITEMENT********************************************/

                   $artists = $d['Concert']['name'];
                    if(!empty($artists)) {
                        $artists = explode("|", $artists);
                        
                        if(sizeof($artists==1)){
                                $sql .= " AND artists.id = ". $artists[0]; 
                        }
                        else{
                        for($i = 1; $i<=sizeof($artists);$i++){
                            if($i == 1){
                               $sql .= " AND (artists.id = ". $artists[$i-1]; 
                            }
                            else if($i == sizeof($artists)){
                                $sql .= " OR artists.id = ". $artists[$i-1].")";
                            }
                            else{
                                   $sql .= " OR artists.id = ". $artists[$i-1];
                            }
                        }
                    }

                       
                    }

/*********************STYLES TRAITEMENT********************************************/

 $styles = $d['Concert']['style'];
                    if(!empty($styles)) {
                        $styles = explode("|", $styles);
                        
                        if(sizeof($styles==1)){
                                $sql .= " AND styles.id = ". $styles[0]; 
                        }
                        else{
                        for($i = 1; $i<=sizeof($styles);$i++){
                            if($i == 1){
                               $sql .= " AND (styles.id = ". $styles[$i-1]; 
                            }
                            else if($i == sizeof($artists)){
                                $sql .= " OR styles.id = ". $styles[$i-1].")";
                            }
                            else{
                                   $sql .= " OR styles.id = ". $styles[$i-1];
                            }
                        }
                    }

                       
                    }

                       
                        $db = $this->Concert->query($sql);
                        $allconcerts = array();
                        if(!empty($db)){
                             for($i=0;$i<sizeof($db);$i++){
                            $allconcerts[$i]= $this->Concert->find('first', array(
                        'conditions' => array('Concert.id' => $db[$i]['concerts']['id'])
                    ));
                        }
                        }
                        

                        /*Construction du tableau de resultat pour le passer à la vue*/
                       
                 $this->set("allconcerts",$allconcerts);

                 
                }
                  }
                 else{
                $allconcerts = $this->Concert->find("all");
                $this->set("allconcerts",$allconcerts);
            }

        }
            
}
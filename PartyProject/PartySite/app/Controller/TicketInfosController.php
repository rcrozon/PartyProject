<?php
class TicketInfosController extends AppController{

	function admin_editTicket(){
        $id =$this->params['named']['id'];
        if($this->request->is('post')) {
            $d = $this->request->data; 
           
          
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            # On récupère l'image chargée et on la met sur le seveur
            # # # # # # # # # # # # # # # # # # # # # # # # # # # # # #
            $extension = strtolower(pathinfo($d['TicketInfo']['image']['name'], PATHINFO_EXTENSION));
            $uploadFolder = "img/Tickets";
            $uploadPath = WWW_ROOT . $uploadFolder;
            $image = $d['TicketInfo']['image']['name'];
            $image = str_replace(" ", "-", $image);
            $d['TicketInfo']['image']['name'] = $image;
            $full_image_path = $uploadPath . '/' . $d['TicketInfo']['image']['name'];
            if(!empty($d['TicketInfo']['image']['tmp_name']) && 
                in_array($extension, array('jpg', 'jpeg', 'png', 'gif'))) {

                if(move_uploaded_file($d['TicketInfo']['image']['tmp_name'], $full_image_path)) {
                    $d['TicketInfo']['image'] = $d['TicketInfo']['image']['name'];
                }
                else{
                    echo'Sorry we can\'t load this image';
                }
            }
            # # # # # # # # # # # # # # # # # # # # # # # # #
            # Sauvegarde des données dans la base de donnée
            # # # # # # # # # # # # # # # # # # # # # # # # #
            if($this->TicketInfo->save($d,true,array('colorFont','image','coordQrX','coordQrY','coordTextX','coordTextY'))) {
                $this->Session->setFlash("Your party has been well created", "notif", array('type'=>'success'));
                $this->request->data = array();

                $idTicketInfo = $this->TicketInfo->getLastInsertId();
                $data = array('id_ticketInfo' => $idTicketInfo, 'id_concert' => $id[0]);
                if($this->AssocTicket->save($data,true,array('id_ticketInfo','id_concert'))) {
                    $this->Session->setFlash("Success","notif",array('type'=>'success'));

                    $this->redirect(array('controller' => 'Concerts', 'action' => 'admin_table_concert' 
                    ));
                    
                }
                else{
                   $this->Session->setFlash("Error","notif",array('type'=>'error'));
                }
               
        }
    }

}}
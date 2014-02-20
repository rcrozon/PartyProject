<?php
class AssocTarifsController extends AppController{

        function admin_index() {
                $id_concert = $this->request['named']['id_concert'][0];
                //debug($id_concert);
                
                $id_tarif = $this->request['named']['id_tarif'];
                //debug($id_tarif);

                $d = $this->request->data;
                $d['id_concert'] = $id_concert;
                $d['id_tarif'] = $id_tarif;

                if($this->AssocTarif->save($d,true,array('id_concert','id_tarif'))) {
                        $this->Session->setFlash("Your tarif has been well created", "notif", array('type'=>'success'));
                        $this->redirect(array('controller' => 'Tarifs', 'action' => 'addTarif', $id_concert));
                } else {
                        $this->Session->setFlash("Error : Thanks to correct your mistakes", "notif", array('type'=>'error'));
                }
        }
} 
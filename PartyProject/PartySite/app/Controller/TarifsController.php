<?php
class TarifsController extends AppController{

    function admin_addTarif() {
        $idConcert = $this->request['pass'];
        //debug($idConcert);

        if($this->request->is('post')) {
            $d = $this->request->data;

            //$this->Tarif->AssocTarif['id_concert'] = $idConcert;
            //$id_concert = $this->Tarif->AssocTarif['id_concert'];
            //debug($id_concert);

            if($this->Tarif->save($d,true,array('label','price'))) {
                $this->request->data = array();

                $idTarif = $this->Tarif->getLastInsertId();
                debug($idTarif);
                //$data = array($idConcert, $idTarif);
                //$this->render('/AssocTarifs/index');
                $this->redirect(array('controller' => 'AssocTarifs', 'action' => 'index', 
                    'id_concert' => $idConcert, 'id_tarif' => $idTarif));
                
                

                //$this->AssocTarif['id_concert'] = $idConcert;
                //$d['AssocTarif']['id_concert'] = $idConcert;
                //$d['AssocTarif']['id_tarif'] = $this->Tarif->getLastInsertId();
                //debug($d['AssocTarif']['id_concert']);
                //debug($d['AssocTarif']['id_tarif']);
            } else{

            }
        }
    }
}
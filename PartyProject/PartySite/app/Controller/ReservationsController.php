<?php
class ReservationsController extends AppController{

	function listReservations(){
		$idClient = $this->params['named']['id'];  		
		$idConcert = $this->params['named']['idC'];  

		$d = $this->Reservation->find('all',array('conditions' => array(
			'Reservation.id_concert' => $idConcert,
			'Reservation.id_client' => $idClient)));		      
		$this->set('reservations',$d);

		
	}

	function listMyAllReservations(){
		$idClient = AuthComponent::user('id');  		
		

		$d = $this->Reservation->find('all',array('conditions' => array(
			'Reservation.id_client' => $idClient)));		      
		$this->set('reservations',$d);

		
	}

	 function create_pdf(){
 

	$idReservation = $this->params['named']['id'];  
	$reservation = $this->Reservation->find('first',array(
		'conditions' => array('Reservation.id' => $idReservation)
		));	
	$idTarif = $reservation['Reservation']['id_tarif'];
	$tarif = $this->Tarif->find('first',array(
		'conditions' => array('Tarif.id' => $idTarif)
		));	
	$idConcert = $reservation['Reservation']['id_concert'];
	$concert = $this->Concert->find('first',array(
		'conditions' => array('Concert.id' => $idConcert)
		));	
	$idClient = $reservation['Reservation']['id_client'];
	$client = $this->Client->find('first',array(
		'conditions' => array('Client.id' => $idClient)
		));	
	$assocTicket = $this->AssocTicket->find('first',array(
		'conditions' => array('AssocTicket.id_concert' => $idConcert)
		));	
	$ticketInfo = $this->TicketInfo->find('first',array(
		'conditions' => array('TicketInfo.id' => $assocTicket['AssocTicket']['id_ticketInfo'])
		));	
		$this->set('ticketInfo',$ticketInfo);
		$this->set('reservation',$reservation);
		$this->set('client',$client);
 		$this->set('tarif',$tarif);
 		$this->set('concert',$concert);


 
 
    $this->render('/Pdf/my_pdf_view');
 
}

	function addReservations (){
		if($this->request->is('post')) {
			$resultPost = $this->request->data; 
			$idConcert = $this->params['named']['idC']; 

			

        //Get id of Tarif
			$v = $this->AssocTarif->find('all',array('conditions' => array('AssocTarif.id_concert' => $idConcert)));		      
        //Parcours des tarifs
			for ($i = 0; $i <= sizeof($v)-1; $i++) {
				$idT= $v[$i]['AssocTarif']['id_tarif'];
				$quantite = $resultPost [$idT];

			//Ajout des reservations

				for ($j = 0; $j <= $quantite-1;$j++){
				$table['id_client'] = $userId = $this->Auth->user('id');

				$table['id_concert'] = $idConcert;
				$table['id_tarif'] = $idT;
				$table['scan'] = 0;

				$this->Reservation->create();

				if($this->Reservation->save($table,false,array('id_client','id_concert','id_tarif','scan')))
				{
				$this->Session->setFlash("success","notif",array('type'=>'success'));

				}
				else {
					
				}

			}
		}
		$this->redirect('listReservations/id:'.$table['id_client'].'/idC:'.$idConcert);

	}


	$id = $this->params['named']['idC'];   
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
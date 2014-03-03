<?php
class ConcertHelper extends AppHelper {
	function getConcertByID($id)
	{
		App::import("Model", "Concert");  

		$model = new Concert();  
		$test  = array();
		$test = $model->find('first',array(
			'conditions' => array('Concert.id' => $id)
			)); 


		return $test;
	}

	function getNbDispoSeats($id)
	{
		App::import("Model", "Concert");
		App::import("Model", "Reservation");  
  
		$modelReservation = new Reservation();  
		$modelConcert = new Concert();  
		$countReservation = $modelReservation->find('count',array(
			'conditions' => array('Reservation.id_concert' => $id)
			)); 
		$concert = $modelConcert->find('first',array(
			'conditions' => array('Concert.id' => $id)
			)); 
		$nbSeatsDispo =	$concert['Concert']['nb_seats'] - $countReservation;


		return $nbSeatsDispo;
	}

}
<?php
class MobilesController  extends AppController{


	function getAllConcerts(){
		$results = $this->Concert->find('all');

		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['Concert'];
			$table[$i] = $d;
		}
		return new CakeResponse(array('body' => json_encode($table)));

	}

	 function getAllReservations(){
	 	$results = $this->Reservation->find('all');

		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['Reservation'];
			$table[$i] = $d;
		}
		return new CakeResponse(array('body' => json_encode($table)));

	}

	function getTariffByID(){
	 	$login = $this->params['named']['id'];
		
        $results = $this->Tarif->find('first',array(
            'conditions' => array('Tarif.id' => $login)));
		
			$d = $results['Tarif'];
			$table = $d;
	
		return new CakeResponse(array('body' => json_encode($table)));

	}

		function getAllTariffs(){
	 $results = $this->Tarif->find('all');

		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['Tarif'];
			$table[$i] = $d;
		}
		return new CakeResponse(array('body' => json_encode($table)));

	}


	public function getConcertByID() {
		$login = $this->params['named']['id'];
		
        $results = $this->Concert->find('first',array(
            'conditions' => array('Concert.id' => $login)));
		
			$d = $results['Concert'];
			$table = $d;
	
		return new CakeResponse(array('body' => json_encode($table)));

    }

    public function getClientByName() {
		


		$login = $this->params['named']['login'];

        $results = $this->Concert->find('first',array(
            'conditions' => array('Client.username' => $login)));
		
			$d = $results['Concert'];
			$table = $d;
	
		return new CakeResponse(array('body' => json_encode($table)));






    }
    public function getAllClients() {
    	$results = $this->Client->find('all');
    	
		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['Client'];
			$table[$i] = $d;
		}
		return new CakeResponse(array('body' => json_encode($table)));

    }

     public function postClient() {
	$this->autoRender = false;
    $this->response->type('json');
    $message = null;
    $data=$this->request->data['json'];

    



   return new CakeResponse(array('body' => json_encode($data)));    

    }
     public function decodejs() {
//	    $message=$this->request->data['json'];

    $message = '{"Client":{"id":"1","username":"anthos","mail":"anthony.gueguenous@laposte.net","password":"f636ba068d303281a78662a4059229817c06a125","first_name":"anthony","last_name":"gueguenou","admin":"0","created":"2014-02-19 17:43:17"}}
';

	$message = json_decode($message);
	debug ($message);

	if($this->Client->save($message,false)){
		echo'success';
	}
	else{
		echo 'error';
	}	

    }

}
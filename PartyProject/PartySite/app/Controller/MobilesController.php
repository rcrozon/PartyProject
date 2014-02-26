<?php
class MobilesController  extends AppController{

	function getAllConcerts(){
		$results = $this->Concert->find('all');
		return new CakeResponse(array('body' => json_encode($results)));

	}

	public function getConcertByID() {
		$id = $this->params['named']['id'];
		
        $results = $this->Concert->findById($id);
        return new CakeResponse(array('body' => json_encode($results)));
    }

    public function getClientByName() {
		$login = $this->params['named']['login'];
		
        $results = $this->Client->find('first',array(
            'conditions' => array('Client.username' => $login)));
        return new CakeResponse(array('body' => json_encode($results)));
    }
    public function getAllClients() {
		$results = $this->Client->find('all');
		return new CakeResponse(array('body' => json_encode($results)));
    }

    function getAllReservations(){
		$results = $this->Reservation->find('all');
		return new CakeResponse(array('body' => json_encode($results)));

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
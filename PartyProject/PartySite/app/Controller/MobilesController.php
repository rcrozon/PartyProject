<?php
require('../webroot/encrypt/encrypt.php');
class MobilesController  extends AppController{


	function getAllConcerts(){
		$results = $this->Concert->find('all',array(
            'conditions' => array('Concert.online' => 1)));
			 	$table=array();




		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['Concert'];
			$table[$i] = $d;
		}
		$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));



	}

	 function getAllReservations(){
	 	$results = $this->Reservation->find('all');
	 	$table=array();
		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['Reservation'];
			$table[$i] = $d;
		}
		$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));

	}

	 function test(){

	$message = json_decode('[{"username":"test","password":"D3AE539C514DC101518CF664EA5F25BB"}]',true);
	$username =	$message[0]['username'];
	$password = $message[0]['password'];
		
	debug($username);
	debug($message);
	$mcrypt = new MCrypt();
	$decrypted = $mcrypt->decrypt($message[0]['password']);

	$results = $this->Client->find('first',array(
            'conditions' => array('Client.username' => $username)));
	if ($results == null){
		return new CakeResponse(array('body' => json_encode('Invalid Login'))); 
	}
	$decryptedPassword = $mcrypt->decrypt($results['Client']['password']);
	debug($decryptedPassword);
	debug($decrypted);

	if (strcmp ($decryptedPassword ,$decrypted)){
		$d = $results['Client'];
		$table = json_encode($d);
		$table = '['.$table.']';
		$mcrypt = new MCrypt();
		
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));
	}
	else{

		return new CakeResponse(array('body' => json_encode('Invalid password'))); 

	}
  }    

	

	function getReservationsByCLient(){
		$idClient = $this->params['named']['id'];
		$table=array();
		$results = $this->Reservation->find('all',array(
            'conditions' => array('Reservation.id_client' => $idClient)));
		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['Reservation'];
			$table[$i] = $d;
		}
		$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));

	}

	function getAllAssocTarifs(){
	 	$results = $this->AssocTarif->find('all');
	 	$table=array();
		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['AssocTarif'];
			$table[$i] = $d;
		}
		$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));

	}
	function getAllArtists(){
	 	$results = $this->Artist->find('all');
	 	$table=array();
		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['Artist'];
			$table[$i] = $d;
		}
		$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));
	}

	function getAllStyles(){
	 	$results = $this->Style->find('all');
	 	$table=array();
		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['Style'];
			$table[$i] = $d;
		}
		$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));

	}

	function getAllAssocStyles(){
	 	$results = $this->AssocStyle->find('all');
	 	$table=array();
		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['AssocStyle'];
			$table[$i] = $d;
		}
		$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));

	}

	function getAllAssocArtists(){
	 	$results = $this->AssocArtist->find('all');
	 	$table=array();
		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['AssocArtist'];
			$table[$i] = $d;
		}
	$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));

	}
	function getTariffByID(){
	 	$login = $this->params['named']['id'];
		
        $results = $this->Tarif->find('first',array(
            'conditions' => array('Tarif.id' => $login)));
		
			$d = $results['Tarif'];
			$table = $d;
	
	$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));

	}

		function getAllTariffs(){
	 $results = $this->Tarif->find('all');
	 	$table=array();

		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['Tarif'];
			$table[$i] = $d;
		}
		$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));

	}


	public function getConcertByID() {
		$login = $this->params['named']['id'];
		
        $results = $this->Concert->find('first',array(
            'conditions' => array('Concert.id' => $login)));
		
			$d = $results['Concert'];
			$table = $d;
	
	$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));

    }

    public function getClientByName() {
		


		$login = $this->params['named']['login'];

        $results = $this->Concert->find('first',array(
            'conditions' => array('Client.username' => $login)));
		
			$d = $results['Concert'];
			$table = $d;
	
	$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));






    }
    public function getAllClients() {
    	$results = $this->Client->find('all');
    		 	$table=array();
		for($i=0;$i<sizeof($results);$i++){
			$d = $results[$i]['Client'];
			$table[$i] = $d;
		}
		$mcrypt = new MCrypt();
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));

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
   
   public function majReservation() {

	$data = $this->request->data['json'];	
	$message = json_decode($data);

	$log =  array();
    for ($i = 0; $i < sizeof($message); $i++) {
    	
		if($this->Reservation->save($message[$i],false)){
			$log[$i] = $i;
			
		} 	
		else{

		return new CakeResponse(array('body' => json_encode($log))); 
		}
	
	}
		$log[0]='success';
		return new CakeResponse(array('body' => $data)); 

}

	public function login(){

	$data = $this->request->data['json'];

	$message = json_decode($data,true);
	$username =	$message[0]['username'];
	$password = $message[0]['password'];
	$mcrypt = new MCrypt();
	$decrypted = $mcrypt->decrypt($message[0]['password']);

	$results = $this->Client->find('first',array(
            'conditions' => array('Client.username' => $username)));
	if ($results == null){
		return new CakeResponse(array('body' => json_encode('Invalid Login'))); 
	}
	$decryptedPassword = $mcrypt->decrypt($results['Client']['password']);
	
	if ($decryptedPassword == $decrypted){
		$d = $results['Client'];
		$table = json_encode($d);
		$table = '['.$table.']';
		$mcrypt = new MCrypt();
		
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));
	}
	else{

		return new CakeResponse(array('body' => json_encode('Invalid password'))); 

	}
  } 

  	public function loginWindows(){

	$data = $this->request->data['json'];
	echo "DAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAATTTTTTTTTTTTTTTTTTTTTTTAAAAAAAAAAAAAAAAAAAA";
	debug($data);
	return new CakeResponse(array('body' => json_decode($data,true)));
	/*$message = json_decode($data,true);
	$username =	$message[0]['username'];
	$password = $message[0]['password'];
	$mcrypt = new MCrypt();
	$decrypted = $mcrypt->decrypt($message[0]['password']);

	$results = $this->Client->find('first',array(
            'conditions' => array('Client.username' => $username)));
	if ($results == null){
		return new CakeResponse(array('body' => json_encode('Invalid Login'))); 
	}
	$decryptedPassword = $mcrypt->decrypt($results['Client']['password']);
	
	if (strcmp($decryptedPassword,$decrypted)){
		$d = $results['Client'];
		$table = json_encode($d);
		$table = '['.$table.']';
		$mcrypt = new MCrypt();
		
		$encrypted = $mcrypt->encrypt(json_encode($table));
		return new CakeResponse(array('body' =>($encrypted) ));
	}
	else{

		return new CakeResponse(array('body' => json_encode('Invalid password'))); 

	}*/
  }   

}
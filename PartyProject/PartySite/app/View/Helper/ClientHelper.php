<?php
class ClientHelper extends AppHelper {
	function getClientByID($id)
	{
		App::import("Model", "Client");  

		$model = new Client();  
		$test = $model->find('first',array(
			'conditions' => array('Client.id' => $id)
			)); 


		return $test;
	}
}
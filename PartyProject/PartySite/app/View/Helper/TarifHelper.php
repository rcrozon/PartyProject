<?php
class TarifHelper extends AppHelper {
	function getTarifByID($id)
	{
		App::import("Model", "Tarif");  

		$model = new Tarif();  
		$test  = array();
		$test = $model->find('first',array(
			'conditions' => array('Tarif.id' => $id)
			)); 


		return $test;
	}
}
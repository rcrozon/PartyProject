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
}
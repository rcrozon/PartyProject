

      <?php
class StyleHelper extends AppHelper {
	function getStylesByIDConcert($id)
	{
		App::import("Model", array("AssocStyle","Style"));  
$AssocStyle = new AssocStyle();  
		$Style = new Style();  
		  $results = $AssocStyle->find('all',array(
                    'conditions' => array('AssocStyle.id_concert' => $id)));
                for ($i = 0; $i < sizeof($results); $i++) {
                    $styles[$i] = $Style->find('first',array('conditions' => array('Style.id' => 
                        $results[$i]['AssocStyle'])));
                }

		return $styles;
	}
}
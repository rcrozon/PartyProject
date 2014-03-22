      <?php
class ArtistHelper extends AppHelper {
	function getArtistsByIDConcert($id)
	{
		App::import("Model", array("AssocArtist","Artist"));  
$AssocArtist = new AssocArtist();  
		$Artist = new Artist();  
		  $results = $AssocArtist->find('all',array(
                    'conditions' => array('AssocArtist.id_concert' => $id)));
                for ($i = 0; $i < sizeof($results); $i++) {
                    
                    $artists[$i] = $Artist->find('first',array('conditions' => array('Artist.id' => 
                        $results[$i]['AssocArtist']['id_artist'])));
                }
                
		return $artists;
	}
}
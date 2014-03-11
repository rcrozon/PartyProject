   <?php 
  function getDateOfDateTime($str) {
			$date = $str;
			$annee = substr($date, 0, 4); 
 			$mois = substr($date, 5, 2); 
 			$jour = substr($date, 8, 2);  
 			$date = $annee . '/' . $mois . '/' . $jour; 
			return $date;
			}
			 function getTimeOfDateTime($str) {
		$time = $str;
		    $heure = substr($time, 11, 2); 
 			$minute = substr($time, 14, 2); 
 			$time = $heure . 'h' . $minute; 
			return $time;
			}
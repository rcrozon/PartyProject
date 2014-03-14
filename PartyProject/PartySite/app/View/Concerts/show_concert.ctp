

<?php
require('../webroot/date/dateConverter.php');

?>

	
<style type="text/css">
		body{
			background:url('<?php echo $this->webroot.'img/Concerts/'.$showConcert['image']; ?>') no-repeat center center fixed; 
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  background-position-y: 51px

}

h1{
	 border-bottom: 3px solid #f0f1e7;
    color: #444;
    font-family: "Marvel", sans-serif;
    
    font-weight: bold;
    line-height: 48px;
    margin: 0 0 15px;
    padding: 0 0 5px;
    text-transform: uppercase;

   
		font-size: 22px;
		line-height: 40px;
		letter-spacing: -1px;color: #444;

}
p{
   font-family: Arial,sans-serif;
	padding: 0 0 0 0px;
color: #666;
font-size: 13px;
}

.detailConcert{
	 background: #fff;
    border-radius: 2px;
    border: 1px solid #ccc;
    padding:15px;
    padding-bottom: 15px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, .1);
}
.plan-price {

margin:auto;
width: 90px;
height: 90px;
line-height: 90px;
font-size: 19px;
font-weight: bold;
color: white;
background: #3276b1;
border-radius: 45px;
text-align: center;
}
		</style>

		<div class="row">  

			
			<div class="col-md-5" style="float:right;">
		<div class="detailConcert">
			<?php
			$nbSeatsDispo = $this->Concert->getNbDispoSeats($showConcert['id']);

            $dateBegin = getDateOfDateTime($showConcert['start_datetime']);

            $dateEnd = getDateOfDateTime($showConcert['end_datetime']);


       		$timeBegin = getTimeOfDateTime($showConcert['start_datetime']);
		    $timeEnd  = getTimeOfDateTime($showConcert['end_datetime']);
 			
			echo '<h1>'.$showConcert['name_concert'].'</h1>';
			echo '<p>'.$dateBegin.' - '.$timeBegin.' to '.$dateEnd.' - '. $timeEnd.' <p>';

			echo '<p> Style(s): ';
			for($i=0;$i<sizeof($styles);$i++){
				if($i==sizeof($styles)-1){
					echo $styles[$i]['Style']['name'];
				}
				else{
						echo $styles[$i]['Style']['name'].', ';
				}
		
			}
			echo '</p>';

			echo '<p> Artist(s): ';
			for($i=0;$i<sizeof($artists);$i++){
				if($i==sizeof($artists)-1){
					echo $artists[$i]['Artist']['name'];
				}
				else{
						echo $artists[$i]['Artist']['name'].', ';
				}
		
			}
			echo '</p>';

			echo '<p>'.'Location: '.$showConcert['location'].'<p>';
			if($nbSeatsDispo > 0){
			echo '<span class="label-success">Tickets available : '.$nbSeatsDispo.'</span>';
			}
			else{
				
				echo '<span class="label-important">Tickets not available</span>';
			}
			echo "<div class='row'>";  
			
			echo "<div class=\"col-md-8\">";
			echo '<p>'.'Tarif: <p>';

			echo "<select  name=\"select\" style='margin-top:0px;' class=\"form-control\">";

			//Affichage des tarifs
			 for ($i = 0; $i <= sizeof($showTarif)-1; $i++) {              
            echo "<option value=\"".$showTarif[$i][0]['Tarif']['price']."\">".$showTarif[$i][0]['Tarif']['label']."</option>";
            }
            echo "</select>";
            echo "</div>";
            echo "<div class=\"col-md-3\">";

 			echo"<p id=\"price\" class='plan-price'>"; 			  
            echo"</p>";
                        			echo "</div>";
						if($nbSeatsDispo > 0){


			?>
<?php if(AuthComponent::user('id')): ?>

        <a  href="<?php echo $this->Html->url(array(
    				"controller" => "reservations",
  					"action" => "addReservations",
  					"idC"=>$showConcert['id']    			
					));?>" role="button" style="margin-left:15px; margin-top:5px;" class="btn btn-primary">Go to Ticket Office</a>
					
					 <?php else: ?>

					  	

		</div>
		<div class="alert alert-info" style="margin-top:10px;">You must be connected to access Ticket Office.</div>
					
<?php endif; ?>
	</div>
					<?php }
					
					 ?>
  	

	



		</div>
	<script>
	

$( "select" )
  .change(function () {
    var str = $( "select" ).val() + " €";
    
    $( "#price" ).text( str );
  })
  .change();
</script>
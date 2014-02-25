<?php

/*echo $this->Html->image('Concerts/'.$showConcert['image'], array('fullBase' => false));
 echo $this->Html->link($this->Html->image('Concerts/'.$showConcert['image'],array('alt'=>'Logo')),'/', array('fullBase'=>true)); 



echo $this->Html->url($this->Html->image('Concerts/'.$showConcert['image'],array('alt'=>'Logo')),'/', array('fullBase'=>true));*/
					



?>

	
<style type="text/css">
		body{
background-image:url('<?php echo $this->webroot.'img/Concerts/'.$showConcert['image']; ?>');
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
			echo '<h1>'.$showConcert['name_concert'].'</h1>';
			echo '<p>'.'Begin date: '.$showConcert['start_datetime'].'<p>';
			echo '<p>'.'End date: '.$showConcert['end_datetime'].'<p>';
			echo '<p>'.'Location: '.$showConcert['location'].'<p>';
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

			?>

        <a  href="<?php echo $this->Html->url(array(
    				"controller" => "reservations",
  					"action" => "addReservations",
  					"idC"=>$showConcert['id']    			
					));?>" role="button" style="margin-left:15px; margin-top:5px;" class="btn btn-primary">Accéder à la billeterie</a>
  	

		</div>



		</div>
	<script>
	

$( "select" )
  .change(function () {
    var str = $( "select" ).val() + " €";
    
    $( "#price" ).text( str );
  })
  .change();
</script>
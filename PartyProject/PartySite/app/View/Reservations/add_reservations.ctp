<style type="text/css">
		body{
	background:url('<?php echo $this->webroot.'img/Concerts/'.$showConcert['image']; ?>') no-repeat center center fixed; 
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
  background-position-y: 51px
}
input[type=number], input[type=password] {
	margin:0px;
	height:25px;
	width:50px;
	}
.row {
margin-left: 0px;
margin-right: 0px;
margin-bottom: 8px;

}
.msg-noplace{
	margin-top: 50px;
}
.label-success, p.notif {
margin-bottom: 5px;
padding: 2px 4px;
font-size: 11.844px;
font-weight: bold;
line-height: 14px;
color: #ffffff;
text-shadow: 0 -1px 0 rgba(0, 0, 0, 0.25);
white-space: nowrap;
vertical-align: baseline;
-webkit-border-radius: 3px;
-moz-border-radius: 3px;
border-radius: 3px;
margin: 0;
background-color: #A72020;}

input[type=submit]:hover  {
color: #fff;
background-color: #3276b1;
border-color: #285e8e;
}
input[type=submit] {
color: #fff;
background-color: #428bca;
border-color: #357ebd;
}
input[type=submit] {
display: inline-block;
margin-bottom: 0;
font-weight: 400;
text-align: center;
vertical-align: middle;
cursor: pointer;
background-image: none;
border: 1px solid transparent;
white-space: nowrap;
padding: 6px 12px;
font-size: 14px;
line-height: 1.42857143;
border-radius: 4px;
-webkit-user-select: none;
-moz-user-select: none;
-ms-user-select: none;
user-select: none;
}

	.box{
		padding: 5px;
		width:50%;
		margin:auto;
	 background: #fff;
    border-radius: 2px;
    border: 1px solid #ccc;
    padding-bottom: 15px;
    box-shadow: 0 1px 2px rgba(0, 0, 0, .1);
}
.event_fiche{
	font-family: Arial,sans-serif;
	padding: 0 0 0 120px;
color: #666;
font-size: 12px;
}
.multi_header{
	position: relative;
border: 1px solid #eee;
padding: 10px;
height:120px;
}
.multi_header img {
width: 100px;
height: 100px;
float: left;
}
.multi_header .event_fiche h1 {
font-size: 14px;
margin: 0;
color: #333;
text-transform: uppercase;
}


.instruction{
	background-color:#303433;
	height:75px;
	margin-bottom: 20px;
	padding:5px;
}

.instruction_number {
	float:left;
width: 65px;
height: 65px;
line-height: 65px;
font-size: 25px;
font-weight: bold;
color: white;
background: #696E6D;
border-radius: 45px;
text-align: center;
margin-right: 10px;
}


form {
color: #131212;
}
</style>
<div class="box">


<div class="multi_header"><img src="<?php echo $this->webroot.'img/Concerts/'.$showConcert['image']; ?>"><div <div class="event_fiche">
                <div class="event_titre"><h1><?php echo $showConcert['name_concert']?></h1></div>
                <div class="event_date"><?php echo $showConcert['start_datetime']?> au <?php echo $showConcert['end_datetime']?></div>
                <div class="event_info"><?php echo $showConcert['location']?><br></div></div></div>





<div class="row">
	<div class="col-md-3">

		<span class="TarifTitle" style="width:100px;">Tarif Name</span>
	</div>
	<div class="col-md-3">

		<span class="priceTitle">Price</span>
	</div>
	<div class="col-md-3">

		<span class="quantityTitle">Quantity</span>
	</div>
	<div class="col-md-3">

		<span class="subtotalTitle">Total Price</span>
	</div>

</div> 

<?php
echo $this->Form->create('Reservations', array('type' => 'file')); 

for ($i = 0; $i <= sizeof($showTarif)-1; $i++) {  ?>            
<div class="row">
	<div class="col-md-3">

		<span class="Tarif" style="width:100px;"> <?php  echo $showTarif[$i][0]['Tarif']['label']; ?></span>
	</div>
	<div class="col-md-3">
		<span class="price" id="<?php echo "price_".$i?>"><?php  echo $showTarif[$i][0]['Tarif']['price']; ?> </span>
	</div>
	<div class="col-md-3">

		<span class="quantity" ><input type="number" value=0  min=0 max=15 id="<?php echo "quantity_".$i?>" name=<?php echo "\"".$showTarif[$i][0]['Tarif']['id']."\"";?>></span>
	</div>
	<div class="col-md-3">
		<span class="subtotal" id="<?php echo "subtotal_".$i?>"></span>
	</div>


</div>


<?php }  


?>            
<div class="col-md-3">		


<span>Total :</span>	<span class="label label-default" id="total">0 €</span>
	</div>
<div class="msg-noplace" id="msg-noplace"></div>

<div class="row" id="validateReservation">
	           

	<?php echo $this->Form->end("Validate"); ?>

</div>
</div>
<script>


<?php for ($i = 0; $i <= sizeof($showTarif)-1; $i++){ ?>


	$( <?php echo "\"#quantity_".$i."\"" ?> )
	.change(function () {
		var totalQuantity=0;
		
		document.getElementById("msg-noplace").innerHTML = "No seats";
		for (i = 0; i < <?php echo sizeof($showTarif); ?>; i++) 
		{ 
			   
		 var nameInput = "quantity_"+i;
   		 totalQuantity =  parseInt(totalQuantity)+	parseInt(document.getElementById(nameInput).value);

		}
		if(totalQuantity > <?php echo $this->Concert->getNbDispoSeats($showConcert['id']);?>)
		{

		document.getElementById("msg-noplace").innerHTML = "<p class=\"notif\" style=\"background-color=#A72020;\">Not enough availability <a href=\"\" class=\"close\" onclick=\"$(this).parent().parent().slideUp()\">x</a></p>";
		document.getElementById("validateReservation").innerHTML =" ";
	}
		else{
		document.getElementById("validateReservation").innerHTML = '<?php echo $this->Form->button("Validate", array("class" => "btn btn-primary","style"=>"margin-top:50px;margin-left:40%"));echo $this->Form->end(); ?>';
		document.getElementById("msg-noplace").innerHTML =" ";
		;
		}




	
		var price = document.getElementById(<?php echo "\"price_".$i."\"" ?>).innerHTML;
		var quantity = document.getElementById(<?php echo "\"quantity_".$i."\"" ?>).value;
		document.getElementById(<?php echo "\"subtotal_".$i."\"" ?>).innerHTML = price * quantity;
		document.getElementById("total").innerHTML = 
		<?php for ($j = 0; $j <= sizeof($showTarif)-1; $j++){ ?>
		 parseFloat(document.getElementById(<?php echo "\"subtotal_".$j."\"" ?>).innerHTML)
		<?php 
		if ($j<sizeof($showTarif)-1){
			echo "+";
		}
		else {
			echo ";";
		}
			} ?>
		document.getElementById("total").innerHTML = document.getElementById("total").innerHTML + " €";
})

	.change();
	<?php } ?>





	</script>
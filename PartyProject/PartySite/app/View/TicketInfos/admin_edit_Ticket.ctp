	<style>
	.tableQR td, th {
	border: thin solid #6495ed;
	background-color:#DAE2E1;
	color:#242727;
	}
	.tableQR td, th:hover {
		color:#242727;
	border: thin solid #6495ed;
	background-color:#5AA4F9;
	margin:auto;

	}


	</style>




		<?php echo $this->Form->create('TicketInfo', array('type' => 'file')); ?> 
		<table>
			
	<?php echo $this->Form->input('image', array('type' => 'file', 'label'=>"Background image : ",
					'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
	<TABLE id="tableQR" BORDER="1" style="width:480px;height:168px"> 
	  <CAPTION> Sélectionnez l'emplacement du QRCode sur le ticket </CAPTION> 
	  <?php
	  for($i=1;$i<=3;$i++){
	  	echo ' <TR> ';
	  	for($j=1;$j<=8;$j++){
	  		echo '<TH>'.$i.':'.$j.'</TH>';
	  	}
	  	echo '  </TR> ';
	  }
	 
	 ?>
	</TABLE> 

			<?php echo $this->Form->input('coordQrX',array('label'=>"QR Code Position X : ",
			    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'id' => 'QRposX')); ?>
			<?php echo $this->Form->input('coordQrY',array('label'=>"QR Code Position Y : ",
			    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'id' => 'QRposY')); ?>

		<div class="tableText">

	<TABLE id="tableText" BORDER="1" style="width:480px;height:168px"> 
	  <CAPTION> Sélectionnez l'emplacement du QRCode sur le ticket </CAPTION> 
	  <?php
	  for($i=1;$i<=1;$i++){
	  	echo ' <TR> ';
	  	for($j=1;$j<=3;$j++){
	  		echo '<TH>'.$i.':'.$j.'</TH>';
	  	}
	  	echo '  </TR> ';
	  }
	 
	 ?>
	</TABLE> 
	</div>
	<?php echo $this->Form->input('coordTextX',array('label'=>"Text Code Position X : ",
			    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'id' => 'TextposX')); ?>
			<?php echo $this->Form->input('coordTextY',array('label'=>"Text Code Position Y : ",
			    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'id' => 'TextposY')); ?>

			    <?php echo $this->Form->input('colorFont',array('label'=>"Text Color : ",
			    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>','type'=>'color', 'id' => 'TextColor')); ?>
			
			<tr><td><?php echo $this->Form->button("Validate", array('class' => 'btn btn-primary')); ?></td></tr>
		</table>


	<script>
	$('#tableQR').click(function(e){
		
		
		var elem = $(e.target).text().split(':');
		var y = elem[0];
		var x = elem[1];
	 	document.getElementById("QRposX").value= parseInt(x);
	 	document.getElementById("QRposY").value= parseInt(y);


	//	document.getElementById("pos").value = 2;
	   // alert($(e.target).text());    
	})

	$('#tableText').click(function(e){
		
		
		var elem = $(e.target).text().split(':');
		var y = elem[0];
		var x = elem[1];
	 	document.getElementById("TextposX").value= parseInt(x);
	 	document.getElementById("TextposY").value= parseInt(y);

	 

	//	document.getElementById("pos").value = 2;
	   // alert($(e.target).text());    
	})
	</script>


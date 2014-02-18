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
		</style>

		<div class="row">  
			
			<div class="col-md-5" style="float:right;">
		<div class="detailConcert">
			<?php
			echo '<h1>'.$showConcert['name_concert'].'</h1>';
			echo '<p>'.'Begin date: '.$showConcert['start_datetime'].'<p>';
			echo '<p>'.'End date: '.$showConcert['end_datetime'].'<p>';
			echo '<p>'.'Location: '.$showConcert['location'].'<p>';
			?>


					</div>

		</div>




		</div>
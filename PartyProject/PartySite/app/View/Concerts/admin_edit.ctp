<div id="page-wrapper">        
	<div class="page-header">
		<h1>Edit Party : '<?php echo $partyName; ?>'</h1>
	</div>

	<table>
		<?php echo $this->Form->create('Concert', array('type' => 'file'));?>
  			<?php echo $this->Form->input('name_concert',array('label' => "Party name :", 
  				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
			<?php echo $this->Form->input('location',array('label'=>"Scene : ", 
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
			<?php echo $this->Form->input('nb_seats',array('label'=>"Number of seats : ", 
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
			<?php echo $this->Form->input('image_file', array('type' => 'file', 'label'=>"Background image : ", 
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
			<?php echo $this->Form->input('start_datetime', array('type' => 'datetime', 'label'=>"Start date and hour : ", 
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
  			<?php echo $this->Form->input('end_datetime', array('type' => 'datetime', 'label'=>"End date and hour : ", 
  				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
  			<?php echo $this->Form->input('full', array('type' => 'checkbox', 'label'=>"Is Full ? ", 
  				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
  			<?php echo $this->Form->input('online', array('type' => 'checkbox', 'label'=>"Is Online ? ", 
  				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
  			<?php echo $this->Form->input('id');?>
		<tr><td><?php echo $this->Form->end("Update my party");?></td></tr>
	</table>

</div>
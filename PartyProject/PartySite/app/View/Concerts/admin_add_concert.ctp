<div id="page-wrapper">        
	<div class="page-header">
		<h1>Add Concert</h1>
	</div>

	<?php echo $this->Form->create('Concert', array('type' => 'file')); ?>
	  <?php echo $this->Form->input('name_concert',array('label'=>"Party name : ")); ?>
		<?php echo $this->Form->input('location',array('label'=>"Scene : ")); ?>
		<?php echo $this->Form->input('nb_seats',array('label'=>"Numbrer of seats : ")); ?>
		<?php echo $this->Form->input('image_file', array('type' => 'file', 'label'=>"Background image : ")); ?>
		<?php echo $this->Form->input('start_datetime', array('type' => 'datetime', 'label'=>"Start date and hour : ")); ?>
	  <?php echo $this->Form->input('end_datetime', array('type' => 'datetime', 'label'=>"End date and hour : ")); ?>
	<?php echo $this->Form->end("Create my party"); ?>
</div>

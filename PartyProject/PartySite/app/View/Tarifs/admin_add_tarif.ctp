<div id="page-wrapper">        
	<div class="page-header">
		<h1>Add Tariffs</h1>
	</div>

	<?php echo $this->Form->create('Tarif'); ?>
		<?php echo $this->Form->input('label',array('label'=>"Label : ")); ?>
		<?php echo $this->Form->input('price',array('label'=>"Price : ")); ?>
	<?php echo $this->Form->end("Add tarif"); ?>
</div>
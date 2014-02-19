<h1>Add tarifs</h1>

<?php echo $this->Form->create('Tarif'); ?>
	<?php echo $this->Form->input('label',array('label'=>"Label : ")); ?>
	<?php echo $this->Form->input('price',array('label'=>"Price : ")); ?>
<?php echo $this->Form->end("Add tarif"); ?>
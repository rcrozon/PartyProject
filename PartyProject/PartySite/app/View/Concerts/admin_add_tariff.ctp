<div id="page-wrapper">        
	<div class="page-header">
		<h1>Add Artist to Party '<?php echo $partyName; ?>'</h1>
	</div>

	<table>
		<?php echo $this->Form->create('Tarif'); ?>
		  	<?php echo $this->Form->input('label',array('label'=>"Label : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('price',array('label'=>"Price : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<tr><td><?php echo $this->Form->button("Add tariff", array('class' => 'btn btn-primary')); ?></td></tr>
		<?php echo $this->Form->end(); ?>
	</table>
</div>

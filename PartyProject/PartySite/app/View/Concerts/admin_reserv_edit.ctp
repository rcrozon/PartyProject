<div id="page-wrapper">        
	<div class="page-header">
		<h1>Edit Reservation</h1>
	</div>

	<table>
		<?php echo $this->Form->create('Reservation'); ?>
			<?php echo $this->Form->input('id_client',array('label'=>"ID Client : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('id_concert',array('label'=>"ID Concert : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('id_tarif',array('label'=>"ID Tarif : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('scan',array('label'=>"Scan ? ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('id');?>
		<tr><td><?php echo $this->Form->button("Update Reservation", array('class' => 'btn btn-primary')); ?></td></tr>
		<?php echo $this->Form->end(); ?>
	</table>
</div>
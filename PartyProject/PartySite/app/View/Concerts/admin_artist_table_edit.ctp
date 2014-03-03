<div id="page-wrapper">        
	<div class="page-header">
		<h1>Edit Artist</h1>
	</div>

	<table>
		<?php echo $this->Form->create('Artist'); ?>
			<?php echo $this->Form->input('name',array('label'=>"Name : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('id');?>
		<tr><td><?php echo $this->Form->button("Update Artist", array('class' => 'btn btn-primary')); ?></td></tr>
		<?php echo $this->Form->end(); ?>
	</table>
</div>
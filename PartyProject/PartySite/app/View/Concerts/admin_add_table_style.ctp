<div id="page-wrapper">        
	<div class="page-header">
		<h1>Add Style</h1>
	</div>

	<table>
		<?php echo $this->Form->create('Style'); ?>
		  	<?php echo $this->Form->input('name',array('label'=>"Name : ",
		  		'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<tr><td><?php echo $this->Form->button("Add artist", array('class' => 'btn btn-primary')); ?></td></tr>
		<?php echo $this->Form->end(); ?>
	</table>
</div>

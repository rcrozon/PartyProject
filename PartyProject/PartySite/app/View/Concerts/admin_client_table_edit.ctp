<div id="page-wrapper">        
	<div class="page-header">
		<h1>Edit Client</h1>
	</div>

	<table>
		<?php echo $this->Form->create('Client'); ?>
			<?php echo $this->Form->input('username',array('label'=>"Username : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('mail',array('label'=>"Mail : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('first_name',array('label'=>"FirstName : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('last_name',array('label'=>"LastName : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('admin',array('label'=>"Admin : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>

			<?php echo $this->Form->input('id');?>
		<tr><td><?php echo $this->Form->button("Update Client", array('class' => 'btn btn-primary')); ?></td></tr>
		<?php echo $this->Form->end(); ?>
	</table>
</div>
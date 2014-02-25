<div id="page-wrapper">        
	<div class="page-header">
		<h2>Register</h2>
	</div>
	<?php echo $this->Session->flash(); ?>
	
	<?php echo $this->Form->create('Client'); ?>
	<table>
		<?php echo $this->Form->input('username',array('label'=>"Login : ",
		    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<?php echo $this->Form->input('first_name',array('label'=>"firstname :",
		    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<?php echo $this->Form->input('last_name',array('label'=>"lastname :",
		    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<?php echo $this->Form->input('mail',array('label'=>"Email : ",
		    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<?php echo $this->Form->input('password',array('label'=>"Mot de passe :",
		    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<tr><td><?php echo $this->Form->end("Register"); ?></td></tr>
	</table>
</div>

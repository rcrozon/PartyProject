<div id="page-wrapper">        
	<div class="page-header">
		<h2>Register</h2>
	</div>
	<?php echo $this->Session->flash(); ?>
	
	<?php echo $this->Form->create('Client'); ?>
	<table>
		<?php echo $this->Form->input('username',array('label'=>"Login : ",
		    'before' => '<tr><td style="padding-right:15px;">', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<?php echo $this->Form->input('first_name',array('label'=>"Firstname : ",
		    'before' => '<tr><td style="padding-right:15px;">', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<?php echo $this->Form->input('last_name',array('label'=>"Lastname : ",
		    'before' => '<tr><td style="padding-right:15px;">', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<?php echo $this->Form->input('mail',array('label'=>"Mail : ",
		    'before' => '<tr><td style="padding-right:15px;">', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<?php echo $this->Form->input('password',array('label'=>"Password : ",
		    'before' => '<tr><td style="padding-right:15px;">', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<tr><td style="padding-top: 30px;padding-left:18px;"><?php echo $this->Form->button(" Register ", array('class' => 'btn btn-primary')); ?></td></tr>
		<?php echo $this->Form->end(); ?>
	</table>
</div>

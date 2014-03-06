<div id="page-wrapper">        
	<div class="page-header">
		<h2>Connection</h2>
	</div>
	<?php echo $this->Session->flash(); ?>
	
	<?php echo $this->Form->create('Client'); ?> 
	<table>
		<?php echo $this->Form->input('username',array('label'=>"Login : ",
		    'before' => '<tr><td style="padding-right:15px;">', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<?php echo $this->Form->input('password',array('label'=>"Password : ",
		    'before' => '<tr><td style="padding-right:15px;">', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<tr><td style="padding-top: 30px;padding-left:18px;"><?php echo $this->Form->button(" Login ", array('class' => 'btn btn-primary')); ?></td></tr>
		<?php echo $this->Form->end(); ?>
	</table>
</div>

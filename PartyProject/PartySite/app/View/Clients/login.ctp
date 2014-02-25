<div id="page-wrapper">        
	<div class="page-header">
		<h2>Connection</h2>
	</div>
	<?php echo $this->Session->flash(); ?>
	
	<?php echo $this->Form->create('Client'); ?> 
	<table>
		<?php echo $this->Form->input('username',array('label'=>"Login : ",
		    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<?php echo $this->Form->input('password',array('label'=>"Mot de passe :",
		    'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<tr><td><?php echo $this->Form->end(" Login "); ?></td></tr>
	</table>
</div>

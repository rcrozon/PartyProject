<?php $this->set('title_for_layout',"Editer mon profil"); ?>
<center>
	<div id="page-wrapper">        
		<div class="page-header">
			<h2>My Profile</h2>
		</div>
		<?php echo $this->Form->create('Client'); ?>
  		<table>
			<?php echo $this->Form->input('first_name',array('label'=>"First Name:",
			   	'before' => '<tr><td style="padding-right:15px;">', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('last_name',array('label'=>"Last Name:",
			   	'before' => '<tr><td style="padding-right:15px;">', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('pass1',array('label'=>"Password:",
			   	'before' => '<tr><td style="padding-right:15px;">', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('pass2',array('label'=>"Confirm password:",
			   	'before' => '<tr><td style="padding-right:15px;">', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<tr><td style="padding-top: 30px;padding-left:18px;"><?php echo $this->Form->button(" Update ", array('class' => 'btn btn-primary')); ?></td></tr>
			<?php echo $this->Form->end(); ?>
		</table>
	</div>
</center>

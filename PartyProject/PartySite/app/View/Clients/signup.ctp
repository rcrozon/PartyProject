<div id="page-wrapper">        

		<div class="loginBox">

	<?php echo $this->Session->flash(); ?>
	
	<?php echo $this->Form->create('Client',array('class'=>"login")); ?>
 <p>
		<?php echo $this->Form->input('username',array('label'=>"Login : ")); ?>
		 </p>
		 	 <p>

		<?php echo $this->Form->input('first_name',array('label'=>"Firstname : ")); ?>
		 </p>
		  <p>
		<?php echo $this->Form->input('last_name',array('label'=>"Lastname : ")); ?>
		 </p>
		  <p>
		<?php echo $this->Form->input('mail',array('label'=>"Mail : ")); ?>
		 </p>
		 	 <p>
		<?php echo $this->Form->input('password',array('label'=>"Password : ")); ?>
		 </p>
		 	 <p>
		<?php echo $this->Form->button(" Register ", array('class' => 'btn btn-primary','style'=>'float:right')); ?>
		 </p>
		<?php echo $this->Form->end(); ?>

</div>
</div>

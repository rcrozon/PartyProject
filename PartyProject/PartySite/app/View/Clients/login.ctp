<div id="page-wrapper">        
	<div class="page-header">
	</div>
	<div class="loginBox">
	<?php echo $this->Session->flash(); ?>
	
	<?php echo $this->Form->create('Client',array('class'=>"login")); ?> 
	 <p>
		<?php echo $this->Form->input('username',array('label'=>"Login : ",
		     'id'=>"login")); ?>
		     </p>
		      <p>
		<?php echo $this->Form->input('password',array('label'=>"Password : ",
		    'id'=>"password")); ?>
		    </p>
		     <p class="login-submit">

      <button type="submit" class="login-button">Login</button>
		 </p>
		<?php echo $this->Form->end(); ?>
</div>
</div>


<?php $this->set('title_for_layout',"Editer mon profil"); ?>
<center>
  <div class="col-md-4">
<h2>My Profile</h2>
<?php echo $this->Form->create('Client'); ?>
   <?php echo $this->Form->input('first_name',array('label'=>"First Name:")); ?>
   <?php echo $this->Form->input('last_name',array('label'=>"Last Name:")); ?>
   <?php echo $this->Form->input('pass1',array('label'=>"Password:")); ?>
   <?php echo $this->Form->input('pass2',array('label'=>"Confirm password:")); ?>
<?php echo $this->Form->end('Modifier'); ?>
</div>
</center>

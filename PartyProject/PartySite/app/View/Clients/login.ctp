<?php echo $this->Session->flash(); ?>
<?php echo $this->Form->create('Client'); ?> 
    <?php echo $this->Form->input('username',array('label'=>"Login : ")); ?>
    <?php echo $this->Form->input('password',array('label'=>"Mot de passe :")); ?>
<?php    echo $this->Form->end("Se connecter"); ?>

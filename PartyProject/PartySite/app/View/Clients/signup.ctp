<?php echo $this->Form->create('Client'); ?>
    <?php echo $this->Form->input('username',array('label'=>"Login : ")); ?>
        <?php echo $this->Form->input('first_name',array('label'=>"firstname :")); ?>

    <?php echo $this->Form->input('last_name',array('label'=>"lastname :")); ?>

    <?php echo $this->Form->input('mail',array('label'=>"Email : ")); ?>
    <?php echo $this->Form->input('password',array('label'=>"Mot de passe :")); ?>
<?php echo $this->Form->end("S'enregistrer"); ?>

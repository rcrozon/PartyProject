<strong>Bonjour <?php echo $username; ?></strong>
<p>Pour activer ce compte suivez le lien :</p>
<p><?php echo $this->Html->link('Activer mon compte',$this->Html->url($link),true); ?></p>
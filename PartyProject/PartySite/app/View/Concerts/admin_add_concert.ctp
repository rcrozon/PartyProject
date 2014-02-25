<div id="page-wrapper">        
	<div class="page-header">
		<h1>Add Concert</h1>
	</div>

	<table>
		<?php echo $this->Form->create('Concert', array('type' => 'file')); ?>
		  	<?php echo $this->Form->input('name_concert',array('label'=>"Party name* : ",
		  		'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		  	<?php echo $this->Form->input('name',array('label'=>"Artists* : ",
		  		'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false, 'id' => 'demo-input-facebook-theme'/*, 'class' => 'form-error', 'required' => 'required'*/)); ?>
			<?php echo $this->Form->input('location',array('label'=>"Scene* : ", 
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('nb_seats',array('label'=>"Numbrer of seats* : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('image_file', array('type' => 'file', 'label'=>"Background image : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('start_datetime', array('type' => 'datetime', 'label'=>"Start date and hour : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		  	<?php echo $this->Form->input('end_datetime', array('type' => 'datetime', 'label'=>"End date and hour : ",
		  		'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		<tr><td><?php echo $this->Form->button("Create my party", array('class' => 'btn btn-primary')); ?></td></tr>
		<?php echo $this->Form->end(); ?>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#demo-input-facebook-theme").tokenInput("../../app/webroot/script-web/search.php?q=$name",

				{
					theme: "facebook",
					noResultsText: "No result",
					preventDuplicates: true,
					tokenDelimiter: "|"
				});
			});
		</script>
	</table>
</div>

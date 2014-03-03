<div id="page-wrapper">        
	<div class="page-header">
		<h1>Add Artist to Party '<?php echo $partyName; ?>'</h1>
	</div>

	<table>
		<?php echo $this->Form->create('AssocArtist'); ?>
		  	<?php echo $this->Form->input('name',array('label'=>"Artists* : ",
		  		'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false, 'id' => 'demo-input-facebook-theme'/*, 'class' => 'form-error', 'required' => 'required'*/)); ?>
		<tr><td><?php echo $this->Form->button("Add artist", array('class' => 'btn btn-primary')); ?></td></tr>
		<?php echo $this->Form->end(); ?>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#demo-input-facebook-theme").tokenInput("../../../app/webroot/script-web/search.php?q=$name",
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

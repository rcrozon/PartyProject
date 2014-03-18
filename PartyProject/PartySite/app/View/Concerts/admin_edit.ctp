<div id="page-wrapper">        
	<div class="page-header">
		<h1>Edit Party : '<?php echo $partyName; ?>'</h1>
	</div>

	<table>
		<?php echo $this->Form->create('Concert', array('type' => 'file'));?>
  			<?php echo $this->Form->input('name_concert',array('label' => "Party name :", 
  				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
			<?php echo $this->Form->input('location',array('label'=>"Scene : ", 
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
			<?php echo $this->Form->input('nb_seats',array('label'=>"Number of seats : ", 
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
			<?php echo $this->Form->input('image_file', array('type' => 'file', 'label'=>"Background image : ", 
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
			<?php echo $this->Form->input('start_datetime', array('type' => 'datetime', 'label'=>"Start date and hour : ", 
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
  			<?php echo $this->Form->input('end_datetime', array('type' => 'datetime', 'label'=>"End date and hour : ", 
  				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false));?>
  			<?php echo $this->Form->input('name', array('label'=>"Artists : ", 
  				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false, 'id' => 'demo-input-facebook-theme'));?>
  			<script type="text/javascript">
				$(document).ready(function() {
					$("#demo-input-facebook-theme").tokenInput("../../../app/webroot/script-web/search.php?q=$name",
					{
						theme: "facebook",
						noResultsText: "No result",
						preventDuplicates: true,
						tokenDelimiter: "|",
						<?php if(!empty($artistsName)): ?>
							prePopulate: [
								<?php echo $artistsName; ?>
							]
						<?php endif; ?>
					});
				});
			</script>
  			<?php echo $this->Form->input('style', array('label'=>"Styles : ", 
  				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false, 'id' => 'style-input'));?>
  			<script type="text/javascript">
				$(document).ready(function() {
					$("#style-input").tokenInput("../../../app/webroot/script-web/search_style.php?q=$name",
					{
						theme: "facebook",
						noResultsText: "No result",
						preventDuplicates: true,
						tokenDelimiter: "|",
						<?php if(!empty($stylesName)): ?>
							prePopulate: [
								<?php echo $stylesName; ?>
							]
						<?php endif; ?>
					});
				});
			</script>
  			<!--<tr>
  				<td><label>Is Full ?</label></td>
	  			<?php echo $this->Form->input('full', array( 
	  				'before' => '<td>', 'after' => '</td>', 'div' => false));?>
  			</tr>
  			<tr>
  				<td><label>Is Online ?</label></td>
  				<?php echo $this->Form->input('online', array( 
  				'before' => '<td>', 'after' => '</td>', 'div' => false));?>
  			</tr>-->
  			<tr>
  				<td><label>Is Full ?</label></td>
  				<td>
		  			<div class="row-fluid tp">
			          <div class="span4 offset2">
			            <div class="onoffswitch tick">
			              <input type="checkbox" name="full" class="onoffswitch-checkbox" id="myonoffswitch-tick" 
			              	<?php if($full) echo 'checked'; ?>
			              >
			              <label class="onoffswitch-label" for="myonoffswitch-tick">
				               <span class="onoffswitch-inner"></span>
				               <span class="onoffswitch-switch tickswitch-switch"></span>
			              </label>
			            </div>
			          </div>
		        	</div>
		        </td>
	        </tr>
	        <tr>
  				<td><label>Is Online ?</label></td>
  				<td>
		  			<div class="row-fluid tp">
			          <div class="span4 offset1">
			            <div class="onoffswitch tickoff">
			              <input type="checkbox" name="online" class="onoffswitch-checkbox" id="myonoffswitch-tickoff" 
			              	<?php if($online) echo 'checked'; ?>
			              >
			              <label class="onoffswitch-label" for="myonoffswitch-tickoff">
			                  <span class="onoffswitch-inner"></span>
			                  <span class="onoffswitch-switch tickswitch-switch"></span>
			              </label>
			            </div>
			          </div>
		        	</div>
		        </td>
	        </tr>
  			<?php echo $this->Form->input('id');?>
		<tr><td><?php echo $this->Form->button("Update my party", array('class' => 'btn btn-primary'));?></td></tr>
		<?php echo $this->Form->end(); ?>
	</table>
</div>
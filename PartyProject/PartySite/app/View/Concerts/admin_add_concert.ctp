<div id="page-wrapper">        
	<div class="page-header">
		<h1>Add Concert</h1>
	</div>

	<!--<div class="row-fluid">
  		<div class="span4">
			<div class="box-wrap">
		  		<div class="box-inner">
			  		<div class="control-group">
					  	<div class="row-fluid input-append date">
				  			<label>Date</label>
						  	<input name="date" class="date-picker" id="id-date-picker-1" type="text" data-date-format="dd-mm-yyyy" />
							<span class="icon32 icon-red icon-calendar"></span>
				        </div>
				    </div>
				</div>
			</div>
		</div>
	</div>-->

	
          <!--<div class="input-append bootstrap-timepicker">
        	<input id="timepicker1" type="text" class="input-small" />
        	<span style="border:1px solid #ccc;border-radius:0 4px 4px 0;height:35px;width:35px;background-color:#eeeeee;" 
					class="add-on icon32 icon-red icon-calendar">
			</span>
      	</div>-->

      	
    

	<table>
		<?php echo $this->Form->create('Concert', array('type' => 'file')); ?>
		  	<?php echo $this->Form->input('name_concert',array('label'=>"Party name* : ",
		  		'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		  	<?php echo $this->Form->input('name',array('label'=>"Artists* : ",
		  		'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false, 'id' => 'demo-input-facebook-theme')); ?>
		  	<?php echo $this->Form->input('style',array('label'=>"Styles* : ",
		  		'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false, 'id' => 'style-input')); ?>
			<?php echo $this->Form->input('location',array('label'=>"Scene* : ", 
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('nb_seats',array('label'=>"Numbrer of seats* : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<?php echo $this->Form->input('image_file', array('type' => 'file', 'label'=>"Background image : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
			<!--<?php echo $this->Form->input('start_datetime', array('type' => 'datetime', 'label'=>"Start date and hour : ",
				'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>
		  	<?php echo $this->Form->input('end_datetime', array('type' => 'datetime', 'label'=>"End date and hour : ",
		  		'before' => '<tr><td>', 'after' => '</td></tr>', 'between' =>'</td><td>', 'div' => false)); ?>-->
		<table>
		  	<tr>
			  	<td style="width:171px;"><label>Start Date & Hour : </label></td>
			  	<td style="padding-right:0px;"><input name="start_date" class="date-picker" id="id-date-picker-1" type="text" data-date-format="dd-mm-yyyy" /></td>
				<td style="padding-top:10px;"><span style="border:1px solid #ccc;border-radius:0 4px 4px 0;height:35px;width:35px;background-color:#eeeeee;" 
					class="icon32 icon-red icon-calendar"></span></td>

				<td class="input-append bootstrap-timepicker" style="padding-right:0px;">
					<input name="start_hour" class="input-small" id="timepicker1" type="text"/>
					<span style="border:1px solid #ccc;border-radius:0 4px 4px 0;height:35px;width:35px;background-color:#eeeeee;margin-top: -9px;
								margin-left: -4px;" 
						class="add-on icon32 icon-red icon-clock">
					</span>
				</td> 		
			</tr>
		</table>

		<table>
		  	<tr>
			  	<td style="width:171px;"><label>End Date & Hour : </label></td>
			  	<td style="padding-right:0px;"><input name="end_date" class="date-picker" id="id-date-picker-2" type="text" data-date-format="dd-mm-yyyy" /></td>
				<td style="padding-top:10px;"><span style="border:1px solid #ccc;border-radius:0 4px 4px 0;height:35px;width:35px;background-color:#eeeeee;" 
					class="icon32 icon-red icon-calendar"></span></td>

				<td class="input-append bootstrap-timepicker" style="padding-right:0px;">
					<input name="end_hour" class="input-small" id="timepicker2" type="text"/>
					<span style="border:1px solid #ccc;border-radius:0 4px 4px 0;height:35px;width:35px;background-color:#eeeeee;margin-top: -9px;
								margin-left: -4px;" 
						class="add-on icon32 icon-red icon-clock">
					</span>
				</td> 		
			</tr>
		</table>

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
		<script type="text/javascript">
			$(document).ready(function() {
				$("#style-input").tokenInput("../../app/webroot/script-web/search_style.php?q=$name",
				{
					theme: "facebook",
					noResultsText: "No result",
					preventDuplicates: true,
					tokenDelimiter: "|"
				});
			});
		</script>
		<script type="text/javascript">
		    $(document).ready(function() {   
		        $('#id-date-picker-1').datepicker();
		        $('#id-date-picker-2').datepicker();
		        $('#timepicker1').timepicker({
		          minuteStep: 1,
		          showSeconds: true,
		          showMeridian: false
		        });
		        $('#timepicker2').timepicker({
		          minuteStep: 1,
		          showSeconds: true,
		          showMeridian: false
		        });
		    });
    	</script>
	</table>
</div>

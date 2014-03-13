	<?php
require('../webroot/date/dateConverter.php');

?>


	<link rel="stylesheet" href="<?php echo $this->Html->url('/css/slider.css');?>">
	<link rel="stylesheet" href="<?php echo $this->Html->url('/css/slider.less');?>">
	    <link rel="stylesheet" href="<?php echo $this->Html->url('/css/token-input-facebook.css');?>" type="text/css">
	       <?php echo $this->Html->script('jquery.tokeninput'); ?>
	<style>
	.side-nav {
	margin-left: -225px;
	padding: 10px;
	padding-top: 50px;
	left: 225px;
	width: 18%;
	position: fixed;
	top: 50px;
	height: 100%;
	border-radius: 0;
	border: none;
	background-color: #222222;
	overflow-y: auto;
	}
	ul.token-input-list-facebook {

	width: 100%;

	}

	.side-nav label {
	color: #FFF;
	display: inline-block;
	margin-left: 0px;
	padding-top: 10px;
	font-size: 12px;
	}

	.side-nav input[type=text], input[type=datetime], input[type=number], input[type=password] {
	color: #777;

	width: 100%;

	}


	.event_fiche{
		font-family: Arial,sans-serif;
		padding: 0 0 0 210px;
	color: #666;
	font-size: 12px;
	}
	.multi_header{
		margin-bottom: 10px;
		position: relative;
	border: 1px solid #eee;
	padding: 10px;
	height:120px;
	}
	.multi_header img {
	width: 190px;
	height: 100px;
	float: left;
	}
	.multi_header .event_fiche h1 {
	font-size: 14px;
	margin: 0;
	color: #333;
	text-transform: uppercase;
	}
	.multi_header .event_fiche a {
	font-size: 14px;
	margin: 0;
	color: #333;
	text-transform: uppercase;
	}

	.resultSearch{
		margin-left: 21%;
	}

	input.search-query {
    padding-left:26px;
}
form.form-search {
    position: relative;
}
form.form-search:before {
    content:'';
    display: block;
    width: 14px;
    height: 14px;
    background-image: url(http://getbootstrap.com/2.3.2/assets/img/glyphicons-halflings.png);
    background-position: -48px 0;
    position: absolute;
    top:8px;
    left:8px;
    opacity: .5;
    z-index: 1000;
}
	</style>

	          
		

	 <?php echo $this->Html->script('bootstrap-slider'); ?>
	 	
	<div class="side-nav">
		<?php echo $this->Form->create('Search', array('type' => 'file','class'=>"form-search form-inline")); ?>
		 
      


         <div class="input-group">
         	  <?php echo $this->Form->input('search',array('label'=>"Party name : ",'class'=>"form-control search-query",
         	  "placeholder"=>"Search...", 'label' => false,'id'=>'demo')); ?><span class="input-group-btn">
           <span class="input-group-btn">

            <?php echo $this->Form->button("Search", array('class' => 'btn btn-primary')); ?>
            </span>

        </div>
      

		
			  
		
	<?php echo $this->Form->end(); ?>


		<?php echo $this->Form->create('Concert', array('type' => 'file')); ?>

		



	  <table style="width:100%;">
			<tr><td>
			  	<?php echo $this->Form->input('name',array('label'=>"Artists : ",
			  		 'id' => 'demo-input-facebook-theme')); ?>
			  	</td></tr>
		<tr><td>
		<?php echo $this->Form->input('style',array('label'=>"Styles : ",
			  	'id' => 'style-input')); ?>
	</td></tr>
	</table>
	  

	   <?php echo $this->Form->input('price',array('label'=>"Price : ",'type'=>"text",
	  'id'=>"foo",
	  'data-slider-min'=>"0",
	  'data-slider-max'=>"150", 'data-slider-step'=>"1", 'class'=>"span2",'data-slider-value'=>"[0,1]", 'data-slider-orientation'=>"horizontal", 'data-slider-selection'=>"before" ,'data-slider-tooltip'=>"show")); ?>
		
		

			

		<?php echo $this->Form->button("Search", array('class' => 'btn btn-primary')); ?>
	<?php echo $this->Form->end(); ?>
	<script>$('#foo').slider()
	  .on('slide', function(ev){
	     $('#bar').val(ev.value);
	  });

	  $('#foo2').slider()
	  .on('slide', function(ev){
	     $('#bar').val(ev.value);
	  });</script>

	</div>

	<div class="resultSearch">


	
	<?php 
	 if(!empty($allconcerts)){ 
	for($i=0;$i<sizeof($allconcerts);$i++)  {
		$resultStyle = $this->Style->getStylesByIDConcert($allconcerts[$i]['Concert']['id']);
		$style = '<p> Style(s): ';
			for($j=0;$j<sizeof($resultStyle);$j++){
				if($j==sizeof($resultStyle)-1){
					$style .= $resultStyle[$j]['Style']['name'];
				}
				else{
					$style .= $resultStyle[$j]['Style']['name'].', ';
				}
		
			}

			$resultArtists = $this->Artist->getArtistsByIDConcert($allconcerts[$i]['Concert']['id']);
			$artists =  'Artist(s): ';
			for($j=0;$j<sizeof($resultArtists);$j++){
				if($j==sizeof($resultArtists)-1){
					$artists .= $resultArtists[$j]['Artist']['name'];
				}
				else{
						$artists .= $resultArtists[$j]['Artist']['name'].', ';
				}
		
			}

		  $dateBegin = getDateOfDateTime($allconcerts[$i]['Concert']['start_datetime']);

            $dateEnd = getDateOfDateTime($allconcerts[$i]['Concert']['end_datetime']);


       		$timeBegin = getTimeOfDateTime($allconcerts[$i]['Concert']['start_datetime']);
		    $timeEnd  = getTimeOfDateTime($allconcerts[$i]['Concert']['end_datetime']);

		echo'<div class="multi_header"><img src="';
		echo $this->webroot."img/Concerts/".$allconcerts[$i]["Concert"]["image"].'"><div class="event_fiche">
	       <div class="event_titre">';

	       echo'<a href="';
					echo $this->Html->url(array(
    				"controller" => "concerts",
  					"action" => "showConcert",
  						"id"=>$allconcerts[$i]['Concert']['id'],
    				"slug" =>$allconcerts[$i]['Concert']['name_concert']
    			
					));
				echo "\" target=\"_blank\" class=\"mosaic-overlay\">".$allconcerts[$i]['Concert']['name_concert']."</a>";
	   
	      echo    '</div>'.'<div class="event_date">'
	       .'<p>'.$dateBegin.' - '.$timeBegin.' to '.$dateEnd.' - '. $timeEnd.' <p>'.
	       '</div>
	       <div class="event_info">'.$allconcerts[$i]['Concert']['location'].
	       '<br>'.$style.'<br>'.$artists.'</div></div></div>';
	  }
	} 
		
	?>


	</div>








		

	       
	  
		<script type="text/javascript">
		
			

					$(document).ready(function() {
					$("#demo-input-facebook-theme").tokenInput("../app/webroot/script-web/search.php?q=$name",
					{
						theme: "facebook",
						noResultsText: "No result",
						preventDuplicates: true,
						tokenDelimiter: "|"
					});
				});
					
		        $('#sl2').slider();

				$(document).ready(function() {
					$("#style-input").tokenInput("../app/webroot/script-web/search_style.php?q=$name",
					{
						theme: "facebook",
						noResultsText: "No result",
						preventDuplicates: true,
						tokenDelimiter: "|"
					});
				});
			</script>

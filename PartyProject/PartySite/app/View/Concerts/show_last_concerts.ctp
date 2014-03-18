<?php echo $this->Html->script('mosaic.1.0.1');
require('../webroot/date/dateConverter.php');
?>

<script type="text/javascript">  
	jQuery(function($){
	    $('.bar').mosaic({
	    	animation : 'slide'
	    });
	});
</script>

<script>
	// Demo functions
	// **************
	$(function(){
		prettyPrint();
		// External Link with callback function
		$("#slide-jump").click(function(){
			$('#slider2').anythingSlider(4, function(slider){ /* alert('Now on page ' + slider.currentPage); */ });
			return false;
		});
		// External Link
		$("a.muppet").click(function(){
			$('#slider1').anythingSlider(5);
			$(document).scrollTop(0); // make the page scroll to the top so you can watch the video
			return false;
		});
		// Report Events to console & features list
		$('#slider1, #slider2').bind('before_initialize initialized swf_completed slideshow_start slideshow_stop slideshow_paused slideshow_unpaused slide_init slide_begin slide_complete', function(e, slider){
			// show object ID + event (e.g. "slider1: slide_begin")
			var txt = slider.$el[0].id + ': ' + e.type + ', now on panel #' + slider.currentPage;
			$('#status').text(txt);
			if (window.console && window.console.firebug){ console.debug(txt); } // added window.console.firebug to make this work in Opera
		});
		// Theme Selector (This is really for demo purposes only)
		var themes = ['minimalist-round','minimalist-square','metallic','construction','cs-portfolio'];
		$('#currentTheme').change(function(){
			var theme = $(this).val();
			$('#slider1').closest('div.anythingSlider')
				.removeClass( $.map(themes, function(t){ return 'anythingSlider-' + t; }).join(' ') )
				.addClass('anythingSlider-' + theme);
			$('#slider1').anythingSlider(); // update slider - needed to fix navigation tabs
		});
		// Add a slide
		var imageNumber = 1;
		$('button.add').click(function(){
			$('#slider1')
				.append('<li><img src="demos/images/slide-tele-' + (++imageNumber%2 + 1)  + '.jpg" alt="" /></li>')
				.anythingSlider(); // update the slider
		});
		$('button.remove').click(function(){
			$('#slider1 > li:not(.cloned):last').remove();
			$('#slider1').anythingSlider(); // update the slider
		});
	});
</script>

<script>
	// Set up Sliders
	// **************
	$(function(){
		$('#slider1').anythingSlider({
			theme           : 'metallic',
			easing          : 'easeInOutBack',
			navigationFormatter : function(index, panel){
				return ['Slab', 'Parking Lot', 'Drive', 'Glorius Dawn', 'Bjork?', 'Traffic Circle'][index - 1];
			},
			onSlideComplete : function(slider){
				// alert('Welcome to Slide #' + slider.currentPage);
			}
		});
		$('#slider2').anythingSlider({
			mode                : 'f',   // fade mode - new in v1.8!
			resizeContents      : false, // If true, solitary images/objects in the panel will expand to fit the viewport
			navigationSize      : 3,     // Set this to the maximum number of visible navigation tabs; false to disable
			navigationFormatter : function(index, panel){ // Format navigation labels with text
				return ['Recipe', 'Quote', 'Image', 'Quote #2', 'Image #2'][index - 1];
			},
			onSlideBegin: function(e,slider) {
				// keep the current navigation tab in view
				slider.navWindow( slider.targetPage );
			}
		});
		// tooltips for first demo
		$.jatt();
	});
</script>

<style type="text/css">		
	/*Demo Styles*/
	body { background-color:#e9eaed; }

	.details{ margin:15px 15px; }	
	 a:hover{
		 text-decoration:none; 
		 font-color:blue;
	}

	h4 { font:300 16px 'Helvetica Neue', Helvetica, Arial, sans-serif; line-height:160%; letter-spacing:0.15em; color:#fff; text-shadow:1px 1px 0 rgb(0,0,0); }

	p { font:300 11px 'Lucida Grande', Tahoma, Verdana, sans-serif; color:#aaa; text-shadow:1px 1px 0 rgb(0,0,0);}

	a { text-decoration:none; }

	.img {
		height="100%";
		width="100%"; 
	}
	.sticker {
		color: #fff;
		display: inline-block;
		font-family: "Open Sans",Arial,sans-serif;
		font-size: 11px;
		font-weight: bold;
		height: 20px;
		letter-spacing: 2px;
		line-height: 20px;
		padding: 2px 12px;
		vertical-align: top;
		white-space: nowrap;
		background-color:#32323a;
	}
	#fond {
		position: relative;
		margin: 0px auto;
		z-index: 10;
	}
	.ruban {
		background: #32323a;
		height: 30px;
		width: 300px;
		position: relative;
		left: -15px;
		top: 5px;
		float: left;
		box-shadow: 0px 0px 4px rgba(0,0,0,0.55);
		z-index: 100;
	}
	.ruban h2 {
		font-size: 15px;
		color: #fff;
		text-shadow: 1px 1px 2px rgba(0,0,0,0.2);
		text-align: center;
		margin-top: 7px;
	}
	.ruban_gauche {
		border-color: transparent #32323a transparent transparent;
		border-style: solid;
		border-width: 15px;
		height: 0px;
		width: 0px;
		position: relative;
		left: -30px;
		top: 20px;
		z-index: -1;
	}
	.ruban_droit {
		border-color: transparent transparent transparent #7e9202;
		border-style:solid;
		border-width:15px;
		height:0px;
		width:0px;
		position: relative;
		left: 350px;
		top: 35px;
		z-index: -1;
	}
</style>
		
<div id="fond"> 
      <div class="ruban">     
        <h2>NEWEST PARTY</h2>     
      </div>     
      <div class="ruban_gauche"></div>
</div>

<div class="row">
	<ul id="slider1">
		<?php 
			$results = Hash::extract($showLastConcerts, '{n}.Concert');		
							
			for ($i = 0; $i <= sizeof($results)-1; $i++) {
				$d = $results[$i];
			$resultStyle = $this->Style->getStylesByIDConcert($d['id']);
		$style = '<p> Style(s): ';
			for($j=0;$j<sizeof($resultStyle);$j++){
				if($j==sizeof($resultStyle)-1){
					$style .= $resultStyle[$j]['Style']['name'];
				}
				else{
					$style .= $resultStyle[$j]['Style']['name'].', ';
				}
		
			}

			$resultArtists = $this->Artist->getArtistsByIDConcert($d['id']);
			$artists =  'Artist(s): ';
			for($j=0;$j<sizeof($resultArtists);$j++){
				if($j==sizeof($resultArtists)-1){
					$artists .= $resultArtists[$j]['Artist']['name'];
				}
				else{
						$artists .= $resultArtists[$j]['Artist']['name'].', ';
				}
		
			}

		  $dateBegin = getDateOfDateTime($d['start_datetime']);

            $dateEnd = getDateOfDateTime($d['end_datetime']);


       		$timeBegin = getTimeOfDateTime($d['start_datetime']);
		    $timeEnd  = getTimeOfDateTime($d['end_datetime']);				//echo $i;
	
				

				if(($i+1)%8==1) {
					echo '<li>';
				}
					echo "<div class=\"col-md-3\">";
						echo "<div class='mosaic-block bar'>";
							echo "<a href=\"";
								echo $this->Html->url(array(
								"controller" => "concerts",
									"action" => "showConcert",
										"id"=>$d['id'],
								"slug" =>$d['name_concert']
								));

							echo "\" target=\"_blank\" class=\"mosaic-overlay\">";
							echo "<div class=\"details\">";
								echo "<h4>" ; echo $d['name_concert'] ;echo "</h4>";
								echo "<p>"; echo"Location: "; echo $d['location'] ;echo "</p>";
								echo '<p>'.$artists.' </p>';
								echo '<p>'.$style.' </p>';

								echo '<p>'.$dateBegin.' - '.$timeBegin.' to '.$dateEnd.' - '. $timeEnd.' <p>';
							echo"</div> </a>";

							echo"<div class=\"mosaic-backdrop\">";
								echo $this->Html->image("Concerts/".$d['image']);
							echo  "</div>
						</div>		
					<div class=\"clearfix\"></div></div>";
				if(($i+1)%8==0) {
					echo '</li>';
				}
			}
		?>
	</ul>
</div>

			
			
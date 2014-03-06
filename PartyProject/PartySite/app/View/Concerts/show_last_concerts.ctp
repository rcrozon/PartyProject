   <?php echo $this->Html->script('mosaic.1.0.1');?>

<script type="text/javascript">  
      
      jQuery(function($){
        $('.bar').mosaic({
          animation : 'slide'
        });
        });
        
    </script>

<style type="text/css">
		
			/*Demo Styles*/
				body{
					background-color:#e9eaed;
				}
				.details{ margin:15px 20px;  }	
					h4{ font:300 16px 'Helvetica Neue', Helvetica, Arial, sans-serif; line-height:160%; letter-spacing:0.15em; color:#fff; text-shadow:1px 1px 0 rgb(0,0,0); }
					p{ font:300 12px 'Lucida Grande', Tahoma, Verdana, sans-serif; color:#aaa; text-shadow:1px 1px 0 rgb(0,0,0);}
					a{ text-decoration:none; }

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
}
		
		</style>
		

<div id="fond"> 
      <div class="ruban">     
        <h2>NEWEST PARTY</h2>     
      </div>     
      <div class="ruban_gauche"></div>
     
</div>
		<div class="row"> 
			
		<?php 


				$results = Hash::extract($showLastConcerts, '{n}.Concert');		
							
			for ($i = 0; $i <= sizeof($results)-1; $i++) {
				$d = $results[$i];
				
				 echo" <div class=\"col-md-3\">";

 				echo "<div class='mosaic-block bar'>";
 			
				echo"<a href=\"";
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
				
				echo "<p>" ; echo $d['start_datetime'] ;echo "</p>";
				echo"</div> </a>";

				echo"<div class=\"mosaic-backdrop\">";
				echo $this->Html->image("Concerts/".$d['image']);
				echo  "</div>
		</div>		
	
				<div class=\"clearfix\"></div></div>";
	


			}
		
		
			
?>

</div>

			
			
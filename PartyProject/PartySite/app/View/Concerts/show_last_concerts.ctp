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
			/*	body{
					background-image:url("../img/Concerts/2012-theatre-anzin-affiche-c2c.jpg");
				}*/
				.details{ margin:15px 20px;  }	
					h4{ font:300 16px 'Helvetica Neue', Helvetica, Arial, sans-serif; line-height:160%; letter-spacing:0.15em; color:#fff; text-shadow:1px 1px 0 rgb(0,0,0); }
					p{ font:300 12px 'Lucida Grande', Tahoma, Verdana, sans-serif; color:#aaa; text-shadow:1px 1px 0 rgb(0,0,0);}
					a{ text-decoration:none; }

				.img {
					 height="100%";
					 width="100%"; 
				}
		
		</style>
		<div class="row"> 
			<h1 style="border-bottom:1px solid cornflowerblue;color:#003d4c;font-size:36px;padding-bottom:15px;">Newest Party</h1>
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

			
			
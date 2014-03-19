 
<style>
table td {
  padding-left: 10px;
}
</style>
 
   <table style="margin-bottom:15px">
    <tr>
      <td>
 <div class="btn-group btn-input clearfix">
  <button type="button" class="btn btn-default dropdown-toggle form-control" data-toggle="dropdown">
    <span data-bind="label">All Concerts</span> <span class="caret"></span>
  </button>
  <ul class="dropdown-menu" role="menu">
      <li name="concert" value="All"><a  href="#">All Concerts</a></li>
    <?php for($i=0;$i<sizeof($listID);$i++){
     $concert = $this->Concert->getConcertByID($listID[$i]['reservations']['id_concert']);
      echo '<li name="concert" value="'.$concert['Concert']['id'].'"><a href="#">'.$concert['Concert']['name_concert'].'</a></li>';
    }?>  
  </ul>
</div>
      </td>
      <td>

<div class="btn-group btn-input clearfix">
  <button type="button" id="scanMenu"  class="btn btn-default dropdown-toggle form-control" data-toggle="dropdown">
    <span data-bind="label">All</span> <span class="caret"></span>
  </button>
  <ul class="dropdown-menu"  role="menu">
      <li  name="scan" value="All"><a  href="#">All</a></li>
    
  <li  name="scan" value="1"><a href="#">Scanned</a></li>
  <li   name="scan" value="0"><a href="#">Not Scanned</a></li>
  </ul>
</div>
      </td>
      <td>

<?php echo $this->Form->create('Reservation',array('style'=>'margin-top:0px')); ?> 
    <?php echo $this->Form->input('idConcert',array('label'=>"Party name : ",'id'=>'idConcert','type'=>"hidden",'value'=>'all')); ?>
    <?php echo $this->Form->input('scanned',array('label'=>"Party name : ",'id'=>'scanned','type'=>"hidden",'value'=>'all')); ?>
   


 
 <?php echo $this->Form->button("Search", array('class' => 'btn btn-primary')); ?>

    <?php echo $this->Form->end(); ?>
      </td>

</tr>
</table>


              
              




<div class="panel panel-primary">
  <div class="panel-heading">
        <h3 class="panel-title">My tickets</h3>
      </div>
<table class="table table-hover">
      <thead>
        <tr>
          <th>#</th>
          <th>Party Name</th>
          <th>Client Name</th>
          <th>Tarif</th>
          <th>Scan ?</th>
          <th>Link</th>
        </tr>
      </thead>
      <tbody>

      <?php 
      for ($i = 0; $i < sizeof($reservations); $i++) {



        echo '<tr>';
        $client = $this->Client->getClientByID($reservations[$i]['reservations']['id_client']);
        $concert = $this->Concert->getConcertByID($reservations[$i]['reservations']['id_concert']);
        $tarif = $this->Tarif->getTarifByID($reservations[$i]['reservations']['id_tarif']);

        echo '<td>'.$i.'</td>';
        echo '<td>'.$concert['Concert']['name_concert'].'</td>';
        echo '<td>'.$client['Client']['first_name'].' '.$client['Client']['last_name'].'</td>';
        echo '<td>'.$tarif['Tarif']['label'].'</td>';
        if($reservations[$i]['reservations']['scan']=='0'){
            echo '<td>'.'<span class="label label-danger">Not scanned</span>'.'</td>';
        }
        else{
            echo '<td>'.'<span class="label label-success">Scanned</span>'.'</td>';
        }

        echo '<td> <a href="';
        echo $this->Html->url(array(
            "controller" => "reservations",
            "action" => "create_pdf",
            "id"=>$reservations[$i]['reservations']['id']         
          ));
         echo '"role="button" style="margin-left:15px; margin-top:5px;" class="btn btn-primary">Ticket</a>   
          </td>';
      }




      ?>

      


      
        
        
        
      </tbody>
    </table> 
        </div>
        <script>
        $( document.body ).on( 'click', '.dropdown-menu li', function( event ) {
 
   var $target = $( event.currentTarget );
 
   $target.closest( '.btn-group' )
      .find( '[data-bind="label"]' ).text( $target.text() )
         .end()
      .children( '.dropdown-toggle' ).dropdown( 'toggle' );
      if($target.attr('name') == 'concert'){
       document.getElementById("idConcert").value = $target.attr('value');
     }
     else{
      document.getElementById("scanned").value = $target.attr('value');
     }
   return false;
 
});
 


        </script>
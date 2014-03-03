<div class="panel panel-info">
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

      for ($i = 0; $i <= sizeof($reservations)-1; $i++) {


        echo '<tr>';
        $client = $this->Client->getClientByID($reservations[$i]['Reservation']['id_client']);
        $concert = $this->Concert->getConcertByID($reservations[$i]['Reservation']['id_concert']);
        $tarif = $this->Tarif->getTarifByID($reservations[$i]['Reservation']['id_tarif']);

        echo '<td>'.$i.'</td>';
        echo '<td>'.$concert['Concert']['name_concert'].'</td>';
        echo '<td>'.$client['Client']['first_name'].' '.$client['Client']['last_name'].'</td>';
        echo '<td>'.$tarif['Tarif']['label'].'</td>';
        if($reservations[$i]['Reservation']['id_client']==1){
            echo '<td>'.'<span class="label label-danger">Not scanned</span>'.'</td>';
        }
        else{
            echo '<td>'.'<span class="label label-success">Scanned</span>'.'</td>';
        }

        echo '<td> <a href="';
        echo $this->Html->url(array(
            "controller" => "reservations",
            "action" => "create_pdf",
            "id"=>$reservations[$i]['Reservation']['id']         
          ));
         echo '"role="button" style="margin-left:15px; margin-top:5px;" class="btn btn-primary">Ticket</a>   
          </td>';
      }




      ?>

      


      
        
        
        
      </tbody>
    </table> 
        </div>


<?php
 require('../webroot/date/dateConverter.php');

App::import('Vendor','xtcpdf');
 $width = 490;
 $height = 168;
 $unitXText = $width/3;
 $unitX = ($width/8);
 $unitY = $height/3;
$pageLayout = array($height, $width); //  or array($height, $width) 
$pdf = new TCPDF('l', 'px', $pageLayout, true, 'UTF-8', false); 
$pdf->SetMargins(0,0,0);
$pdf->AddPage();
$pdf->SetFont('helvetica', '', 10);
    $pdf->SetTextColor(255,255,255);
    $pdf->Text(50, 66, 'Scale');
    $resultArtists = $this->Artist->getArtistsByIDConcert($concert['Concert']['id']);

            $artists =  'Artist(s): ';
            for($v=0;$v<sizeof($resultArtists);$v++){
                if($v==sizeof($resultArtists)-1){
                    $artists .= $resultArtists[$v]['Artist']['name'];
                }
                else{
                        $artists .= $resultArtists[$v]['Artist']['name'].', ';
                }
        
            }

  $dateBegin = getDateOfDateTime($concert['Concert']['start_datetime']);



            $timeBegin = getTimeOfDateTime($concert['Concert']['start_datetime']);

$html = '<style>
h1{
     color:'.$ticketInfo['TicketInfo']['colorFont'].';
}
p{
    color:'.$ticketInfo['TicketInfo']['colorFont'].';
    font-weight:bolder;
     line-height: 75%
}
 </style><h1>'.$concert['Concert']['name_concert'].'</h1>'
.'<p>'. $artists.'</p>' 
.'<p>'.$dateBegin.' - '.$timeBegin.'</p>'
.'<p>Location: '.$concert['Concert']['location'].'</p>'
.'<p>'.$concert['Concert']['start_datetime'].'</p>'
.'<p> Client: '.$client['Client']['first_name']." ".$client['Client']['last_name'].'</p>'


;
 $pdf->SetAutoPageBreak(false, 0);
$style = array(

    'border' => 2,
    'vpadding' => 'auto',
    'hpadding' => 'auto',
    'fgcolor' => array(0,0,0),
    'bgcolor' => array(255,255,255),
    'module_width' => 1, // width of a single module in points
    'module_height' => 1 // height of a single module in points
);

 $img_file = $this->webroot.'app/webroot/img/Tickets/'.$ticketInfo['TicketInfo']['image'];
$pdf->Image($img_file, 0, 0, 490, 168, '', '', '', false, 300, '', false, false, 0);
//$pdf->writeHTML($html, true, false, true, false, '');
$pdf->writeHTMLCell( $unitXText, 168,($ticketInfo['TicketInfo']['coordTextX']-1)*$unitXText, '10', $html, 'LRTB', 1, 0, true, 'L', true);




$codeQR = $reservation['Reservation']['id'].';'.$concert['Concert']['id'].';'.$client['Client']['id'].';'.$tarif['Tarif']['id'];
$pdf->write2DBarcode($codeQR, 'QRCODE,Q',($ticketInfo['TicketInfo']['coordQrX']-1)*$unitX, ($ticketInfo['TicketInfo']['coordQrY']-1)*$unitY, 60, 56, $style, 'N');
/*$pdf->write2DBarcode($codeQR, 'QRCODE,Q',($ticketInfo['TicketInfo']['coordQrX']-2)*$unitX, ($ticketInfo['TicketInfo']['coordQrY']-1)*$unitY, 60, 56, $style, 'N');
$pdf->write2DBarcode($codeQR, 'QRCODE,Q',($ticketInfo['TicketInfo']['coordQrX']-3)*$unitX, ($ticketInfo['TicketInfo']['coordQrY']-1)*$unitY, 60, 56, $style, 'N');
$pdf->write2DBarcode($codeQR, 'QRCODE,Q',($ticketInfo['TicketInfo']['coordQrX']-4)*$unitX, ($ticketInfo['TicketInfo']['coordQrY']-1)*$unitY, 60, 56, $style, 'N');
$pdf->write2DBarcode($codeQR, 'QRCODE,Q',($ticketInfo['TicketInfo']['coordQrX']-5)*$unitX, ($ticketInfo['TicketInfo']['coordQrY']-1)*$unitY, 60, 56, $style, 'N');
$pdf->write2DBarcode($codeQR, 'QRCODE,Q',($ticketInfo['TicketInfo']['coordQrX']-6)*$unitX, ($ticketInfo['TicketInfo']['coordQrY']-1)*$unitY, 60, 56, $style, 'N');
$pdf->write2DBarcode($codeQR, 'QRCODE,Q',($ticketInfo['TicketInfo']['coordQrX']-7)*$unitX, ($ticketInfo['TicketInfo']['coordQrY']-1)*$unitY, 60, 56, $style, 'N');
$pdf->write2DBarcode($codeQR, 'QRCODE,Q',($ticketInfo['TicketInfo']['coordQrX']-0)*$unitX, ($ticketInfo['TicketInfo']['coordQrY']-0)*$unitY, 60, 56, $style, 'N');*/


$pdf->lastPage();
$dir =APP.'webroot/files/'.$client['Client']['username'];

if (!is_dir($dir)) {
mkdir($dir, 0700);
}


$fileName = $reservation['Reservation']['id'].'_'.$concert['Concert']['name_concert'].'_'.$client['Client']['username'];

echo $pdf->Output(APP.'webroot/files/'.$client['Client']['username']."/".$fileName.'.pdf', 'F');

?>



<object height="400" width="100%" data=" <?php echo $this->webroot.'app/webroot/files/'.$client['Client']['username'].'/'.$fileName.'.pdf' ?>" type="application/pdf">

            <p>It appears you don't have a PDF plugin for this browser.
                No biggie... you can <a href= "<?php echo $this->webroot.'app/webroot/files/'.$client['Client']['username'].'/'.$fileName.'.pdf' ?>" >click here to
                download the PDF file.</a>
            </p>


        </object>

<a  class="btn btn-primary" href= "<?php echo $this->webroot.'app/webroot/files/'.$client['Client']['username'].'/'.$fileName.'.pdf' ?>" >click here to
                download the PDF file</a>
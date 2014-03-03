
<?php
 
App::import('Vendor','xtcpdf');
 $width = 168;
 $height = 480;
$pageLayout = array($width, $height); //  or array($height, $width) 
$pdf = new TCPDF('l', 'px', $pageLayout, true, 'UTF-8', false); 
$pdf->AddPage();
$pdf->SetFont('helvetica', '', 9);
    $pdf->SetTextColor(255,255,255);
    $pdf->Text(50, 66, 'Scale');

 
$html = '<h1>'.$concert['Concert']['name_concert'].'</h1>'
.'<p>Location: '.$concert['Concert']['location'].'</p>'
.'<p>'.$concert['Concert']['start_datetime'].'</p>'
.'<p>'.$client['Client']['first_name']." ".$client['Client']['last_name'].'</p>'


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

 $img_file = $this->webroot.'app/webroot/img/Concerts/'.$concert['Concert']['image'];
$pdf->Image($img_file, 0, 0, 480, 168, '', '', '', false, 300, '', false, false, 0);
$pdf->writeHTML($html, true, false, true, false, '');
$codeQR = $reservation['Reservation']['id'].';'.$concert['Concert']['id'].';'.$client['Client']['id'].';'.$tarif['Tarif']['id'];
$pdf->write2DBarcode($codeQR, 'QRCODE,Q', 400, 70, 130, 80, $style, 'N');

$pdf->lastPage();
$dir =APP.'webroot/files/'.$client['Client']['username'];

if (!is_dir($dir)) {
mkdir($dir, 0700);
}


$fileName = $reservation['Reservation']['id'].'_'.$concert['Concert']['name_concert'].'_'.$client['Client']['username'];

echo $pdf->Output(APP.'webroot/files/'.$client['Client']['username']."/".$fileName.'.pdf', 'F');

?>



<object height="300" width="100%" data=" <?php echo $this->webroot.'app/webroot/files/'.$client['Client']['username'].'/'.$fileName.'.pdf' ?>" type="application/pdf">

            <p>It appears you don't have a PDF plugin for this browser.
                No biggie... you can <a href= "<?php echo $dir . DS . $fileName.'.pdf' ?>" >click here to
                download the PDF file.</a>
            </p>

        </object>


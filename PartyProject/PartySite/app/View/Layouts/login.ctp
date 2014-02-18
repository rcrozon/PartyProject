<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr"></html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset utf-8"/>
    <title>
        <?php echo $title_for_layout; ?>
    </title>
    <link rel="stylesheet/less" href="<?php echo $this->Html->url('/css/login.css');?>">
        <?php echo $this->Html->script('less'); ?>  

</head>
<body>

   <div class="boxConnection">
   <?php echo $content_for_layout; ?>
   </div>
 
</body>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>
</html>
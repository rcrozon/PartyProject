<div class="notification <?php echo isset($type)?$type:'label-success'; ?>">
    <p class ="notif">
        <?php echo $message; ?>
        <a href="#" class="close" onclick="$(this).parent().parent().slideUp()">x</a>
    </p>
</div>
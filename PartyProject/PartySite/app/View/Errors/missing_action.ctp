<?php $this->extend('/Layouts/error'); ?>
<h2 style="font-size: 70px;">Error 404</h2>
<h3 style="font-size: 25px;">Page Not Found</h3>
<p class="error">
	<strong><?php echo __d('cake', 'Error'); ?>: </strong>
	<?php printf(
		__d('cake', 'The requested address %s was not found on this server.'),
		"<strong>'{$url}'</strong>"
	); ?>
</p>

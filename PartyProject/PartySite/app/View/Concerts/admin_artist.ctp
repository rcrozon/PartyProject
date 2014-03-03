<div id="page-wrapper">        
	<div class="page-header">
		<h1>Manage Artist(s) for party '<?php echo $partyName; ?>'</h1>
	</div>

	<table>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Actions</th>
		</tr>
		<?php if(!empty($artists)): ?>
			<?php foreach ($artists as $k => $v): $v = current($v); ?>
			<tr>
				<td><?php echo $v['Artist']['id'];?></td>
				<td><?php echo $v['Artist']['name'];?></td>
				<td>
					<?php echo $this->Html->link("Edit", array('action' => 'artist_edit', $v['Artist']['id'], $partyName, $idConcert)); ?> - 
					<?php echo $this->Html->link("Delete", array('action' => 'artist_delete', $v['Artist']['id'], $idConcert), null, 
						"Are you sure you really want delete this tarif"); ?>
				</td>
			</tr>
			<?php endforeach; ?>
		<?php endif; ?> 
	</table>
	<div style="padding-top:30px;">
		<?php echo $this->Html->link('Add Artist', array('action' => 'add_artist', $idConcert), array('class' => 'btn btn-primary')); ?>
	</div>
	<div style="padding-top:30px;">
		<?php echo $this->Html->link('Back To Party List', array('action' => 'table_concert'), array('class' => 'btn btn-primary')); ?>
	</div>
	<!--<div class="pagination"><?php echo $this->Paginator->numbers(); ?></div>-->
</div>
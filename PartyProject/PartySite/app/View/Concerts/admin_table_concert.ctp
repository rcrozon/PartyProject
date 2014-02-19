<div id="page-wrapper">        
	<div class="page-header">
		<h1>Manage Party</h1>
	</div>

	<table>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Location</th>
			<th>NB Seats</th>
			<th>Image</th>
			<th>Full ?</th>
			<th>Start DateTime</th>
			<th>End DateTime</th>
			<th>Online ?</th>
			<th>Actions</th>
		</tr>
		<?php foreach ($concerts as $k => $v): $v = current($v); ?>
		<tr>
			<td><?php echo $v['id'];?></td>
			<td><?php echo $v['name_concert'];?></td>
			<td><?php echo $v['location'];?></td>
			<td><?php echo $v['nb_seats'];?></td>
			<td><?php echo $v['image'];?></td>
			<td><?php echo $v['full']=='0'?'<span class="label-success">Places restantes</span>':'<span class="label-important">Complet</span>';?></td>
			<td><?php echo $v['start_datetime'];?></td>
			<td><?php echo $v['end_datetime'];?></td>
			<td><?php echo $v['online']=='0'?'<span class="label-important">Offline</span>':'<span class="label-success">Online</span>';?></td>
			<td>
				<?php echo $this->Html->link("Edit", array('action' => 'edit', $v['id'])); ?> - 
				<?php echo $this->Html->link("Delete", array('action' => 'delete', $v['id']), null, "Are you sure you really want delete this party"); ?>
			</td>
		</tr>
		<?php endforeach; ?>
	</table>
	<div class="pagination"><?php echo $this->Paginator->numbers(); ?></div>
</div>
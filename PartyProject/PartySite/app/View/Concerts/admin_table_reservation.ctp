<div id="page-wrapper">        
	<div class="page-header">
		<h1>Manage Reservations</h1>
	</div>

	<table>
		<tr>
			<th>ID</th>
			<th>ID Client</th>
			<th>ID Concert</th>
			<th>ID Tarif</th>
			<th>Scan ?</th>
			<th>Actions</th>
		</tr>
		<?php foreach ($reserv as $k => $v): $v = current($v); ?>
		<tr>
			<td><?php echo $v['id'];?></td>
			<td><?php echo $v['id_client'];?></td>
			<td><?php echo $v['id_concert'];?></td>
			<td><?php echo $v['id_tarif'];?></td>
			<td><?php echo $v['scan']=='0'?'<span class="label-important">Not Scanned</span>':'<span class="label-success">Scanned</span>';?></td>
			<td>
				<?php echo $this->Html->link("Edit", array('action' => 'reserv_edit', $v['id'])); ?> - 
				<?php echo $this->Html->link("Delete", array('action' => 'reserv_delete', $v['id']), null, "Are you sure you really want delete this party"); ?>
			</td>
		</tr>
		<?php endforeach; ?>
	</table>
	<div class="pagination"><?php echo $this->Paginator->numbers(); ?></div>
</div>
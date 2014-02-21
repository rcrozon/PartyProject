<div id="page-wrapper">        
	<div class="page-header">
		<h1>Manage Tariff(s) for party '<?php echo $partyName; ?>'</h1>
	</div>

	<table>
		<tr>
			<th>ID</th>
			<th>Label</th>
			<th>Price</th>
			<th>Actions</th>
		</tr>
		<?php foreach ($tariffs as $k => $v): $v = current($v); ?>
		<tr>
			<td><?php echo $v['Tarif']['id'];?></td>
			<td><?php echo $v['Tarif']['label'];?></td>
			<td><?php echo $v['Tarif']['price'];?></td>
			<td>
				<?php echo $this->Html->link("Edit", array('action' => 'tarif_edit', $v['Tarif']['id'], $partyName, $idConcert)); ?> - 
				<?php echo $this->Html->link("Delete", array('action' => 'tarif_delete', $v['Tarif']['id']), null, 
					"Are you sure you really want delete this tarif"); ?>
			</td>
		</tr>
		<?php endforeach; ?>
	</table>
	<div style="padding-top:30px;">
		<?php echo $this->Html->link('Back To Party List', array('action' => 'table_concert'), array('class' => 'btn btn-primary')); ?>
	</div>
	<!--<div class="pagination"><?php echo $this->Paginator->numbers(); ?></div>-->
</div>
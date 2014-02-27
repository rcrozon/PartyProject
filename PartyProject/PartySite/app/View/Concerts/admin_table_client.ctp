<div id="page-wrapper">        
	<div class="page-header">
		<h1>Manage Clients</h1>
	</div>

	<table>
		<tr>
			<th>ID</th>
			<th>Username</th>
			<th>Mail</th>
			<th>FirstName</th>
			<th>LastName</th>
			<th>Admin ?</th>
			<th>Actions</th>
		</tr>
		<?php foreach ($client as $k => $v): $v = current($v); ?>
		<tr>
			<td><?php echo $v['id'];?></td>
			<td><?php echo $v['username'];?></td>
			<td><?php echo $v['mail'];?></td>
			<td><?php echo $v['first_name'];?></td>
			<td><?php echo $v['last_name'];?></td>
			<td><?php echo $v['admin']=='0'?'<span class="label-important">Client</span>':'<span class="label-success">Admin</span>';?></td>
			<td>
				<?php echo $this->Html->link("Edit", array('action' => 'client_table_edit', $v['id'])); ?> - 
				<?php echo $this->Html->link("Delete", array('action' => 'client_table_delete', $v['id']), null, 
					"Are you sure you really want delete this party"); ?>
			</td>
		</tr>
		<?php endforeach; ?>
	</table>
	<div class="pagination"><?php echo $this->Paginator->numbers(); ?></div>
</div>
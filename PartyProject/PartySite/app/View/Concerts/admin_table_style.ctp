<div id="page-wrapper">        
	<div class="page-header">
		<h1>Manage Styles</h1>
	</div>

	<table>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Actions</th>
		</tr>
		<?php foreach ($style as $k => $v): $v = current($v); ?>
		<tr>
			<td><?php echo $v['id'];?></td>
			<td><?php echo $v['name'];?></td>
			<td>
				<?php echo $this->Html->link("Edit", array('action' => 'style_table_edit', $v['id'])); ?> - 
				<?php echo $this->Html->link("Delete", array('action' => 'style_table_delete', $v['id']), null, "Are you sure you really want delete this style"); ?>
			</td>
		</tr>
		<?php endforeach; ?>
	</table>
	<div class="pagination"><?php echo $this->Paginator->numbers(); ?></div>
	<div style="padding-top:30px;">
		<?php echo $this->Html->link('Add Style', array('action' => 'add_table_style'), array('class' => 'btn btn-primary')); ?>
	</div>
</div>
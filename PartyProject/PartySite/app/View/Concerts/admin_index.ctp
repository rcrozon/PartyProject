<div id="page-wrapper">        
	<div class="page-header">
		<h1>Dashboard</h1>
	</div>

  <div class="row">
    <div class="col-lg-12">
      <h1>Charts <small>Main Datas</small></h1>
      <ol class="breadcrumb">
        <li>
          <?php echo $this->Html->link('<i class="fa fa-dashboard"></i> Dashboard',
            array('action'=>'index','controller'=>'concerts'), array('escape' => false)); ?>
          <!--<a href=""><i class="fa fa-dashboard"></i> Dashboard</a>-->
        </li>
        <li class="active"><i class="fa fa-bar-chart-o"></i> Charts</li>
      </ol>
      <div class="alert alert-info alert-dismissable">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        There are two options for charts: <a href="http://www.flotcharts.org/" target="_blank" class="alert-link">Flot charts</a> and <a href="http://www.oesmith.co.uk/morris.js/" class="alert-link" target="_blank">morris.js</a>. Choose which one best suits your needs, and make sure to master the documentation to get the most out of these charts!
      </div>
    </div>
  </div><!-- /.row -->

  <div class="sortable row-fluid" style="border-bottom:10px solid #eee;padding-bottom:30px;">
    <a data-rel="tooltip" title="<?php echo $nbTodayNewClient; ?> new members today." class="well span3 top-block" href="#">
      <span class="icon32 icon-red icon-user"></span>
      <div>Total Members</div>
      <div><?php echo $nbClient; ?></div>
      <span class="notification"><?php echo $nbTodayNewClient; ?></span>
    </a>

    <a data-rel="tooltip" title="<?php echo $nbTodayNewAdmin; ?> new admin members today." class="well span3 top-block" href="#">
      <span class="icon32 icon-color icon-star-on"></span>
      <div>Admin Members</div>
      <div><?php echo $nbAdmin; ?></div>
      <span class="notification green"><?php echo $nbTodayNewAdmin; ?></span>
    </a>

    <a data-rel="tooltip" title="<?php echo $nbTodaySales; ?> € new sales today." class="well span3 top-block" href="#">
      <span class="icon32 icon-color icon-cart"></span>
      <div>Sales</div>
      <div><?php echo $sales; ?> €</div>
      <span class="notification yellow"><?php echo $nbTodaySales; ?> €</span>
    </a>

    <a data-rel="tooltip" title="<?php echo $nbTodayNewParty; ?> new party today." class="well span3 top-block" href="#">
      <span class="icon32 icon-blue icon-audio"></span>
      <div>Total Party</div>
      <div><?php echo $nbParty; ?></div>
      <span class="notification red"><?php echo $nbTodayNewParty; ?></span>
    </a>
  </div>

  <div class="row" style="margin-top:30px;">
    <div class="col-lg-4">
      <div class="panel panel-primary">
        <div class="panel-heading">
          <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i><center>- Donut Chart -<br/><i>Percentage of scanned reservations</i></center></h3>
        </div>
        <div class="panel-body">
          <div id="donutchart" style="height: 250px;"></div>
          <div class="text-right">
            <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
      </div>
    </div>

    <div class="col-lg-8">
      <div class="panel panel-primary">
        <div class="panel-heading">
          <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i><center>- Area Line Graph -<br/><i>Evolution of sales during this month</i></center></h3>
        </div>
        <div class="panel-body">
          <div id="areachart" style="height: 250px;"></div>
          <div class="text-right">
            <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
  new Morris.Donut({
  element: 'donutchart',
  data: [
    {value: <?php echo $percentScanned; ?>, label: 'Scanned'},
    {value: <?php echo $percentNotScanned; ?>, label: 'Not Scanned'}
    /*{value: 10, label: 'baz'},
    {value: 5, label: 'A really really long label'}*/
  ],
  formatter: function (x) { return x + "%"}
}).on('click', function(i, row){
  console.log(i, row);
});
</script>

<!--<script type="text/javascript">
  new Morris.Area({
  element: 'areachart',
  data: [
    { y: '2006', a: 100, b: 90 },
    { y: '2007', a: 75,  b: 65 },
    { y: '2008', a: 50,  b: 40 },
    { y: '2009', a: 75,  b: 65 },
    { y: '2010', a: 50,  b: 40 },
    { y: '2011', a: 75,  b: 65 },
    { y: '2012', a: 100, b: 90 }
  ],
  xkey: 'y',
  ykeys: ['a', 'b'],
  labels: ['Series A', 'Series B']
});
</script>-->

<script type="text/javascript">
  /* data stolen from http://howmanyleft.co.uk/vehicle/jaguar_'e'_type */
  /*var day_data = [
    {"period": "<?php echo $year;?>-<?php echo $month;?>-01", "nbSales": 100},
    {"period": "<?php echo $year;?>-<?php echo $month;?>-05", "nbSales": 75},
    {"period": "<?php echo $year;?>-<?php echo $month;?>-10", "nbSales": 50},
    {"period": "<?php echo $year;?>-<?php echo $month;?>-15", "nbSales": 75},
    {"period": "<?php echo $year;?>-<?php echo $month;?>-20", "nbSales": 50},
    {"period": "<?php echo $year;?>-<?php echo $month;?>-25", "nbSales": 75},
    {"period": "<?php echo $year;?>-<?php echo $month;?>-31", "nbSales": 100}
  ];*/

  var day_data = [<?php echo $dataDay; ?>];

  new Morris.Area({
    element: 'areachart',
    data: day_data,
    xkey: 'period',
    ykeys: ['nbSales'],
    labels: ['Sales :'],
    xLabelAngle: 60
  });
</script>
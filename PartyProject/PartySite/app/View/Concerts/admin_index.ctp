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

    <div class="col-lg-4">
      <div class="panel panel-primary">
        <div class="panel-heading">
          <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i><center>- Stats -<br/><i>Top 5 Best Sellers</i></center></h3>
        </div>
        <div class="panel-body">
          <div style="height: 250px;"><?php echo $top5; ?></div>
          <div class="text-right">
            <a href="#">View Details <i class="fa fa-arrow-circle-right"></i></a>
          </div>
        </div>
      </div>
    </div>

    <div class="col-lg-4">
      <div class="panel panel-primary">
        <div class="panel-heading">
          <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i><center>- Moving Line Chart -<br/><i>RealTime</i></center></h3>
        </div>
        <div class="panel-body">
          <div id="placeholder" style="height: 250px;"></div>
          <div class="text-right" style="font-style:italic;font-size:smaller;">
            Time between updates: <input id="updateInterval" type="text" value="" 
              style="text-align:right;width:5em;margin:0;padding:0;height:20px;"> milliseconds
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="row" style="margin-top:30px;">
    <div class="col-lg-12">
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

<script type="text/javascript">
  new Morris.Donut({
  element: 'donutchart',
  data: [
    {value: <?php echo $percentScanned; ?>, label: 'Scanned'},
    {value: <?php echo $percentNotScanned; ?>, label: 'Not Scanned'}
  ],
  formatter: function (x) { return x + "%"}
}).on('click', function(i, row){
  console.log(i, row);
});
</script>

<script type="text/javascript">
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

<script type="text/javascript">
    $(function() {
      // We use an inline data source in the example, usually data would
      // be fetched from a server
      var data = [], totalPoints = 300;

      function getRandomData() {
        if (data.length > 0)
          data = data.slice(1);
        // Do a random walk
        while (data.length < totalPoints) {
          var prev = data.length > 0 ? data[data.length - 1] : 50,
            y = prev + Math.random() * 10 - 5;
          if (y < 0) {
            y = 0;
          } else if (y > 100) {
            y = 100;
          }
          data.push(y);
        }
        // Zip the generated y values with the x values
        var res = [];
        for (var i = 0; i < data.length; ++i) {
          res.push([i, data[i]])
        }
        return res;
      }
      // Set up the control widget
      var updateInterval = 30;
      $("#updateInterval").val(updateInterval).change(function () {
        var v = $(this).val();
        if (v && !isNaN(+v)) {
          updateInterval = +v;
          if (updateInterval < 1) {
            updateInterval = 1;
          } else if (updateInterval > 2000) {
            updateInterval = 2000;
          }
          $(this).val("" + updateInterval);
        }
      });
      var plot = $.plot("#placeholder", [ getRandomData() ], {
        series: {
          shadowSize: 0 // Drawing is faster without shadows
        },
        lines: {
          fill: true
        },
        yaxis: {
          min: 0,
          max: 100
        },
        xaxis: {
          show: false
        }
      });
      function update() {
        plot.setData([getRandomData()]);
        // Since the axes don't change, we don't need to call plot.setupGrid()
        plot.draw();
        setTimeout(update, updateInterval);
      }
      update();
      // Add the Flot version string to the footer
      $("#footer").prepend("Flot " + $.plot.version + " &ndash; ");
    });
  </script>
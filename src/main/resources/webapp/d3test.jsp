<!doctype html>
<head>
    <meta charset="utf-8">
    <title>d3 and Angular</title>
    <meta name="description" content="d3 and Angular">
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css">
</head>
<body ng-app="myApp">
<div class="row">
    <div class="col-md-6 col-md-offset-3">Resize the page or change the numbers to see the d3 re-render!</div>
</div>
<div class="row">
    <div class="col-md-6 col-md-offset-3"><hr></div>
</div>
<div class="row">
    <div class="col-md-6 col-md-offset-3" ng-controller="DemoCtrl">
        <span>{{title}}</span>
        <d3-bars data="d3Data" label="name" on-click="d3OnClick(item)"></d3-bars>
        <br>
        <br>
        <span>First Item</span>
        <input type="text" ng-model="d3Data[0].score"></input>
        <br>
        <span>Second Item</span>
        <input type="text" ng-model="d3Data[1].score"></input>
        <br>
        <span>Third Item</span>
        <input type="text" ng-model="d3Data[2].score"></input>
    </div>
</div>
<div class="row">
    <div class="col-md-6 col-md-offset-3"><hr></div>
</div>
<div class="row">
    <div class="col-md-6 col-md-offset-3" ng-controller="DemoCtrl2">
        <span>{{title}}</span>
        <d3-bars data="d3Data" label="title"></d3-bars>
        <br>
        <br>
        <span>First Item</span>
        <input type="text" ng-model="d3Data[0].score"></input>
        <br>
        <span>Second Item</span>
        <input type="text" ng-model="d3Data[1].score"></input>
        <br>
        <span>Third Item</span>
        <input type="text" ng-model="d3Data[2].score"></input>
    </div>
</div>

<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.1.5/angular.min.js"></script>
<script src="javascript/data/visualization/app.js"></script>
<script src="javascript/data/visualization/services/d3.js"></script>
<script src="javascript/data/visualization/controllers/demoCtrl.js"></script>
<script src="javascript/data/visualization/controllers/demoCtrl2.js"></script>
<script src="javascript/data/visualization/directives/d3Basic.js"></script>

</body>
</html>
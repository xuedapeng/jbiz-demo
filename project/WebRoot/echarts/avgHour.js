
$(window).load(function() {
  showChartInst();
  searchData();
  connectWs();
  signal_countdown();
});


var _timeline = ['05-17 01', '05-17 02','05-17 03','05-17 04','05-17 05','05-17 06'];
var _temperature = [820, 932, 901, 934, 1290, 1330, 1320];
var _humidity = [520, 532, 401, 934, 1090, 1310, 320];
var _instant_temperature = 0;
var _instant_humidity = 0;

var _ws_status = "close";

var searchData = function() {

      var url = "http://service.bz12306.com:8081/jbizdemo/api/gps/gpsRetrieve.do";
      var data = {"carId":1, "avgField":"hour"};
  	  $.ajax({

  	        type    : "POST",
  	        url     : url,
  	        data    : data,
  	        dataType: "json",
  	        timeout : 120000,
  	        success : function(response, ex){
              if (response.status < 0) {
                $("#message").text(response.msg);
                return;
              }

              var resultList = response.result;

              var j = 0
              for (var i=resultList.length-1; i>-1; i--) {
                  var item = resultList[i];
                  _timeline[j] = item[0].replace("2018-", "");
                  _temperature[j] = item[1];
                  _humidity[j] = item[2];
                  j++;
              }

              showChartHist();

  	        },
  	        error   : function(request, status, ex){
  	        	var err = "ajaxPost error!\t(status:"+status+", exception:" + ex + ")";
  	        	console.log(err);
  	        }
  	   });


}


var showChartHist = function() {
    // 基于准备好的dom，初始化echarts实例
    var myChartHist = echarts.init(document.getElementById('history'));

    // 使用刚指定的配置项和数据显示图表。
    myChartHist.setOption(optionHist);
};

var myChartInst;
var showChartInst = function() {
    // 基于准备好的dom，初始化echarts实例
    myChartInst = echarts.init(document.getElementById('instant'));

    // 使用刚指定的配置项和数据显示图表。
    myChartInst.setOption(optionInst);
};

var updateChartInst = function() {


  optionInst.series[0].data[0].value = _instant_temperature;
  optionInst.series[1].data[0].value = _instant_humidity;
  myChartInst.setOption(optionInst);
}

function sleep(numberMillis) {
    var now = new Date();
    var exitTime = now.getTime() + numberMillis;
    while (true) {
        now = new Date();
        if (now.getTime() > exitTime)
        return;
        }
}

optionHist = {
    legend: {
        data:['温度','湿度']
    },
    tooltip: {
        trigger: 'axis'
    },
      xAxis: {
          type: 'category',
          data: _timeline
      },
      yAxis:[
        {
          name: '温度(°C)',
          type: 'value',
          max: 40,
          min:20

        },
        {
          name: '湿度(RH%)',
          type: 'value',
          max: 70,
          min: 30

        },
      ],
    dataZoom: [{
        type: 'inside',
        start: 0,
        end: 100
    }, {
        start: 0,
        end: 100,
        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
        handleSize: '80%',
        handleStyle: {
            color: '#fff',
            shadowBlur: 3,
            shadowColor: 'rgba(0, 0, 0, 0.6)',
            shadowOffsetX: 2,
            shadowOffsetY: 2
        }
    }],
      series: [
        {
          name:'温度',
          data: _temperature,
          type: 'line',
          smooth: true
        },
        {
          name:'湿度',
          data: _humidity,
          type: 'line',
          smooth: true,
          yAxisIndex:1
        },

      ]
  };



  optionInst = {
      backgroundColor: '#1b1b1b',
      tooltip : {
          formatter: "{a} <br/>{c} {b}"
      },
      toolbox: {
          show : true,
          feature : {
              mark : {show: true},
              saveAsImage : {show: true}
          }
      },
      series : [
          {
              name:'温度',
              type:'gauge',
              center : ['30%', '50%'],
              min:0,
              max:60,
              splitNumber:6,
              radius: '80%',
              axisLine: {            // 坐标轴线
                  lineStyle: {       // 属性lineStyle控制线条样式
                      color: [[0.33, '#1e90ff'],[0.5, 'lime'],[0.66, '#ff4500'],[1, 'red']],
                      width: 3,
                      shadowColor : '#fff', //默认透明
                      shadowBlur: 10
                  }
              },
              axisLabel: {            // 坐标轴小标记
                  textStyle: {       // 属性lineStyle控制线条样式
                      fontWeight: 'bolder',
                      color: '#fff',
                      shadowColor : '#fff', //默认透明
                      shadowBlur: 10
                  }
              },
              axisTick: {            // 坐标轴小标记
                  length :15,        // 属性length控制线长
                  lineStyle: {       // 属性lineStyle控制线条样式
                      color: 'auto',
                      shadowColor : '#fff', //默认透明
                      shadowBlur: 10
                  }
              },
              splitLine: {           // 分隔线
                  length :25,         // 属性length控制线长
                  lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                      width:3,
                      color: '#fff',
                      shadowColor : '#fff', //默认透明
                      shadowBlur: 10
                  }
              },
              pointer: {           // 分隔线
                  shadowColor : '#fff', //默认透明
                  shadowBlur: 5
              },
              title : {
                  textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                      fontWeight: 'bolder',
                      fontSize: 20,
                      fontStyle: 'italic',
                      color: '#fff',
                      shadowColor : '#fff', //默认透明
                      shadowBlur: 10
                  }
              },
              detail : {
                  backgroundColor: 'rgba(30,144,255,0.8)',
                  borderWidth: 1,
                  borderColor: '#fff',
                  shadowColor : '#fff', //默认透明
                  shadowBlur: 5,
                  offsetCenter: [0, '50%'],       // x, y，单位px
                  textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                      fontWeight: 'bolder',
                      color: '#fff'
                  }
              },
              data:[{value: _instant_temperature, name: '温度°C'}]
          },
          {
              name:'湿度',
              type:'gauge',
              center : ['70%', '70%'],    // 默认全局居中
              radius : '60%',
              min:20,
              max:60,
              startAngle:165,
              endAngle:15,
              splitNumber:4,
              axisLine: {            // 坐标轴线
                  lineStyle: {       // 属性lineStyle控制线条样式
                      color: [[0.25, '#1e90ff'],[0.75, 'lime'],[1, '#ff4500']],
                      width: 2,
                      shadowColor : '#fff', //默认透明
                      shadowBlur: 10
                  }
              },
              axisLabel: {            // 坐标轴小标记
                  textStyle: {       // 属性lineStyle控制线条样式
                      fontWeight: 'bolder',
                      color: '#fff',
                      shadowColor : '#fff', //默认透明
                      shadowBlur: 10
                  }
              },
              axisTick: {            // 坐标轴小标记
                  length :12,        // 属性length控制线长
                  lineStyle: {       // 属性lineStyle控制线条样式
                      color: 'auto',
                      shadowColor : '#fff', //默认透明
                      shadowBlur: 10
                  }
              },
              splitLine: {           // 分隔线
                  length :20,         // 属性length控制线长
                  lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                      width:3,
                      color: '#fff',
                      shadowColor : '#fff', //默认透明
                      shadowBlur: 10
                  }
              },
              pointer: {
                  width:5,
                  shadowColor : '#fff', //默认透明
                  shadowBlur: 5
              },
              title : {
                  offsetCenter: [0, '-30%'],       // x, y，单位px
                  textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                      fontWeight: 'bolder',
                      fontStyle: 'italic',
                      color: '#fff',
                      shadowColor : '#fff', //默认透明
                      shadowBlur: 10
                  }
              },
              detail : {
                  //backgroundColor: 'rgba(30,144,255,0.8)',
                 // borderWidth: 1,
                  borderColor: '#fff',
                  shadowColor : '#fff', //默认透明
                  shadowBlur: 5,
                  width: 80,
                  height:30,
                  offsetCenter: [25, '20%'],       // x, y，单位px
                  textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                      fontWeight: 'bolder',
                      color: '#fff'
                  }
              },
              data:[{value: _instant_humidity, name: '湿度RH%'}]
          }
      ]
  };

  // websocket

    var ws = null ;
    var _lastRecieveTime = 0;

    function connectWs() {

      if (_ws_status != 'close') {
        return;
      }
      _ws_status  = 'connecting';

    	target = "ws://service.bz12306.com:8081/jbizdemo/zws";

	    if ('WebSocket' in window) {
	        ws = new WebSocket(target);
	    } else if ('MozWebSocket' in window) {
	        ws = new MozWebSocket(target);
	    } else {
	        alert('WebSocket is not supported by this browser.');
	    }

	    ws.onopen = function(obj){
          var signInMsg = {"msgType":"sign_in", "path":"gps"};
          ws.send(JSON.stringify(signInMsg));
	        console.info('open') ;
	        console.info(obj) ;
          log('open');
          _ws_status = "open";
	    } ;

	    ws.onclose = function (obj) {
	        console.info('close') ;
	        console.info(obj) ;
          log('close');
          _ws_status = "close";
	    } ;

      ws.onerror = function (obj) {
          log('error');
          _ws_status = "close";
      };

	    ws.onmessage = function(obj){

        	console.info(obj) ;

          var nowTime = new Date().getTime();
          if (nowTime - _lastRecieveTime < 8000) {
            return;
          }
          var data = $.parseJSON(obj.data);
          if (data.carId != 1) {
            return;
          }
          _instant_temperature = data.longitude;
          _instant_humidity = data.latitude;
          count_down = 10;

          signal_on();
          updateChartInst();

          _lastRecieveTime = nowTime;

	    } ;

    }

    // 闪烁
    var signal_color = "lime";
    var signal_count = 0;
    function signal_on(){
        var signalDiv=$(".signal");
        var signalValue=$("#signal_value");

        signalValue.text(_instant_temperature + "/" + _instant_humidity);
        signal_count++;
        if (signal_count > 5) {
          signal_count = 0;
          signalDiv.css("color","gray");
          signal_color = "gray";
          return;
        }

        if(signal_color == "lime") {
            signalDiv.css("color","gray");
            signal_color = "gray";
        }
        else{
            signalDiv.css("color","lime");
            signal_color = "lime";
        }

        setTimeout(function(){ signal_on(); }, 300);
    }

    var count_down = 10;
    function signal_countdown() {

      if (count_down < -10) {
          connectWs();
      }

      var signalCountDown=$("#signal_countdown");
      signalCountDown.text(count_down);
      count_down--;

      setTimeout(function(){ signal_countdown(); }, 1000);
    }

    function log(msg) {
      $("#message").text(msg);
    }

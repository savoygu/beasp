/*var map = new AMap.Map("container", {
    resizeEnable: true,
    center: [116.397428, 39.90923],
    zoom: 13
});*/
/*var map = new AMap.Map({});*/
//获取用户所在城市信息
/*function showCityInfo() {
    
}*/

window.onload = function() {
	//实例化城市查询类
    var citysearch = new AMap.CitySearch();
    //自动获取用户IP，返回当前城市
    citysearch.getLocalCity(function(status, result) {
        if (status === 'complete' && result.info === 'OK') {
            if (result && result.city && result.bounds) {
                var cityinfo = result.city;
                var citybounds = result.bounds;
                document.getElementById('currentPosition').innerHTML = cityinfo;
                //地图显示当前城市
                /*map.setBounds(citybounds);*/
            }
        } else {
            document.getElementById('currentPosition').innerHTML = result.info;
        }
    });
}
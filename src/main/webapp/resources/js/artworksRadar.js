/**
 * 
 * @param context
 */
function initArtworksRadar(context)
{
	loadMapByUserGeoLocation(context);
}

/**
 * 
 * @param context
 */
function loadMapByUserGeoLocation(context)
{
	if (navigator.geolocation)
	{
		navigator.geolocation.getCurrentPosition(function (position) { loadMapByPosition(context, position); });
	}
	else
	{
		console.log("Unable to get current user geolocation: assigning Venice as default.");
		
		loadMapByCoordinates(context, 45.4375, 12.335833);
	}
}

/**
 * 
 * @param context
 * @param position
 */
function loadMapByPosition(context, position)
{
	loadMapByCoordinates(context, position.coords.latitude, position.coords.longitude);
}

/**
 * 
 * @param context
 * @param longitude
 * @param latitude
 */
function loadMapByCoordinates(context, latitude, longitude)
{
	//latitude = 51.5086;
	//longitude = -0.1283;
	
	$("#currentLatitude").text(latitude);
	$("#currentLongitude").text(longitude);
	
	console.log("Loading map for " + latitude + ", " + longitude);
	
	var options = 
	{
		elt : document.getElementById('artworksRadarMap'),	/* ID of element on the page where you want the map added */
		zoom : 14,											/* initial zoom level of the map */
		latLng :
		{
			lat : latitude,
			lng : longitude
		}, 													/* center of map in latitude/longitude */
		mtype : 'osm',										/* map type (osm) */
		bestFitMargin : 0,									/* margin offset from the map viewport when applying a bestfit on shapes */
		zoomOnDoubleClick : true							/* zoom in when double-clicking on map */
	};

	window.artworksRadarMap = new MQA.TileMap(options);
	
    MQA.withModule('largezoom','viewoptions','geolocationcontrol','insetmapcontrol','mousewheel', function()
    {
    	artworksRadarMap.addControl(
    		new MQA.LargeZoom(),
    		new MQA.MapCornerPlacement(MQA.MapCorner.TOP_LEFT, new MQA.Size(5,5))
    	);
   
    	artworksRadarMap.addControl(new MQA.ViewOptions());
   
    	artworksRadarMap.addControl(
    		new MQA.GeolocationControl(),
    		new MQA.MapCornerPlacement(MQA.MapCorner.TOP_RIGHT, new MQA.Size(10,50))
    	);
   
    	var options =
    	{
    		size: { width: 150, height: 125 },
    		zoom: 3,
    		mapType: 'osmsat',
    		minimized: false
    	};
   
    	artworksRadarMap.addControl(
    		new MQA.InsetMapControl(options),
    		new MQA.MapCornerPlacement(MQA.MapCorner.BOTTOM_RIGHT)
    	);
   
    	artworksRadarMap.enableMouseWheelZoom();
	});
    
    addPoi(latitude, longitude, context + '/resources/images/poi-you.png', '<img style="max-height: 250px;" src="http://www.vggallery.com/painting/f_0365v.jpg">');
}

/**
 * 
 * @param context
 */
function findMuseumsWithinDistance(context)
{
	var latitude     = $("#travelToLatitude").val();
	var longitude    = $("#travelToLongitude").val();
	var distanceInKm = $("#travelToWithinDistance").val();
	
	var url = context + "/museums/lon/" + longitude + "/lat/" + latitude + "/distanceInKm/" + distanceInKm;

	console.log(latitude + ", " + longitude + ", " + distanceInKm + ". URL: " + url);
	
	$.ajax(
	{
		type: "GET",
		url: url,
		dataType: "json"
	})
	.done(function(response)
	{
		showMuseums(response, context, latitude, longitude);
	});
}

/**
 * 
 * @param museums
 * @param context
 * @param latitude
 * @param longitude
 */
function showMuseums(museums, context, latitude, longitude)
{
	artworksRadarMap.setCenter({lat: latitude, lng: longitude});
	artworksRadarMap.removeAllShapes();	
	artworksRadarMap.bestFit();	
	
	for (var i = 0; i < museums.length; i++)
	{
		var museum = museums[i];
		
		console.log("Adding poi for museum: " + museum.name);
		
		addPoi(museum.latitude, museum.longitude, context + '/resources/images/poi-museum.png', '<img style="max-height: 250px;" src="http://www.vggallery.com/painting/f_0365v.jpg">');
	}
	
	artworksRadarMap.bestFit();
}

/**
 * 
 * @param latitude
 * @param longitude
 * @param icon
 * @param content
 */
function addPoi(latitude, longitude, icon, content)
{
	var poi=new MQA.Poi({ lat: latitude, lng: longitude });
	
	/*An example using the MQA.Icon constructor. This constructor takes a URL to an image, and the image's height and width.*/
	var icon=new MQA.Icon(icon, 50, 50);
	
	poi.setRolloverContent(content);
	
	/*This tells the POI to use the Icon object that was created rather than the default POI icon.*/
	poi.setIcon(icon);
	
	/*Set the shadow offset for your custom icon if necessary.*/
	poi.setShadowOffset({x:8,y:-4});
	
	/*This will add the POI to the map in the map's default shape collection.*/
	artworksRadarMap.addShape(poi);
}

/**
 * 
 * @param context
 * @param place
 */
function travelTo(context, place)
{
	if (place == "neo4j-sanmateo") 
	{
		//geo:37.554167,-122.313056
		
		$("#travelToLatitude").val(37.554167);
		$("#travelToLongitude").val(-122.313056);
		$("#travelToWithinDistance").val(40.0);
	}
	else if (place == "neo4j-london")
	{
		// geo:51.5086,-0.1283
		
		$("#travelToLatitude").val(51.5086);
		$("#travelToLongitude").val(-0.1283);
		$("#travelToWithinDistance").val(10.0);
	}
	else if (place == "neo4j-malmo")
	{
		// geo:55.605833,13.035833
		
		$("#travelToLatitude").val(55.605833);
		$("#travelToLongitude").val(13.035833);
		$("#travelToWithinDistance").val(100.0);
	}
	else if (place == "neo4j-desden")
	{
		// geo:51.033333,13.733333
		
		$("#travelToLatitude").val(51.033333);
		$("#travelToLongitude").val(13.733333);
		$("#travelToWithinDistance").val(250.0);
	}
	else if (place == "neo4j-venice")
	{
		// geo:45.4375,12.335833
		
		$("#travelToLatitude").val(45.4375);
		$("#travelToLongitude").val(12.335833);
		$("#travelToWithinDistance").val(500.0);
	}
	
	findMuseumsWithinDistance(context);	
}

package cn.com.ceict.www.esridemo;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.esri.arcgisruntime.geometry.GeometryEngine;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.LocationDisplay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;

public class MainActivity extends Activity {
    Button btn1;
    MapView mMapView;
    final String URL_STREET_COLD = "http://cache1.arcgisonline.cn/ArcGIS/rest/services/ChinaOnlineStreetCold/MapServer";
    GraphicsOverlay mGraphicsLayer;
    final int STATE_ADD_GRAPHIC = 1; // 进入 “添加graphics状态，这时候单击地图时操作就添加graphics
    final int STATE_SHOW = 2;// “选中graphics状态“，这时候单击地图时操作就
    // 选择一个graphics，并显示该graphics的附加信息”
    int m_State;// 状态
    LocationDisplay locationDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.map);
        String theURLString =
                "http://map.geoq.cn/arcgis/rest/services/ChinaOnlineCommunity/MapServer";
        ArcGISTiledLayer mainArcGISTiledLayer = new ArcGISTiledLayer(theURLString);
        Basemap mainBasemap = new Basemap(mainArcGISTiledLayer);
        ArcGISMap mainArcGISMap = new ArcGISMap(mainBasemap);
        locationDisplay = mMapView.getLocationDisplay();
        locationDisplay.setAutoPanMode(LocationDisplay.AutoPanMode.RECENTER );
        locationDisplay.startAsync();
        // create an initial viewpoint with a point and scale
        Point point = new Point(-226773, 6550477, SpatialReferences.getWebMercator());
        Viewpoint vp = new Viewpoint(point, 7500);

        // set initial map extent
        mainArcGISMap.setInitialViewpoint(vp);

        // set the map to be displayed in the mapview
        mMapView.setMap(mainArcGISMap);

        // create a new graphics overlay and add it to the mapview
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mMapView.getGraphicsOverlays().add(graphicsOverlay);
        android.graphics.Point mapPoint = mMapView.locationToScreen(new Point(34.3431470000,108.9396210000));
        SpatialReference spacRef = SpatialReference.create(4326);
//        com.esri.arcgisruntime.geometry.Point ltLn = (com.esri.arcgisruntime.geometry.Point)
//                GeometryEngine.project(mapPoint, spacRef ,mMapView.getSpatialReference());
        //create a simple marker symbol
        SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 12); //size 12, style of circle
        //add a new graphic with a new point geometry
        Point graphicPoint = new Point(34.3431470000, 108.9396210000, spacRef);
        Point test=(Point) GeometryEngine.project(graphicPoint, spacRef);
        Graphic graphic = new Graphic(test, symbol);
        graphicsOverlay.getGraphics().add(graphic);


    }
}

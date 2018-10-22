import {Component, OnInit} from '@angular/core';
import 'ol/ol.css';
import {Map, View, Tile, Feature} from 'ol';
import {Attribution} from 'ol/control';
import TileLayer from 'ol/layer/Tile';
import OSM from 'ol/source/OSM';
import {fromLonLat} from 'ol/proj';
import {XYZ} from 'ol/source';
import {Point} from 'ol/geom';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'MÁV Térképes Incidenstár';
  MOCenter = [19.3599821729, 47.0581535133];

  ngOnInit(): void {
    const openrailwaymap = new TileLayer({
      title: 'OpenRailwayMap',
      visible: true,
      source : new XYZ({
        attributions : [
          OSM.ATTRIBUTION,
          new Attribution({
            html : 'Style: <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA 2.0</a>' +
            '<a href="http://www.openrailwaymap.org/">OpenRailwayMap</a> and OpenStreetMap'
          })
        ],
        url : 'http://{a-c}.tiles.openrailwaymap.org/standard/{z}/{x}/{y}.png',
        crossOrigin: null, // make it work inside canvas
        tilePixelRatio: 2, // server returns 512px img for 256 tiles
        maxZoom: 19, // XYZ's default is 18
        opaque: true
      })
    });

    // TODO Megprobaltam letenni egy pint, meg nem sikerult.
    const pointFeature = new Feature(new Point(this.MOCenter));

    const map = new Map({
      target: 'map',
      layers: [
        new TileLayer({
          source: new OSM()
        })
      ],
      view: new View({
        center: fromLonLat(this.MOCenter),
        zoom: 7
      }),
      features: [pointFeature]
    });

    map.addLayer(openrailwaymap);
  }
}

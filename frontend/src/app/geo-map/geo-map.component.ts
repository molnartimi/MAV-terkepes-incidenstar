import { Component, OnInit } from '@angular/core';
import 'ol/ol.css';
import { Map, View, Tile } from 'ol';
import { Attribution } from 'ol/control';
import TileLayer from 'ol/layer/Tile';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import Feature from 'ol/Feature';
import Style from 'ol/style/Style';
import Icon from 'ol/style/Icon';
import OSM from 'ol/source/OSM';
import { fromLonLat, transform } from 'ol/proj';
import { XYZ } from 'ol/source';
import { Point } from 'ol/geom';
import {Report} from '../common/dto/report';
import {Observable, of} from 'rxjs';
import {RsApiService} from '../common/service/rs-api.service';
import {switchMap} from 'rxjs/internal/operators';

@Component({
  selector: 'app-map-component',
  templateUrl: './geo-map.component.html',
  styleUrls: ['./geo-map.component.less']
})
export class GeoMapComponent implements OnInit {

  MOCenter = [19.3599821729, 47.0581535133];
  vectorLayer: VectorLayer;
  reports: Observable<Report[]>;
  map: Map;

  constructor(private rsApiService: RsApiService) {}

  ngOnInit(): void {
    this.vectorLayer = this.createPinVectorLayer(
      [new Report('First', '100', new Date(), this.MOCenter[1], this.MOCenter[0], 'Center')]);
    this.map = this.initMap();

    // TODO update not triggered :(
    this.reports = this.rsApiService.reportSubject.pipe(
      switchMap((reports: Report[]) => {
        this.map.removeLayer(this.vectorLayer);
        this.vectorLayer = this.createPinVectorLayer(reports);
        this.map.addLayer(this.vectorLayer);
        return of(reports);
      })
    );
  }

  private initMap(): Map {
    return new Map({
      target: 'map',
      layers: [
        new TileLayer({
          source: new OSM()
        }),
        this.createOpenrailwayMapLayer(),
        this.vectorLayer
      ],
      view: new View({
        center: fromLonLat(this.MOCenter),
        zoom: 7
      })
    });
  }

  private createOpenrailwayMapLayer(): TileLayer {
    return new TileLayer({
      title: 'OpenRailwayMap',
      visible: true,
      source: new XYZ({
        attributions: [
          OSM.ATTRIBUTION,
          new Attribution({
            html: 'Style: <a href="http://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA 2.0</a>' +
            '<a href="http://www.openrailwaymap.org/">OpenRailwayMap</a> and OpenStreetMap'
          })
        ],
        url: 'http://{a-c}.tiles.openrailwaymap.org/standard/{z}/{x}/{y}.png',
        crossOrigin: null, // make it work inside canvas
        tilePixelRatio: 2, // server returns 512px img for 256 tiles
        maxZoom: 19, // XYZ's default is 18
        opaque: true
      })
    });
  }

  private createPinVectorLayer(reports: Report[]): VectorLayer {
    let vectorSource = new VectorSource();
    for (let report of reports) {
      let iconFeature = new Feature({
        geometry: new Point(transform([report.longitude, report.latitude], 'EPSG:4326',   'EPSG:3857')),
        name: report.station
      });
      vectorSource.addFeature(iconFeature);
    }
    const iconStyle = new Style({
      image: new Icon(({
        anchor: [0.5, 47],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        opacity: 0.75,
        src: 'http://openlayers.org/en/v3.9.0/examples/data/icon.png'
      }))
    });
    return new VectorLayer({source: vectorSource, style: iconStyle});
  }
}

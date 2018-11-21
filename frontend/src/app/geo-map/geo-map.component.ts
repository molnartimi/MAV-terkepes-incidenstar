import 'ol/ol.css';
import {Component, OnInit} from '@angular/core';
import {Map, View, Tile} from 'ol';
import {Attribution} from 'ol/control';
import {fromLonLat, transform} from 'ol/proj';
import {XYZ} from 'ol/source';
import {Point} from 'ol/geom';
import {ReportsForStation} from '../common/dto/reportsForStation';
import {Observable} from 'rxjs';
import {ReportService} from '../common/service/report.service';
import {click} from 'ol/events/condition';
import TileLayer from 'ol/layer/Tile';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import Feature from 'ol/Feature';
import Style from 'ol/style/Style';
import Icon from 'ol/style/Icon';
import OSM from 'ol/source/OSM';
import Select from 'ol/interaction/Select';

@Component({
  selector: 'app-map-component',
  templateUrl: './geo-map.component.html',
  styleUrls: ['./geo-map.component.less']
})
export class GeoMapComponent implements OnInit {

  MOCenter = [19.3599821729, 47.0581535133];
  vectorLayer: VectorLayer;
  reports: Observable<ReportsForStation[]>;
  map: Map;

  constructor(private reportService: ReportService) {}

  ngOnInit(): void {
    this.vectorLayer = this.createPinVectorLayer([]);
    this.map = this.initMap();
    this.setOnClickListener();

    this.reportService.allReportsSubject.subscribe(((reports: ReportsForStation[]) => {
        this.map.removeLayer(this.vectorLayer);
        this.vectorLayer = this.createPinVectorLayer(reports);
        this.map.addLayer(this.vectorLayer);
      })
    );
  }

  private initMap(): Map {
    return new Map({
      target: 'map',
      controls: [],
      layers: [
        new TileLayer({
          source: new OSM()
        }),
        this.createOpenrailwayMapLayer(),
        this.vectorLayer
      ],
      view: new View({
        center: fromLonLat(this.MOCenter),
        zoom: 7,
        minZoom: 6.5,
        maxZoom: 12
      })
    });
  }

  private createOpenrailwayMapLayer(): TileLayer {
    return new TileLayer({
      title: 'OpenRailwayMap',
      visible: true,
      source: new XYZ({
        url: 'http://{a-c}.tiles.openrailwaymap.org/standard/{z}/{x}/{y}.png',
        crossOrigin: null,
      })
    });
  }

  private createPinVectorLayer(reports: ReportsForStation[]): VectorLayer {
    let vectorSource = new VectorSource();
    for (let report of reports) {
      let iconFeature = new Feature({
        geometry: new Point(transform([report.longitude, report.latitude], 'EPSG:4326',   'EPSG:3857')),
        name: report.station
      });
      iconFeature.reportList = report;
      vectorSource.addFeature(iconFeature);
    }
    const iconStyle = new Style({
      image: new Icon(({
        anchor: [0.5, 47],
        anchorXUnits: 'fraction',
        anchorYUnits: 'pixels',
        opacity: 0.75,
        src: 'http://openlayers.org/en/latest/examples/data/icon.png'
      }))
    });
    return new VectorLayer({source: vectorSource, style: iconStyle});
  }

  private setOnClickListener(): void {
    let selectClick = new Select({
      condition: click
    });
    this.map.addInteraction(selectClick);
    selectClick.on('select', (e => this.reportService.stationSelectedSubject.next(e.selected[0].reportList)));
  }
}

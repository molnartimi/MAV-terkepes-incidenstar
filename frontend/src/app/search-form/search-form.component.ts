import {Component, OnInit} from '@angular/core';
import {SearchKind} from '../common/enum/search-kind';
import {RsApiService} from '../common/service/rs-api.service';
import {Station} from '../common/dto/station';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.less']
})
export class SearchFormComponent implements OnInit {
  type: number = SearchKind.Station;
  stations: Station[];

  constructor(private rsApiService: RsApiService) {}

  ngOnInit(): void {
    this.rsApiService.getStations().subscribe((stations: Station[]) => this.stations = stations);
  }

  search() {
    // TODO
    this.rsApiService.getReports();
  }
}

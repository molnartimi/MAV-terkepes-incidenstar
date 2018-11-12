import {Component, OnInit} from '@angular/core';
import {SearchKind} from '../common/enum/search-kind';
import {RsApiService} from '../common/service/rs-api.service';
import {SelectStationInfo, Station} from '../common/dto/station';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.less']
})
export class SearchFormComponent implements OnInit {
  type: number = SearchKind.Station;
  needDate = false;
  stationInfos: SelectStationInfo[];

  station1: SelectStationInfo;
  station2?: Station;
  fromDate: string;
  toDate: string;

  constructor(private rsApiService: RsApiService) {}

  ngOnInit(): void {
    this.rsApiService.getSelectStationInfo().subscribe((infos: SelectStationInfo[]) => this.stationInfos = infos);
  }

  showDateBlock(shouldShow: boolean) {
    if (shouldShow) {
      this.toDate = new Date().toLocaleDateString();
      let fromDate = new Date();
      fromDate.setFullYear(fromDate.getFullYear() - 1);
      this.fromDate = fromDate.toLocaleDateString();
    }
    this.needDate = shouldShow;
  }

  search() {
    this.rsApiService.getReports(
      this.station1.station.id,
      this.type === SearchKind.Road ? this.station2.id : ''//,
      //this.fromDate,
      //this.toDate);
  }
}

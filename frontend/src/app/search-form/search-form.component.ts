import {Component, OnInit} from '@angular/core';
import {SearchKind} from '../common/enum/search-kind';
import {RsApiService} from '../common/service/rs-api.service';
import {SelectStationInfo, Station} from '../common/dto/station';
import {NgbDate} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.less']
})
export class SearchFormComponent implements OnInit {
  type: number = SearchKind.Station;
  needDate = false;
  stationInfos: SelectStationInfo[];

  station1: SelectStationInfo = new SelectStationInfo(null, []);
  station2?: Station;
  fromDate?: NgbDate;
  toDate?: NgbDate;

  constructor(private rsApiService: RsApiService) {}

  ngOnInit(): void {
    this.rsApiService.getSelectStationInfo().subscribe((infos: SelectStationInfo[]) => this.stationInfos = infos.sort((a, b) => a.station.name > b.station.name ? 1 : -1));
  }

  showDateBlock(shouldShow: boolean) {
    if (shouldShow) {
      const now = new Date();
      this.toDate = new NgbDate(now.getFullYear(), now.getMonth() + 1, now.getDate());
      this.fromDate = new NgbDate(now.getFullYear(), now.getMonth() + 1, 1);
    }
    this.needDate = shouldShow;
  }

  search() {
    if (this.needDate) {
      this.fromDate = new NgbDate(this.fromDate.year, this.fromDate.month, this.fromDate.day);
      this.toDate = new NgbDate(this.toDate.year, this.toDate.month, this.toDate.day);

      const fromDate = this.fromDate.before(this.toDate) ? this.fromDate : this.toDate;
      const toDate = this.toDate.after(this.fromDate) ? this.toDate : this.fromDate;

      this.rsApiService.getReports(
        this.station1.station.id,
        this.type === SearchKind.Road ? this.station2.id : '',
        `${fromDate.year}-${fromDate.month}-${fromDate.day}`,
        `${toDate.year}-${toDate.month}-${toDate.day}`
      );
    } else {
      this.rsApiService.getReports(
        this.station1.station.id,
        this.station2 ? this.station2.id : ''
      );
    }
  }

  get isValid(): boolean {
    return ((this.station1 && this.station1.station) && (this.type !== SearchKind.Road || !!this.station2));
  }
}

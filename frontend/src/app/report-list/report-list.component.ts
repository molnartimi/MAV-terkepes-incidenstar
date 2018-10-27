import {Component, OnInit} from '@angular/core';
import {RsApiService} from '../common/service/rs-api.service';
import {Report} from '../common/dto/report';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/internal/operators';
import {Observable, of} from 'rxjs';

@Component({
  selector: 'app-report-list',
  templateUrl: './report-list.component.html',
  styleUrls: ['./report-list.component.less']
})
export class ReportListComponent implements OnInit {

  reports: Observable<Report[]>;

  constructor(private rsApiService: RsApiService) {}

  ngOnInit(): void {
    this.reports = this.rsApiService.reportSubject.pipe(
      switchMap((reports: Report[]) => of(reports))
    );
  }
}

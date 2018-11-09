import {Component, OnInit} from '@angular/core';
import {ReportsForStation} from '../common/dto/reportsForStation';
import {ReportService} from '../common/service/report.service';

@Component({
  selector: 'app-report-list',
  templateUrl: './report-list.component.html',
  styleUrls: ['./report-list.component.less']
})
export class ReportListComponent implements OnInit {

  reports: ReportsForStation;

  constructor(private reportService: ReportService) {}

  ngOnInit(): void {
    this.reportService.stationSelectedSubject.subscribe(reportsForStation => this.reports = reportsForStation);
  }
}

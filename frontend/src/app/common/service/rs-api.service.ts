import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SelectStationInfo} from '../dto/station';
import {ReportsForStation} from '../dto/reportsForStation';
import {ReportService} from './report.service';

@Injectable()
export class RsApiService {
  private api = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private reportService: ReportService) {}

  getSelectStationInfo(): Observable<SelectStationInfo[]> {
    return this.http.get<SelectStationInfo[]>(`${this.api}/getSelectStationInfo`);
  }

  getReports(stationId1: string, stationId2?: string, fromDate?: string, toDate?: string): void {
    const url = `${this.api}/getIncidents?stationId1=${stationId1}` +
      (stationId2 ? `&stationId2=${stationId2}` : '') +
      (fromDate && toDate ? `&fromDate=${fromDate}&toDate=${toDate}` : '');
    this.http.get<ReportsForStation[]>(url)
      .subscribe(reportList => this.reportService.allReportsSubject.next(reportList));
  }
}

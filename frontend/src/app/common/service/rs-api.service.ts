import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SelectStationInfo} from '../dto/station';
import {ReportsForStation, Report} from '../dto/reportsForStation';
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
      .subscribe(reportList => {
        let reportStationInfos = [];
        
        for (let i in reportList) {
          let reportInfo = reportList[i];
          let reportStationInfo = new ReportsForStation(reportInfo.station, reportInfo.longitude, reportInfo.latitude, []);
          
          for (let j in reportInfo.reports) {
            let report = reportInfo.reports[j];
            reportStationInfo.reports.push(new Report( report.id, report.link, report.publicationDate, report.title));
          }

          reportStationInfos.push(reportStationInfo);
        }        

        this.reportService.allReportsSubject.next(reportStationInfos);
      });
  }
}

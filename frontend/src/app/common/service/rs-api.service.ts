import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of} from 'rxjs';
import {SelectStationInfo, Station} from '../dto/station';
import {Report, ReportsForStation} from '../dto/reportsForStation';
import {ReportService} from './report.service';

@Injectable()
export class RsApiService {

  MOCK_STATIONS = [
    new Station('1', 'Budapest-Keleti'),
    new Station('2', 'Nagykanizsa'),
    new Station('3', 'Pécs'),
    new Station('4', 'Keszthely')
  ];

  MOCK_SELECT_INFOS = [
    new SelectStationInfo(this.MOCK_STATIONS[0], [this.MOCK_STATIONS[1], this.MOCK_STATIONS[2]]),
    new SelectStationInfo(this.MOCK_STATIONS[1], [this.MOCK_STATIONS[0], this.MOCK_STATIONS[3]]),
    new SelectStationInfo(this.MOCK_STATIONS[2], [this.MOCK_STATIONS[0], this.MOCK_STATIONS[3]]),
    new SelectStationInfo(this.MOCK_STATIONS[3], [this.MOCK_STATIONS[1], this.MOCK_STATIONS[2]])
  ];

  MOCK_REPORTS = [
    new ReportsForStation(this.MOCK_STATIONS[0], 16.9833, 46.45,
      [new Report('20', new Date(), 'Valami baj történt itt'),
              new Report('21', new Date(), 'Sok itt a baleset')]),
    new ReportsForStation(this.MOCK_STATIONS[1], 19.0831, 47.5005,
      [new Report('23', new Date(), 'Megint lerobbant egy vonat')]),
    new ReportsForStation(this.MOCK_STATIONS[2], 18.2333, 46.0667,
      [new Report('25', new Date(), 'Még egy hiba, jajj'),
        new Report('26', new Date(), 'Elütöttek egy bogarat'),
        new Report('27', new Date(), 'Felsővezetékszakadás')])
  ];

  constructor(private http: HttpClient, private reportService: ReportService) {}

  getSelectStationInfo(): Observable<SelectStationInfo[]> {
    // return this.http.get<SelectStationInfo[]>('api/getSelectStationInfo');
    return of(this.MOCK_SELECT_INFOS);
  }

  getReports(stationId1: string, stationId2?: string, fromDate?: Date, toDate?: Date): void {
    /*this.http.get<ReportsForStation[]>(`api/getIncidents?stationId1=${stationId1}` +
      (stationId2 ? `&stationId2=${stationId2}` : '') +
      (fromDate && toDate ? `&fromDate=${fromDate}&toDate=${toDate}` : ''))
      .subscribe(resportList => this.reportService.allReportsSubject.next(resportList));*/
    this.reportService.allReportsSubject.next(this.MOCK_REPORTS);
  }
}

import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of, Subject} from 'rxjs';
import {Station} from '../dto/station';
import {Report, ReportsForStation} from '../dto/reportsForStation';
import {ReportService} from './report.service';

@Injectable()
export class RsApiService {
  // TODO valid api paths
  private stationsAPI = 'api/stations';
  private reportAPI = 'api/reports';

  MOCK_STATIONS = [
    new Station('1', 'Budapest-Keleti', 16.9833, 46.45 ),
    new Station('2', 'Nagykanizsa', 19.0831, 47.5005),
    new Station('3', 'Pécs', 18.2333, 46.0667),
    new Station('4', 'Keszthely', 17.2667, 46.7667)
  ];

  MOCK_REPORTS = [
    new ReportsForStation(this.MOCK_STATIONS[0],
      [new Report('20', new Date(), 'Valami baj történt itt'),
              new Report('21', new Date(), 'Sok itt a baleset')]),
    new ReportsForStation(this.MOCK_STATIONS[1],
      [new Report('23', new Date(), 'Megint lerobbant egy vonat')]),
    new ReportsForStation(this.MOCK_STATIONS[2],
      [new Report('25', new Date(), 'Még egy hiba, jajj'),
        new Report('26', new Date(), 'Elütöttek egy bogarat'),
        new Report('27', new Date(), 'Felsővezetékszakadás')])
  ];

  constructor(private http: HttpClient, private reportService: ReportService) {}

  getStations(): Observable<Station[]> {
    // TODO remove MOCK if valid data is available
    return of(this.MOCK_STATIONS);
    // return this.http.get<Station[]>(this.stationsAPI);
  }

  getReports(): void {
    // TODO get reports for station or path
    this.reportService.allReportsSubject.next(this.MOCK_REPORTS);
    // return this.http.get<ReportsForStation[]>(this.reportAPI);
  }
}

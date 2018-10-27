import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable, of, Subject} from 'rxjs';
import {Station} from '../dto/station';
import {Report} from '../dto/report';

@Injectable()
export class RsApiService {
  // TODO valid api paths
  private stationsAPI = 'api/stations';
  private reportAPI = 'api/reports';

  public reportSubject = new Subject<Report[]>();

  MOCK_STATIONS = [
    new Station('Budapest-Keleti', '1'),
    new Station('Nagykanizsa', '2'),
    new Station('Pécs', '3'),
    new Station('Keszthely', '4')
  ];

  MOCK_REPORTS = [
    new Report('Valami baj történt itt', '1', new Date()),
    new Report('Megint lerobbant egy vonat', '2', new Date()),
    new Report('Ez nem is hiba, csak nem jól szűrtük ki!', '3', new Date())
  ];

  constructor(private http: HttpClient) {}

  getStations(): Observable<Station[]> {
    // TODO remove MOCK if valid data is available
    return of(this.MOCK_STATIONS);
    // return this.http.get<Station[]>(this.stationsAPI);
  }

  getReports(): void {
    // TODO get reports for station or path
    this.reportSubject.next(this.MOCK_REPORTS);
    // return this.http.get<Report[]>(this.reportAPI);
  }
}

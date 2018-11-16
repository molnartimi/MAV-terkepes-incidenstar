import {Injectable} from '@angular/core';
import {ReportsForStation} from '../dto/reportsForStation';
import {Subject} from 'rxjs';

@Injectable()
export class ReportService {
  public allReportsSubject: Subject<ReportsForStation[]> = new Subject();
  public stationSelectedSubject: Subject<ReportsForStation> = new Subject();
}

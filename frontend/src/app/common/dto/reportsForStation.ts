import {Station} from './station';

export class Report {
  constructor(public id: string,
              public date: Date,
              public title: string) {}
}

export class ReportsForStation {
  constructor(
    public station: Station,
    public longitude: number,
    public latitude: number,
    public reports: Report[]) {}
}


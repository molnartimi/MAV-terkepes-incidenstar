import {Station} from './station';

export class ReportsForStation {
  // TODO
  constructor(
    public station: Station,
    public reports: Report[]) {}
}

export class Report {
  constructor(public id: string, public date: Date, public title: string) {}
}


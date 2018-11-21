import {Station} from './station';

export class Report {
  constructor(public id: string,
              public link: string,
              public publicationDate: string,
              public title: string) {}

  public getDate(): string {
    let dateParts = this.publicationDate.substr(0, this.publicationDate.indexOf('T')).split('-');
    let hourParts = this.publicationDate.substr(this.publicationDate.indexOf('T') + 1).split(':');
    return `${dateParts[0]}.${dateParts[1]}.${dateParts[2]}. ${hourParts[0]}:${hourParts[1]}`;
  }
}

export class ReportsForStation {
  constructor(
    public station: Station,
    public longitude: number,
    public latitude: number,
    public reports: Report[]) {}
}


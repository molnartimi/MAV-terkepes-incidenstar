export class Station implements Searchable {
  constructor(public id: string, public name: string) {}
}

export class SelectStationInfo {
  constructor(public station: Station,
              public accessibleStations: Station[]) {}
}

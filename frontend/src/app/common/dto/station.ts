export class Station implements Searchable {
  constructor(public id: string,
              public name: string,
              public longitude: number,
              public latitude: number) {}
}

export class Station implements Searchable {
  constructor(public name: string, public id: string) {
    this.name = name;
    this.id = id;
  }
}

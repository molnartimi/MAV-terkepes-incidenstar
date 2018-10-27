import {Component, ElementRef, Input, OnInit} from '@angular/core';
import {Observable, of, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/internal/operators';

@Component({
  selector: 'app-search-input',
  templateUrl: './search-input.component.html',
  styleUrls: ['./search-input.component.less']
})
export class SearchInputComponent implements OnInit {
  @Input()
  label: string;
  @Input()
  valueList: Searchable[];
  inputValue: Searchable;

  matchedObjects: Observable<Searchable[]>;
  private searchTerms = new Subject<string>();

  ngOnInit() {
    this.inputValue = {name: ''};
    this.matchedObjects = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.filteredList(term.trim())),
    );
  }

  filteredList(term: string): Observable<Searchable[]> {
    return of(this.valueList.filter(value =>
      term && value.name.toLowerCase().indexOf(term.trim().toLowerCase()) >= 0));
  }

  findMatches(term: string) {
    this.searchTerms.next(term);
  }

  setValue(value) {
    this.inputValue = value;
    // TODO buggy after first click: input field value listed too
    this.findMatches('');
  }
}

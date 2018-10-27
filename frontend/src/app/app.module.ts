import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { GeoMapComponent } from './geo-map/geo-map.component';
import { SearchFormComponent } from './search-form/search-form.component';
import { RsApiService } from './common/service/rs-api.service';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { SearchInputComponent } from './search-input/search-input.component';
import {ReportListComponent} from './report-list/report-list.component';

@NgModule({
  declarations: [
    AppComponent,
    GeoMapComponent,
    SearchFormComponent,
    SearchInputComponent,
    ReportListComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    RsApiService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

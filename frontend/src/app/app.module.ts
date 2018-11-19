import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { GeoMapComponent } from './geo-map/geo-map.component';
import { SearchFormComponent } from './search-form/search-form.component';
import { RsApiService } from './common/service/rs-api.service';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import {ReportListComponent} from './report-list/report-list.component';
import {ReportService} from './common/service/report.service';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    GeoMapComponent,
    SearchFormComponent,
    ReportListComponent
  ],
  imports: [
    NgbModule,
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [
    RsApiService,
    ReportService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

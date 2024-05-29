import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IUrl } from '../url.model';

@Component({
  standalone: true,
  selector: 'jhi-url-detail',
  templateUrl: './url-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class UrlDetailComponent {
  url = input<IUrl | null>(null);

  previousState(): void {
    window.history.back();
  }
}

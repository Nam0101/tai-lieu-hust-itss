import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IComments } from '../comments.model';

@Component({
  standalone: true,
  selector: 'jhi-comments-detail',
  templateUrl: './comments-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class CommentsDetailComponent {
  comments = input<IComments | null>(null);

  previousState(): void {
    window.history.back();
  }
}

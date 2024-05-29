import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IUrl } from '../url.model';
import { UrlService } from '../service/url.service';

@Component({
  standalone: true,
  templateUrl: './url-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class UrlDeleteDialogComponent {
  url?: IUrl;

  protected urlService = inject(UrlService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.urlService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

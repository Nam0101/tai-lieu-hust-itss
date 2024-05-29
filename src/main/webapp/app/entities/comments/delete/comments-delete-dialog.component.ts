import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IComments } from '../comments.model';
import { CommentsService } from '../service/comments.service';

@Component({
  standalone: true,
  templateUrl: './comments-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CommentsDeleteDialogComponent {
  comments?: IComments;

  protected commentsService = inject(CommentsService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.commentsService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

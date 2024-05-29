import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ISubject } from '../subject.model';
import { SubjectService } from '../service/subject.service';

@Component({
  standalone: true,
  templateUrl: './subject-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class SubjectDeleteDialogComponent {
  subject?: ISubject;

  protected subjectService = inject(SubjectService);
  protected activeModal = inject(NgbActiveModal);

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subjectService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}

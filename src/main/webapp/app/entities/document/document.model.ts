import { ISubject } from 'app/entities/subject/subject.model';

export interface IDocument {
  id: number;
  title?: string | null;
  subject?: Pick<ISubject, 'id'> | null;
}

export type NewDocument = Omit<IDocument, 'id'> & { id: null };

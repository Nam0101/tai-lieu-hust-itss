import { IDocument } from 'app/entities/document/document.model';

export interface IUrl {
  id: number;
  driveUrl?: string | null;
  type?: string | null;
  document?: Pick<IDocument, 'id'> | null;
}

export type NewUrl = Omit<IUrl, 'id'> & { id: null };

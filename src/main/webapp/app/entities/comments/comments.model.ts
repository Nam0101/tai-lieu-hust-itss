import dayjs from 'dayjs/esm';
import { IDocument } from 'app/entities/document/document.model';

export interface IComments {
  id: number;
  createdAt?: dayjs.Dayjs | null;
  updatedAt?: dayjs.Dayjs | null;
  anonymousId?: string | null;
  document?: Pick<IDocument, 'id'> | null;
}

export type NewComments = Omit<IComments, 'id'> & { id: null };

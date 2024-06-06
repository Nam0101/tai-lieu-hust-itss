import { ISubject } from 'app/entities/subject/subject.model';

export interface IMajor {
  id: number;
  name?: string | null;
  subjects?: Pick<ISubject, 'id'>[] | null;
}

export type NewMajor = Omit<IMajor, 'id'> & { id: null };

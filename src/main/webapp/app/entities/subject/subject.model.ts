import { IMajor } from 'app/entities/major/major.model';

export interface ISubject {
  id: number;
  name?: string | null;
  code?: string | null;
  major?: Pick<IMajor, 'id'> | null;
}

export type NewSubject = Omit<ISubject, 'id'> & { id: null };

export interface IMajor {
  id: number;
  name?: string | null;
}

export type NewMajor = Omit<IMajor, 'id'> & { id: null };

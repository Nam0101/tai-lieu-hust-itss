import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'e515761b-896c-40cb-b266-1cbbf6f6dda3',
};

export const sampleWithPartialData: IAuthority = {
  name: 'e1543037-2ceb-4643-a3cb-bc3860720abd',
};

export const sampleWithFullData: IAuthority = {
  name: '1c6ec7f6-cc84-4811-b035-f54a0511ec0f',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

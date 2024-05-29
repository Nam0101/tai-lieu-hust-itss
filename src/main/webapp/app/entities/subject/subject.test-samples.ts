import { ISubject, NewSubject } from './subject.model';

export const sampleWithRequiredData: ISubject = {
  id: 25964,
};

export const sampleWithPartialData: ISubject = {
  id: 596,
  name: 'throughout',
  code: 'that sample towards',
};

export const sampleWithFullData: ISubject = {
  id: 26202,
  name: 'because hence',
  code: 'openly',
};

export const sampleWithNewData: NewSubject = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

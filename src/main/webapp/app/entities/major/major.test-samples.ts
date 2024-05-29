import { IMajor, NewMajor } from './major.model';

export const sampleWithRequiredData: IMajor = {
  id: 25834,
};

export const sampleWithPartialData: IMajor = {
  id: 5082,
  name: 'dandelion',
};

export const sampleWithFullData: IMajor = {
  id: 20941,
  name: 'but',
};

export const sampleWithNewData: NewMajor = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

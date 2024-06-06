import { ISubject, NewSubject } from './subject.model';

export const sampleWithRequiredData: ISubject = {
  id: 596,
};

export const sampleWithPartialData: ISubject = {
  id: 3456,
  name: 'lustrous whoa',
  code: 'unabashedly roughly',
};

export const sampleWithFullData: ISubject = {
  id: 19639,
  name: 'dop till',
  code: 'waveform limping beach',
};

export const sampleWithNewData: NewSubject = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

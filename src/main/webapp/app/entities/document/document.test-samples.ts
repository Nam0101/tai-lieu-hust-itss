import { IDocument, NewDocument } from './document.model';

export const sampleWithRequiredData: IDocument = {
  id: 8397,
};

export const sampleWithPartialData: IDocument = {
  id: 1152,
  title: 'er',
};

export const sampleWithFullData: IDocument = {
  id: 20741,
  title: 'forsaken',
};

export const sampleWithNewData: NewDocument = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

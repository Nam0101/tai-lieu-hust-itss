import { IUrl, NewUrl } from './url.model';

export const sampleWithRequiredData: IUrl = {
  id: 4118,
};

export const sampleWithPartialData: IUrl = {
  id: 32162,
  driveUrl: 'before along boastfully',
};

export const sampleWithFullData: IUrl = {
  id: 21211,
  driveUrl: 'unimpressively',
  type: 'whoever',
};

export const sampleWithNewData: NewUrl = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

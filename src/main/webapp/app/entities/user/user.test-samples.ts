import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 4851,
  login: 'Wn9Q@w\\EIcj96\\?rNS1k\\TN-PQ\\8r',
};

export const sampleWithPartialData: IUser = {
  id: 5725,
  login: '-T@F4kb\\N7OCXA2\\+tRmQ-D\\tLDpb8\\?Qfa\\_w',
};

export const sampleWithFullData: IUser = {
  id: 28625,
  login: '9KAp',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

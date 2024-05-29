import dayjs from 'dayjs/esm';

import { IComments, NewComments } from './comments.model';

export const sampleWithRequiredData: IComments = {
  id: 7060,
};

export const sampleWithPartialData: IComments = {
  id: 25751,
  updatedAt: dayjs('2024-05-29T08:19'),
};

export const sampleWithFullData: IComments = {
  id: 8810,
  createdAt: dayjs('2024-05-29T07:18'),
  updatedAt: dayjs('2024-05-29T02:50'),
  anonymousId: 'c93732d1-5ff2-43ff-a1d7-dcbbdc6595bd',
};

export const sampleWithNewData: NewComments = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

import dayjs from 'dayjs/esm';

import { ITbGuide, NewTbGuide } from './tb-guide.model';

export const sampleWithRequiredData: ITbGuide = {
  id: 79178,
};

export const sampleWithPartialData: ITbGuide = {
  id: 75515,
  title: 'salmon methodologies',
  content: 'copying',
  modUser: 'sensor Uruguayo Manat',
};

export const sampleWithFullData: ITbGuide = {
  id: 31302,
  title: 'Gorgeous Synchronised United',
  content: 'Berkshire copying Light',
  status: 'Concrete withdrawal',
  link: 'bottom-line',
  regUser: 'Dynamic',
  regDate: dayjs('2023-05-18T13:42'),
  modUser: 'GB Borders withdrawal',
  modDate: dayjs('2023-05-18T22:01'),
};

export const sampleWithNewData: NewTbGuide = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

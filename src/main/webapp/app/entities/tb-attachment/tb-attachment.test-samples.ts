import dayjs from 'dayjs/esm';

import { ITbAttachment, NewTbAttachment } from './tb-attachment.model';

export const sampleWithRequiredData: ITbAttachment = {
  id: 96742,
};

export const sampleWithPartialData: ITbAttachment = {
  id: 23408,
  ext: 'Loan',
  regUser: 'Account Valleys',
  regDate: dayjs('2023-05-19T00:05'),
  modUser: 'PCI Guilder',
};

export const sampleWithFullData: ITbAttachment = {
  id: 17408,
  attType: 'end-to-end Cheese Sausages',
  name: 'Shoal',
  path: 'Plastic Rubber',
  fileSize: 22967,
  ext: 'Dollar Pants matrix',
  regUser: 'Slovenia redundant',
  regDate: dayjs('2023-05-18T17:27'),
  modUser: 'Gorgeous',
  modDate: dayjs('2023-05-18T07:09'),
};

export const sampleWithNewData: NewTbAttachment = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

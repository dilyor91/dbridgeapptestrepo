import { ITbEduMst, NewTbEduMst } from './tb-edu-mst.model';

export const sampleWithRequiredData: ITbEduMst = {
  id: 84728,
};

export const sampleWithPartialData: ITbEduMst = {
  id: 77653,
  viewCnt: 36,
};

export const sampleWithFullData: ITbEduMst = {
  id: 99671,
  viewCnt: 23719,
};

export const sampleWithNewData: NewTbEduMst = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

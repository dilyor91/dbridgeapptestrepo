import { ITbBoardViewCnt, NewTbBoardViewCnt } from './tb-board-view-cnt.model';

export const sampleWithRequiredData: ITbBoardViewCnt = {
  id: 70749,
};

export const sampleWithPartialData: ITbBoardViewCnt = {
  id: 44833,
  viewCnt: 23034,
};

export const sampleWithFullData: ITbBoardViewCnt = {
  id: 21219,
  viewCnt: 98589,
};

export const sampleWithNewData: NewTbBoardViewCnt = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

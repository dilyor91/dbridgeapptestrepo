import dayjs from 'dayjs/esm';

import { ITbBoard, NewTbBoard } from './tb-board.model';

export const sampleWithRequiredData: ITbBoard = {
  id: 82335,
};

export const sampleWithPartialData: ITbBoard = {
  id: 74300,
  publishedDate: dayjs('2023-05-18T22:50'),
  regDate: dayjs('2023-05-19T03:05'),
  modDate: dayjs('2023-05-18T07:08'),
};

export const sampleWithFullData: ITbBoard = {
  id: 13969,
  bdType: 'Brunei',
  title: 'projection Chips',
  content: 'ADP',
  publishedDate: dayjs('2023-05-19T00:10'),
  status: 'Connecticut Intelligent',
  boardOrder: 76141,
  regUser: 'sexy',
  regDate: dayjs('2023-05-19T00:15'),
  modUser: 'Gorgeous virtual',
  modDate: dayjs('2023-05-19T04:37'),
};

export const sampleWithNewData: NewTbBoard = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);

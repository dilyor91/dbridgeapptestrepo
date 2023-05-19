import dayjs from 'dayjs/esm';

export interface ITbBoard {
  id: number;
  bdType?: string | null;
  title?: string | null;
  content?: string | null;
  publishedDate?: dayjs.Dayjs | null;
  status?: string | null;
  boardOrder?: number | null;
  regUser?: string | null;
  regDate?: dayjs.Dayjs | null;
  modUser?: string | null;
  modDate?: dayjs.Dayjs | null;
}

export type NewTbBoard = Omit<ITbBoard, 'id'> & { id: null };

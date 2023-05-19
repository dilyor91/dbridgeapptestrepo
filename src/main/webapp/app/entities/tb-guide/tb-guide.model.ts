import dayjs from 'dayjs/esm';
import { ITbEduMst } from 'app/entities/tb-edu-mst/tb-edu-mst.model';

export interface ITbGuide {
  id: number;
  title?: string | null;
  content?: string | null;
  status?: string | null;
  link?: string | null;
  regUser?: string | null;
  regDate?: dayjs.Dayjs | null;
  modUser?: string | null;
  modDate?: dayjs.Dayjs | null;
  eduSeq?: Pick<ITbEduMst, 'id'> | null;
}

export type NewTbGuide = Omit<ITbGuide, 'id'> & { id: null };

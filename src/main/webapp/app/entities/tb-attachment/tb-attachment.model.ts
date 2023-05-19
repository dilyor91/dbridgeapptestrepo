import dayjs from 'dayjs/esm';
import { ITbBoard } from 'app/entities/tb-board/tb-board.model';
import { ITbGuide } from 'app/entities/tb-guide/tb-guide.model';

export interface ITbAttachment {
  id: number;
  attType?: string | null;
  name?: string | null;
  path?: string | null;
  fileSize?: number | null;
  ext?: string | null;
  regUser?: string | null;
  regDate?: dayjs.Dayjs | null;
  modUser?: string | null;
  modDate?: dayjs.Dayjs | null;
  bdSeq?: Pick<ITbBoard, 'id'> | null;
  gdSeq?: Pick<ITbGuide, 'id'> | null;
}

export type NewTbAttachment = Omit<ITbAttachment, 'id'> & { id: null };

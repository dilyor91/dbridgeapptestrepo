import { ITbBoard } from 'app/entities/tb-board/tb-board.model';

export interface ITbBoardViewCnt {
  id: number;
  viewCnt?: number | null;
  bdSeq?: Pick<ITbBoard, 'id'> | null;
}

export type NewTbBoardViewCnt = Omit<ITbBoardViewCnt, 'id'> & { id: null };

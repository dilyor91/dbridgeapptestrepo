export interface ITbEduMst {
  id: number;
  viewCnt?: number | null;
}

export type NewTbEduMst = Omit<ITbEduMst, 'id'> & { id: null };

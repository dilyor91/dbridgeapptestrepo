import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tb-board.test-samples';

import { TbBoardFormService } from './tb-board-form.service';

describe('TbBoard Form Service', () => {
  let service: TbBoardFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TbBoardFormService);
  });

  describe('Service methods', () => {
    describe('createTbBoardFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTbBoardFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bdType: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            publishedDate: expect.any(Object),
            status: expect.any(Object),
            boardOrder: expect.any(Object),
            regUser: expect.any(Object),
            regDate: expect.any(Object),
            modUser: expect.any(Object),
            modDate: expect.any(Object),
          })
        );
      });

      it('passing ITbBoard should create a new form with FormGroup', () => {
        const formGroup = service.createTbBoardFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            bdType: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            publishedDate: expect.any(Object),
            status: expect.any(Object),
            boardOrder: expect.any(Object),
            regUser: expect.any(Object),
            regDate: expect.any(Object),
            modUser: expect.any(Object),
            modDate: expect.any(Object),
          })
        );
      });
    });

    describe('getTbBoard', () => {
      it('should return NewTbBoard for default TbBoard initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTbBoardFormGroup(sampleWithNewData);

        const tbBoard = service.getTbBoard(formGroup) as any;

        expect(tbBoard).toMatchObject(sampleWithNewData);
      });

      it('should return NewTbBoard for empty TbBoard initial value', () => {
        const formGroup = service.createTbBoardFormGroup();

        const tbBoard = service.getTbBoard(formGroup) as any;

        expect(tbBoard).toMatchObject({});
      });

      it('should return ITbBoard', () => {
        const formGroup = service.createTbBoardFormGroup(sampleWithRequiredData);

        const tbBoard = service.getTbBoard(formGroup) as any;

        expect(tbBoard).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITbBoard should not enable id FormControl', () => {
        const formGroup = service.createTbBoardFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTbBoard should disable id FormControl', () => {
        const formGroup = service.createTbBoardFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

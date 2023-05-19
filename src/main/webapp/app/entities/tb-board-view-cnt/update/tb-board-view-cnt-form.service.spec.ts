import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tb-board-view-cnt.test-samples';

import { TbBoardViewCntFormService } from './tb-board-view-cnt-form.service';

describe('TbBoardViewCnt Form Service', () => {
  let service: TbBoardViewCntFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TbBoardViewCntFormService);
  });

  describe('Service methods', () => {
    describe('createTbBoardViewCntFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTbBoardViewCntFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            viewCnt: expect.any(Object),
            bdSeq: expect.any(Object),
          })
        );
      });

      it('passing ITbBoardViewCnt should create a new form with FormGroup', () => {
        const formGroup = service.createTbBoardViewCntFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            viewCnt: expect.any(Object),
            bdSeq: expect.any(Object),
          })
        );
      });
    });

    describe('getTbBoardViewCnt', () => {
      it('should return NewTbBoardViewCnt for default TbBoardViewCnt initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTbBoardViewCntFormGroup(sampleWithNewData);

        const tbBoardViewCnt = service.getTbBoardViewCnt(formGroup) as any;

        expect(tbBoardViewCnt).toMatchObject(sampleWithNewData);
      });

      it('should return NewTbBoardViewCnt for empty TbBoardViewCnt initial value', () => {
        const formGroup = service.createTbBoardViewCntFormGroup();

        const tbBoardViewCnt = service.getTbBoardViewCnt(formGroup) as any;

        expect(tbBoardViewCnt).toMatchObject({});
      });

      it('should return ITbBoardViewCnt', () => {
        const formGroup = service.createTbBoardViewCntFormGroup(sampleWithRequiredData);

        const tbBoardViewCnt = service.getTbBoardViewCnt(formGroup) as any;

        expect(tbBoardViewCnt).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITbBoardViewCnt should not enable id FormControl', () => {
        const formGroup = service.createTbBoardViewCntFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTbBoardViewCnt should disable id FormControl', () => {
        const formGroup = service.createTbBoardViewCntFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

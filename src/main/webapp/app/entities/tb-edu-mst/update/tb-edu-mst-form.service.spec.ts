import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tb-edu-mst.test-samples';

import { TbEduMstFormService } from './tb-edu-mst-form.service';

describe('TbEduMst Form Service', () => {
  let service: TbEduMstFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TbEduMstFormService);
  });

  describe('Service methods', () => {
    describe('createTbEduMstFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTbEduMstFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            viewCnt: expect.any(Object),
          })
        );
      });

      it('passing ITbEduMst should create a new form with FormGroup', () => {
        const formGroup = service.createTbEduMstFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            viewCnt: expect.any(Object),
          })
        );
      });
    });

    describe('getTbEduMst', () => {
      it('should return NewTbEduMst for default TbEduMst initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTbEduMstFormGroup(sampleWithNewData);

        const tbEduMst = service.getTbEduMst(formGroup) as any;

        expect(tbEduMst).toMatchObject(sampleWithNewData);
      });

      it('should return NewTbEduMst for empty TbEduMst initial value', () => {
        const formGroup = service.createTbEduMstFormGroup();

        const tbEduMst = service.getTbEduMst(formGroup) as any;

        expect(tbEduMst).toMatchObject({});
      });

      it('should return ITbEduMst', () => {
        const formGroup = service.createTbEduMstFormGroup(sampleWithRequiredData);

        const tbEduMst = service.getTbEduMst(formGroup) as any;

        expect(tbEduMst).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITbEduMst should not enable id FormControl', () => {
        const formGroup = service.createTbEduMstFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTbEduMst should disable id FormControl', () => {
        const formGroup = service.createTbEduMstFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

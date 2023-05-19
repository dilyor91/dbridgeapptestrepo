import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tb-guide.test-samples';

import { TbGuideFormService } from './tb-guide-form.service';

describe('TbGuide Form Service', () => {
  let service: TbGuideFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TbGuideFormService);
  });

  describe('Service methods', () => {
    describe('createTbGuideFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTbGuideFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            status: expect.any(Object),
            link: expect.any(Object),
            regUser: expect.any(Object),
            regDate: expect.any(Object),
            modUser: expect.any(Object),
            modDate: expect.any(Object),
            eduSeq: expect.any(Object),
          })
        );
      });

      it('passing ITbGuide should create a new form with FormGroup', () => {
        const formGroup = service.createTbGuideFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            title: expect.any(Object),
            content: expect.any(Object),
            status: expect.any(Object),
            link: expect.any(Object),
            regUser: expect.any(Object),
            regDate: expect.any(Object),
            modUser: expect.any(Object),
            modDate: expect.any(Object),
            eduSeq: expect.any(Object),
          })
        );
      });
    });

    describe('getTbGuide', () => {
      it('should return NewTbGuide for default TbGuide initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTbGuideFormGroup(sampleWithNewData);

        const tbGuide = service.getTbGuide(formGroup) as any;

        expect(tbGuide).toMatchObject(sampleWithNewData);
      });

      it('should return NewTbGuide for empty TbGuide initial value', () => {
        const formGroup = service.createTbGuideFormGroup();

        const tbGuide = service.getTbGuide(formGroup) as any;

        expect(tbGuide).toMatchObject({});
      });

      it('should return ITbGuide', () => {
        const formGroup = service.createTbGuideFormGroup(sampleWithRequiredData);

        const tbGuide = service.getTbGuide(formGroup) as any;

        expect(tbGuide).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITbGuide should not enable id FormControl', () => {
        const formGroup = service.createTbGuideFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTbGuide should disable id FormControl', () => {
        const formGroup = service.createTbGuideFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

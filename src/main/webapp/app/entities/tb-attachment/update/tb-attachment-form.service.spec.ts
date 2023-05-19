import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tb-attachment.test-samples';

import { TbAttachmentFormService } from './tb-attachment-form.service';

describe('TbAttachment Form Service', () => {
  let service: TbAttachmentFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TbAttachmentFormService);
  });

  describe('Service methods', () => {
    describe('createTbAttachmentFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTbAttachmentFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            attType: expect.any(Object),
            name: expect.any(Object),
            path: expect.any(Object),
            fileSize: expect.any(Object),
            ext: expect.any(Object),
            regUser: expect.any(Object),
            regDate: expect.any(Object),
            modUser: expect.any(Object),
            modDate: expect.any(Object),
            bdSeq: expect.any(Object),
            gdSeq: expect.any(Object),
          })
        );
      });

      it('passing ITbAttachment should create a new form with FormGroup', () => {
        const formGroup = service.createTbAttachmentFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            attType: expect.any(Object),
            name: expect.any(Object),
            path: expect.any(Object),
            fileSize: expect.any(Object),
            ext: expect.any(Object),
            regUser: expect.any(Object),
            regDate: expect.any(Object),
            modUser: expect.any(Object),
            modDate: expect.any(Object),
            bdSeq: expect.any(Object),
            gdSeq: expect.any(Object),
          })
        );
      });
    });

    describe('getTbAttachment', () => {
      it('should return NewTbAttachment for default TbAttachment initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createTbAttachmentFormGroup(sampleWithNewData);

        const tbAttachment = service.getTbAttachment(formGroup) as any;

        expect(tbAttachment).toMatchObject(sampleWithNewData);
      });

      it('should return NewTbAttachment for empty TbAttachment initial value', () => {
        const formGroup = service.createTbAttachmentFormGroup();

        const tbAttachment = service.getTbAttachment(formGroup) as any;

        expect(tbAttachment).toMatchObject({});
      });

      it('should return ITbAttachment', () => {
        const formGroup = service.createTbAttachmentFormGroup(sampleWithRequiredData);

        const tbAttachment = service.getTbAttachment(formGroup) as any;

        expect(tbAttachment).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITbAttachment should not enable id FormControl', () => {
        const formGroup = service.createTbAttachmentFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTbAttachment should disable id FormControl', () => {
        const formGroup = service.createTbAttachmentFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});

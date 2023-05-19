import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITbAttachment } from '../tb-attachment.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tb-attachment.test-samples';

import { TbAttachmentService, RestTbAttachment } from './tb-attachment.service';

const requireRestSample: RestTbAttachment = {
  ...sampleWithRequiredData,
  regDate: sampleWithRequiredData.regDate?.toJSON(),
  modDate: sampleWithRequiredData.modDate?.toJSON(),
};

describe('TbAttachment Service', () => {
  let service: TbAttachmentService;
  let httpMock: HttpTestingController;
  let expectedResult: ITbAttachment | ITbAttachment[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TbAttachmentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a TbAttachment', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const tbAttachment = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tbAttachment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TbAttachment', () => {
      const tbAttachment = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tbAttachment).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TbAttachment', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TbAttachment', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TbAttachment', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTbAttachmentToCollectionIfMissing', () => {
      it('should add a TbAttachment to an empty array', () => {
        const tbAttachment: ITbAttachment = sampleWithRequiredData;
        expectedResult = service.addTbAttachmentToCollectionIfMissing([], tbAttachment);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tbAttachment);
      });

      it('should not add a TbAttachment to an array that contains it', () => {
        const tbAttachment: ITbAttachment = sampleWithRequiredData;
        const tbAttachmentCollection: ITbAttachment[] = [
          {
            ...tbAttachment,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTbAttachmentToCollectionIfMissing(tbAttachmentCollection, tbAttachment);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TbAttachment to an array that doesn't contain it", () => {
        const tbAttachment: ITbAttachment = sampleWithRequiredData;
        const tbAttachmentCollection: ITbAttachment[] = [sampleWithPartialData];
        expectedResult = service.addTbAttachmentToCollectionIfMissing(tbAttachmentCollection, tbAttachment);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tbAttachment);
      });

      it('should add only unique TbAttachment to an array', () => {
        const tbAttachmentArray: ITbAttachment[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tbAttachmentCollection: ITbAttachment[] = [sampleWithRequiredData];
        expectedResult = service.addTbAttachmentToCollectionIfMissing(tbAttachmentCollection, ...tbAttachmentArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tbAttachment: ITbAttachment = sampleWithRequiredData;
        const tbAttachment2: ITbAttachment = sampleWithPartialData;
        expectedResult = service.addTbAttachmentToCollectionIfMissing([], tbAttachment, tbAttachment2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tbAttachment);
        expect(expectedResult).toContain(tbAttachment2);
      });

      it('should accept null and undefined values', () => {
        const tbAttachment: ITbAttachment = sampleWithRequiredData;
        expectedResult = service.addTbAttachmentToCollectionIfMissing([], null, tbAttachment, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tbAttachment);
      });

      it('should return initial array if no TbAttachment is added', () => {
        const tbAttachmentCollection: ITbAttachment[] = [sampleWithRequiredData];
        expectedResult = service.addTbAttachmentToCollectionIfMissing(tbAttachmentCollection, undefined, null);
        expect(expectedResult).toEqual(tbAttachmentCollection);
      });
    });

    describe('compareTbAttachment', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTbAttachment(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTbAttachment(entity1, entity2);
        const compareResult2 = service.compareTbAttachment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTbAttachment(entity1, entity2);
        const compareResult2 = service.compareTbAttachment(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTbAttachment(entity1, entity2);
        const compareResult2 = service.compareTbAttachment(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

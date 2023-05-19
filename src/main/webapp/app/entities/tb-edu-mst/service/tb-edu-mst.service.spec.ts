import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITbEduMst } from '../tb-edu-mst.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tb-edu-mst.test-samples';

import { TbEduMstService } from './tb-edu-mst.service';

const requireRestSample: ITbEduMst = {
  ...sampleWithRequiredData,
};

describe('TbEduMst Service', () => {
  let service: TbEduMstService;
  let httpMock: HttpTestingController;
  let expectedResult: ITbEduMst | ITbEduMst[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TbEduMstService);
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

    it('should create a TbEduMst', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const tbEduMst = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tbEduMst).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TbEduMst', () => {
      const tbEduMst = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tbEduMst).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TbEduMst', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TbEduMst', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TbEduMst', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTbEduMstToCollectionIfMissing', () => {
      it('should add a TbEduMst to an empty array', () => {
        const tbEduMst: ITbEduMst = sampleWithRequiredData;
        expectedResult = service.addTbEduMstToCollectionIfMissing([], tbEduMst);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tbEduMst);
      });

      it('should not add a TbEduMst to an array that contains it', () => {
        const tbEduMst: ITbEduMst = sampleWithRequiredData;
        const tbEduMstCollection: ITbEduMst[] = [
          {
            ...tbEduMst,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTbEduMstToCollectionIfMissing(tbEduMstCollection, tbEduMst);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TbEduMst to an array that doesn't contain it", () => {
        const tbEduMst: ITbEduMst = sampleWithRequiredData;
        const tbEduMstCollection: ITbEduMst[] = [sampleWithPartialData];
        expectedResult = service.addTbEduMstToCollectionIfMissing(tbEduMstCollection, tbEduMst);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tbEduMst);
      });

      it('should add only unique TbEduMst to an array', () => {
        const tbEduMstArray: ITbEduMst[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tbEduMstCollection: ITbEduMst[] = [sampleWithRequiredData];
        expectedResult = service.addTbEduMstToCollectionIfMissing(tbEduMstCollection, ...tbEduMstArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tbEduMst: ITbEduMst = sampleWithRequiredData;
        const tbEduMst2: ITbEduMst = sampleWithPartialData;
        expectedResult = service.addTbEduMstToCollectionIfMissing([], tbEduMst, tbEduMst2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tbEduMst);
        expect(expectedResult).toContain(tbEduMst2);
      });

      it('should accept null and undefined values', () => {
        const tbEduMst: ITbEduMst = sampleWithRequiredData;
        expectedResult = service.addTbEduMstToCollectionIfMissing([], null, tbEduMst, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tbEduMst);
      });

      it('should return initial array if no TbEduMst is added', () => {
        const tbEduMstCollection: ITbEduMst[] = [sampleWithRequiredData];
        expectedResult = service.addTbEduMstToCollectionIfMissing(tbEduMstCollection, undefined, null);
        expect(expectedResult).toEqual(tbEduMstCollection);
      });
    });

    describe('compareTbEduMst', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTbEduMst(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTbEduMst(entity1, entity2);
        const compareResult2 = service.compareTbEduMst(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTbEduMst(entity1, entity2);
        const compareResult2 = service.compareTbEduMst(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTbEduMst(entity1, entity2);
        const compareResult2 = service.compareTbEduMst(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

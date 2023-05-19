import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITbBoardViewCnt } from '../tb-board-view-cnt.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tb-board-view-cnt.test-samples';

import { TbBoardViewCntService } from './tb-board-view-cnt.service';

const requireRestSample: ITbBoardViewCnt = {
  ...sampleWithRequiredData,
};

describe('TbBoardViewCnt Service', () => {
  let service: TbBoardViewCntService;
  let httpMock: HttpTestingController;
  let expectedResult: ITbBoardViewCnt | ITbBoardViewCnt[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TbBoardViewCntService);
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

    it('should create a TbBoardViewCnt', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const tbBoardViewCnt = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tbBoardViewCnt).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TbBoardViewCnt', () => {
      const tbBoardViewCnt = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tbBoardViewCnt).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TbBoardViewCnt', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TbBoardViewCnt', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TbBoardViewCnt', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTbBoardViewCntToCollectionIfMissing', () => {
      it('should add a TbBoardViewCnt to an empty array', () => {
        const tbBoardViewCnt: ITbBoardViewCnt = sampleWithRequiredData;
        expectedResult = service.addTbBoardViewCntToCollectionIfMissing([], tbBoardViewCnt);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tbBoardViewCnt);
      });

      it('should not add a TbBoardViewCnt to an array that contains it', () => {
        const tbBoardViewCnt: ITbBoardViewCnt = sampleWithRequiredData;
        const tbBoardViewCntCollection: ITbBoardViewCnt[] = [
          {
            ...tbBoardViewCnt,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTbBoardViewCntToCollectionIfMissing(tbBoardViewCntCollection, tbBoardViewCnt);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TbBoardViewCnt to an array that doesn't contain it", () => {
        const tbBoardViewCnt: ITbBoardViewCnt = sampleWithRequiredData;
        const tbBoardViewCntCollection: ITbBoardViewCnt[] = [sampleWithPartialData];
        expectedResult = service.addTbBoardViewCntToCollectionIfMissing(tbBoardViewCntCollection, tbBoardViewCnt);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tbBoardViewCnt);
      });

      it('should add only unique TbBoardViewCnt to an array', () => {
        const tbBoardViewCntArray: ITbBoardViewCnt[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tbBoardViewCntCollection: ITbBoardViewCnt[] = [sampleWithRequiredData];
        expectedResult = service.addTbBoardViewCntToCollectionIfMissing(tbBoardViewCntCollection, ...tbBoardViewCntArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tbBoardViewCnt: ITbBoardViewCnt = sampleWithRequiredData;
        const tbBoardViewCnt2: ITbBoardViewCnt = sampleWithPartialData;
        expectedResult = service.addTbBoardViewCntToCollectionIfMissing([], tbBoardViewCnt, tbBoardViewCnt2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tbBoardViewCnt);
        expect(expectedResult).toContain(tbBoardViewCnt2);
      });

      it('should accept null and undefined values', () => {
        const tbBoardViewCnt: ITbBoardViewCnt = sampleWithRequiredData;
        expectedResult = service.addTbBoardViewCntToCollectionIfMissing([], null, tbBoardViewCnt, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tbBoardViewCnt);
      });

      it('should return initial array if no TbBoardViewCnt is added', () => {
        const tbBoardViewCntCollection: ITbBoardViewCnt[] = [sampleWithRequiredData];
        expectedResult = service.addTbBoardViewCntToCollectionIfMissing(tbBoardViewCntCollection, undefined, null);
        expect(expectedResult).toEqual(tbBoardViewCntCollection);
      });
    });

    describe('compareTbBoardViewCnt', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTbBoardViewCnt(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTbBoardViewCnt(entity1, entity2);
        const compareResult2 = service.compareTbBoardViewCnt(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTbBoardViewCnt(entity1, entity2);
        const compareResult2 = service.compareTbBoardViewCnt(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTbBoardViewCnt(entity1, entity2);
        const compareResult2 = service.compareTbBoardViewCnt(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

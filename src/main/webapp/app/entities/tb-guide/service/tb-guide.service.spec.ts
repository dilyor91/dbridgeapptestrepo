import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITbGuide } from '../tb-guide.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tb-guide.test-samples';

import { TbGuideService, RestTbGuide } from './tb-guide.service';

const requireRestSample: RestTbGuide = {
  ...sampleWithRequiredData,
  regDate: sampleWithRequiredData.regDate?.toJSON(),
  modDate: sampleWithRequiredData.modDate?.toJSON(),
};

describe('TbGuide Service', () => {
  let service: TbGuideService;
  let httpMock: HttpTestingController;
  let expectedResult: ITbGuide | ITbGuide[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TbGuideService);
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

    it('should create a TbGuide', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const tbGuide = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tbGuide).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TbGuide', () => {
      const tbGuide = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tbGuide).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TbGuide', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TbGuide', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TbGuide', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTbGuideToCollectionIfMissing', () => {
      it('should add a TbGuide to an empty array', () => {
        const tbGuide: ITbGuide = sampleWithRequiredData;
        expectedResult = service.addTbGuideToCollectionIfMissing([], tbGuide);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tbGuide);
      });

      it('should not add a TbGuide to an array that contains it', () => {
        const tbGuide: ITbGuide = sampleWithRequiredData;
        const tbGuideCollection: ITbGuide[] = [
          {
            ...tbGuide,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTbGuideToCollectionIfMissing(tbGuideCollection, tbGuide);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TbGuide to an array that doesn't contain it", () => {
        const tbGuide: ITbGuide = sampleWithRequiredData;
        const tbGuideCollection: ITbGuide[] = [sampleWithPartialData];
        expectedResult = service.addTbGuideToCollectionIfMissing(tbGuideCollection, tbGuide);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tbGuide);
      });

      it('should add only unique TbGuide to an array', () => {
        const tbGuideArray: ITbGuide[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const tbGuideCollection: ITbGuide[] = [sampleWithRequiredData];
        expectedResult = service.addTbGuideToCollectionIfMissing(tbGuideCollection, ...tbGuideArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tbGuide: ITbGuide = sampleWithRequiredData;
        const tbGuide2: ITbGuide = sampleWithPartialData;
        expectedResult = service.addTbGuideToCollectionIfMissing([], tbGuide, tbGuide2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tbGuide);
        expect(expectedResult).toContain(tbGuide2);
      });

      it('should accept null and undefined values', () => {
        const tbGuide: ITbGuide = sampleWithRequiredData;
        expectedResult = service.addTbGuideToCollectionIfMissing([], null, tbGuide, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tbGuide);
      });

      it('should return initial array if no TbGuide is added', () => {
        const tbGuideCollection: ITbGuide[] = [sampleWithRequiredData];
        expectedResult = service.addTbGuideToCollectionIfMissing(tbGuideCollection, undefined, null);
        expect(expectedResult).toEqual(tbGuideCollection);
      });
    });

    describe('compareTbGuide', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTbGuide(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTbGuide(entity1, entity2);
        const compareResult2 = service.compareTbGuide(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTbGuide(entity1, entity2);
        const compareResult2 = service.compareTbGuide(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTbGuide(entity1, entity2);
        const compareResult2 = service.compareTbGuide(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

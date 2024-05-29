import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUrl } from '../url.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../url.test-samples';

import { UrlService } from './url.service';

const requireRestSample: IUrl = {
  ...sampleWithRequiredData,
};

describe('Url Service', () => {
  let service: UrlService;
  let httpMock: HttpTestingController;
  let expectedResult: IUrl | IUrl[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UrlService);
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

    it('should create a Url', () => {
      const url = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(url).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Url', () => {
      const url = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(url).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Url', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Url', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Url', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUrlToCollectionIfMissing', () => {
      it('should add a Url to an empty array', () => {
        const url: IUrl = sampleWithRequiredData;
        expectedResult = service.addUrlToCollectionIfMissing([], url);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(url);
      });

      it('should not add a Url to an array that contains it', () => {
        const url: IUrl = sampleWithRequiredData;
        const urlCollection: IUrl[] = [
          {
            ...url,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUrlToCollectionIfMissing(urlCollection, url);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Url to an array that doesn't contain it", () => {
        const url: IUrl = sampleWithRequiredData;
        const urlCollection: IUrl[] = [sampleWithPartialData];
        expectedResult = service.addUrlToCollectionIfMissing(urlCollection, url);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(url);
      });

      it('should add only unique Url to an array', () => {
        const urlArray: IUrl[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const urlCollection: IUrl[] = [sampleWithRequiredData];
        expectedResult = service.addUrlToCollectionIfMissing(urlCollection, ...urlArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const url: IUrl = sampleWithRequiredData;
        const url2: IUrl = sampleWithPartialData;
        expectedResult = service.addUrlToCollectionIfMissing([], url, url2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(url);
        expect(expectedResult).toContain(url2);
      });

      it('should accept null and undefined values', () => {
        const url: IUrl = sampleWithRequiredData;
        expectedResult = service.addUrlToCollectionIfMissing([], null, url, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(url);
      });

      it('should return initial array if no Url is added', () => {
        const urlCollection: IUrl[] = [sampleWithRequiredData];
        expectedResult = service.addUrlToCollectionIfMissing(urlCollection, undefined, null);
        expect(expectedResult).toEqual(urlCollection);
      });
    });

    describe('compareUrl', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUrl(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUrl(entity1, entity2);
        const compareResult2 = service.compareUrl(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUrl(entity1, entity2);
        const compareResult2 = service.compareUrl(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUrl(entity1, entity2);
        const compareResult2 = service.compareUrl(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

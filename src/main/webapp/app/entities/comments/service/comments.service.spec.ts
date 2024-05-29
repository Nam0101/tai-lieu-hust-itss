import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IComments } from '../comments.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../comments.test-samples';

import { CommentsService, RestComments } from './comments.service';

const requireRestSample: RestComments = {
  ...sampleWithRequiredData,
  createdAt: sampleWithRequiredData.createdAt?.toJSON(),
  updatedAt: sampleWithRequiredData.updatedAt?.toJSON(),
};

describe('Comments Service', () => {
  let service: CommentsService;
  let httpMock: HttpTestingController;
  let expectedResult: IComments | IComments[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CommentsService);
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

    it('should create a Comments', () => {
      const comments = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(comments).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Comments', () => {
      const comments = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(comments).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Comments', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Comments', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Comments', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCommentsToCollectionIfMissing', () => {
      it('should add a Comments to an empty array', () => {
        const comments: IComments = sampleWithRequiredData;
        expectedResult = service.addCommentsToCollectionIfMissing([], comments);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comments);
      });

      it('should not add a Comments to an array that contains it', () => {
        const comments: IComments = sampleWithRequiredData;
        const commentsCollection: IComments[] = [
          {
            ...comments,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCommentsToCollectionIfMissing(commentsCollection, comments);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Comments to an array that doesn't contain it", () => {
        const comments: IComments = sampleWithRequiredData;
        const commentsCollection: IComments[] = [sampleWithPartialData];
        expectedResult = service.addCommentsToCollectionIfMissing(commentsCollection, comments);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comments);
      });

      it('should add only unique Comments to an array', () => {
        const commentsArray: IComments[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const commentsCollection: IComments[] = [sampleWithRequiredData];
        expectedResult = service.addCommentsToCollectionIfMissing(commentsCollection, ...commentsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const comments: IComments = sampleWithRequiredData;
        const comments2: IComments = sampleWithPartialData;
        expectedResult = service.addCommentsToCollectionIfMissing([], comments, comments2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(comments);
        expect(expectedResult).toContain(comments2);
      });

      it('should accept null and undefined values', () => {
        const comments: IComments = sampleWithRequiredData;
        expectedResult = service.addCommentsToCollectionIfMissing([], null, comments, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(comments);
      });

      it('should return initial array if no Comments is added', () => {
        const commentsCollection: IComments[] = [sampleWithRequiredData];
        expectedResult = service.addCommentsToCollectionIfMissing(commentsCollection, undefined, null);
        expect(expectedResult).toEqual(commentsCollection);
      });
    });

    describe('compareComments', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareComments(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareComments(entity1, entity2);
        const compareResult2 = service.compareComments(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareComments(entity1, entity2);
        const compareResult2 = service.compareComments(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareComments(entity1, entity2);
        const compareResult2 = service.compareComments(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISubject } from '../subject.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../subject.test-samples';

import { SubjectService } from './subject.service';

const requireRestSample: ISubject = {
  ...sampleWithRequiredData,
};

describe('Subject Service', () => {
  let service: SubjectService;
  let httpMock: HttpTestingController;
  let expectedResult: ISubject | ISubject[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(SubjectService);
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

    it('should create a Subject', () => {
      const subject = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(subject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Subject', () => {
      const subject = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(subject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Subject', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Subject', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Subject', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSubjectToCollectionIfMissing', () => {
      it('should add a Subject to an empty array', () => {
        const subject: ISubject = sampleWithRequiredData;
        expectedResult = service.addSubjectToCollectionIfMissing([], subject);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subject);
      });

      it('should not add a Subject to an array that contains it', () => {
        const subject: ISubject = sampleWithRequiredData;
        const subjectCollection: ISubject[] = [
          {
            ...subject,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSubjectToCollectionIfMissing(subjectCollection, subject);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Subject to an array that doesn't contain it", () => {
        const subject: ISubject = sampleWithRequiredData;
        const subjectCollection: ISubject[] = [sampleWithPartialData];
        expectedResult = service.addSubjectToCollectionIfMissing(subjectCollection, subject);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subject);
      });

      it('should add only unique Subject to an array', () => {
        const subjectArray: ISubject[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const subjectCollection: ISubject[] = [sampleWithRequiredData];
        expectedResult = service.addSubjectToCollectionIfMissing(subjectCollection, ...subjectArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const subject: ISubject = sampleWithRequiredData;
        const subject2: ISubject = sampleWithPartialData;
        expectedResult = service.addSubjectToCollectionIfMissing([], subject, subject2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(subject);
        expect(expectedResult).toContain(subject2);
      });

      it('should accept null and undefined values', () => {
        const subject: ISubject = sampleWithRequiredData;
        expectedResult = service.addSubjectToCollectionIfMissing([], null, subject, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(subject);
      });

      it('should return initial array if no Subject is added', () => {
        const subjectCollection: ISubject[] = [sampleWithRequiredData];
        expectedResult = service.addSubjectToCollectionIfMissing(subjectCollection, undefined, null);
        expect(expectedResult).toEqual(subjectCollection);
      });
    });

    describe('compareSubject', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSubject(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSubject(entity1, entity2);
        const compareResult2 = service.compareSubject(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSubject(entity1, entity2);
        const compareResult2 = service.compareSubject(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSubject(entity1, entity2);
        const compareResult2 = service.compareSubject(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});

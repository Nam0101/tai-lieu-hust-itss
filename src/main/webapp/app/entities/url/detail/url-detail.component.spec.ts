import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { UrlDetailComponent } from './url-detail.component';

describe('Url Management Detail Component', () => {
  let comp: UrlDetailComponent;
  let fixture: ComponentFixture<UrlDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UrlDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: UrlDetailComponent,
              resolve: { url: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(UrlDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UrlDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load url on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', UrlDetailComponent);

      // THEN
      expect(instance.url()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});

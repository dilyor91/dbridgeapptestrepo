import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TbGuideDetailComponent } from './tb-guide-detail.component';

describe('TbGuide Management Detail Component', () => {
  let comp: TbGuideDetailComponent;
  let fixture: ComponentFixture<TbGuideDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TbGuideDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tbGuide: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TbGuideDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TbGuideDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tbGuide on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tbGuide).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

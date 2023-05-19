import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TbEduMstDetailComponent } from './tb-edu-mst-detail.component';

describe('TbEduMst Management Detail Component', () => {
  let comp: TbEduMstDetailComponent;
  let fixture: ComponentFixture<TbEduMstDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TbEduMstDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tbEduMst: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TbEduMstDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TbEduMstDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tbEduMst on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tbEduMst).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

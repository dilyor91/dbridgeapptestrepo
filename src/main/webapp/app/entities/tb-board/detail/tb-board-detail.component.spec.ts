import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TbBoardDetailComponent } from './tb-board-detail.component';

describe('TbBoard Management Detail Component', () => {
  let comp: TbBoardDetailComponent;
  let fixture: ComponentFixture<TbBoardDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TbBoardDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tbBoard: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TbBoardDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TbBoardDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tbBoard on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tbBoard).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

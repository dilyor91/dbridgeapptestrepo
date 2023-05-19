import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TbBoardViewCntDetailComponent } from './tb-board-view-cnt-detail.component';

describe('TbBoardViewCnt Management Detail Component', () => {
  let comp: TbBoardViewCntDetailComponent;
  let fixture: ComponentFixture<TbBoardViewCntDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TbBoardViewCntDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tbBoardViewCnt: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TbBoardViewCntDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TbBoardViewCntDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tbBoardViewCnt on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tbBoardViewCnt).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

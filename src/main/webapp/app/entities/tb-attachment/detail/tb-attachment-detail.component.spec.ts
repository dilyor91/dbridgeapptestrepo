import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TbAttachmentDetailComponent } from './tb-attachment-detail.component';

describe('TbAttachment Management Detail Component', () => {
  let comp: TbAttachmentDetailComponent;
  let fixture: ComponentFixture<TbAttachmentDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TbAttachmentDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tbAttachment: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TbAttachmentDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TbAttachmentDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tbAttachment on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tbAttachment).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});

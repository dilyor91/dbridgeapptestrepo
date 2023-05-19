import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TbBoardViewCntService } from '../service/tb-board-view-cnt.service';

import { TbBoardViewCntComponent } from './tb-board-view-cnt.component';

describe('TbBoardViewCnt Management Component', () => {
  let comp: TbBoardViewCntComponent;
  let fixture: ComponentFixture<TbBoardViewCntComponent>;
  let service: TbBoardViewCntService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        RouterTestingModule.withRoutes([{ path: 'tb-board-view-cnt', component: TbBoardViewCntComponent }]),
        HttpClientTestingModule,
      ],
      declarations: [TbBoardViewCntComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            data: of({
              defaultSort: 'id,asc',
            }),
            queryParamMap: of(
              jest.requireActual('@angular/router').convertToParamMap({
                page: '1',
                size: '1',
                sort: 'id,desc',
              })
            ),
            snapshot: { queryParams: {} },
          },
        },
      ],
    })
      .overrideTemplate(TbBoardViewCntComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TbBoardViewCntComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TbBoardViewCntService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.tbBoardViewCnts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to tbBoardViewCntService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getTbBoardViewCntIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getTbBoardViewCntIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

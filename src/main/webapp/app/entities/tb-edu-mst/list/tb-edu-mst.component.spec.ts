import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { TbEduMstService } from '../service/tb-edu-mst.service';

import { TbEduMstComponent } from './tb-edu-mst.component';

describe('TbEduMst Management Component', () => {
  let comp: TbEduMstComponent;
  let fixture: ComponentFixture<TbEduMstComponent>;
  let service: TbEduMstService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [RouterTestingModule.withRoutes([{ path: 'tb-edu-mst', component: TbEduMstComponent }]), HttpClientTestingModule],
      declarations: [TbEduMstComponent],
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
      .overrideTemplate(TbEduMstComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TbEduMstComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TbEduMstService);

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
    expect(comp.tbEduMsts?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });

  describe('trackId', () => {
    it('Should forward to tbEduMstService', () => {
      const entity = { id: 123 };
      jest.spyOn(service, 'getTbEduMstIdentifier');
      const id = comp.trackId(0, entity);
      expect(service.getTbEduMstIdentifier).toHaveBeenCalledWith(entity);
      expect(id).toBe(entity.id);
    });
  });
});

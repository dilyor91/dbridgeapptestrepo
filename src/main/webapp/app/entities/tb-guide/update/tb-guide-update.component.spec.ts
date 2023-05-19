import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TbGuideFormService } from './tb-guide-form.service';
import { TbGuideService } from '../service/tb-guide.service';
import { ITbGuide } from '../tb-guide.model';
import { ITbEduMst } from 'app/entities/tb-edu-mst/tb-edu-mst.model';
import { TbEduMstService } from 'app/entities/tb-edu-mst/service/tb-edu-mst.service';

import { TbGuideUpdateComponent } from './tb-guide-update.component';

describe('TbGuide Management Update Component', () => {
  let comp: TbGuideUpdateComponent;
  let fixture: ComponentFixture<TbGuideUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tbGuideFormService: TbGuideFormService;
  let tbGuideService: TbGuideService;
  let tbEduMstService: TbEduMstService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TbGuideUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(TbGuideUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TbGuideUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tbGuideFormService = TestBed.inject(TbGuideFormService);
    tbGuideService = TestBed.inject(TbGuideService);
    tbEduMstService = TestBed.inject(TbEduMstService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TbEduMst query and add missing value', () => {
      const tbGuide: ITbGuide = { id: 456 };
      const eduSeq: ITbEduMst = { id: 35010 };
      tbGuide.eduSeq = eduSeq;

      const tbEduMstCollection: ITbEduMst[] = [{ id: 60422 }];
      jest.spyOn(tbEduMstService, 'query').mockReturnValue(of(new HttpResponse({ body: tbEduMstCollection })));
      const additionalTbEduMsts = [eduSeq];
      const expectedCollection: ITbEduMst[] = [...additionalTbEduMsts, ...tbEduMstCollection];
      jest.spyOn(tbEduMstService, 'addTbEduMstToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tbGuide });
      comp.ngOnInit();

      expect(tbEduMstService.query).toHaveBeenCalled();
      expect(tbEduMstService.addTbEduMstToCollectionIfMissing).toHaveBeenCalledWith(
        tbEduMstCollection,
        ...additionalTbEduMsts.map(expect.objectContaining)
      );
      expect(comp.tbEduMstsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tbGuide: ITbGuide = { id: 456 };
      const eduSeq: ITbEduMst = { id: 1472 };
      tbGuide.eduSeq = eduSeq;

      activatedRoute.data = of({ tbGuide });
      comp.ngOnInit();

      expect(comp.tbEduMstsSharedCollection).toContain(eduSeq);
      expect(comp.tbGuide).toEqual(tbGuide);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbGuide>>();
      const tbGuide = { id: 123 };
      jest.spyOn(tbGuideFormService, 'getTbGuide').mockReturnValue(tbGuide);
      jest.spyOn(tbGuideService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbGuide });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tbGuide }));
      saveSubject.complete();

      // THEN
      expect(tbGuideFormService.getTbGuide).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tbGuideService.update).toHaveBeenCalledWith(expect.objectContaining(tbGuide));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbGuide>>();
      const tbGuide = { id: 123 };
      jest.spyOn(tbGuideFormService, 'getTbGuide').mockReturnValue({ id: null });
      jest.spyOn(tbGuideService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbGuide: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tbGuide }));
      saveSubject.complete();

      // THEN
      expect(tbGuideFormService.getTbGuide).toHaveBeenCalled();
      expect(tbGuideService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbGuide>>();
      const tbGuide = { id: 123 };
      jest.spyOn(tbGuideService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbGuide });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tbGuideService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTbEduMst', () => {
      it('Should forward to tbEduMstService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tbEduMstService, 'compareTbEduMst');
        comp.compareTbEduMst(entity, entity2);
        expect(tbEduMstService.compareTbEduMst).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

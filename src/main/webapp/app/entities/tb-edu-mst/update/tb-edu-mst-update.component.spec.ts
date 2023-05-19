import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TbEduMstFormService } from './tb-edu-mst-form.service';
import { TbEduMstService } from '../service/tb-edu-mst.service';
import { ITbEduMst } from '../tb-edu-mst.model';

import { TbEduMstUpdateComponent } from './tb-edu-mst-update.component';

describe('TbEduMst Management Update Component', () => {
  let comp: TbEduMstUpdateComponent;
  let fixture: ComponentFixture<TbEduMstUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tbEduMstFormService: TbEduMstFormService;
  let tbEduMstService: TbEduMstService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TbEduMstUpdateComponent],
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
      .overrideTemplate(TbEduMstUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TbEduMstUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tbEduMstFormService = TestBed.inject(TbEduMstFormService);
    tbEduMstService = TestBed.inject(TbEduMstService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tbEduMst: ITbEduMst = { id: 456 };

      activatedRoute.data = of({ tbEduMst });
      comp.ngOnInit();

      expect(comp.tbEduMst).toEqual(tbEduMst);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbEduMst>>();
      const tbEduMst = { id: 123 };
      jest.spyOn(tbEduMstFormService, 'getTbEduMst').mockReturnValue(tbEduMst);
      jest.spyOn(tbEduMstService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbEduMst });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tbEduMst }));
      saveSubject.complete();

      // THEN
      expect(tbEduMstFormService.getTbEduMst).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tbEduMstService.update).toHaveBeenCalledWith(expect.objectContaining(tbEduMst));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbEduMst>>();
      const tbEduMst = { id: 123 };
      jest.spyOn(tbEduMstFormService, 'getTbEduMst').mockReturnValue({ id: null });
      jest.spyOn(tbEduMstService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbEduMst: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tbEduMst }));
      saveSubject.complete();

      // THEN
      expect(tbEduMstFormService.getTbEduMst).toHaveBeenCalled();
      expect(tbEduMstService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbEduMst>>();
      const tbEduMst = { id: 123 };
      jest.spyOn(tbEduMstService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbEduMst });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tbEduMstService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

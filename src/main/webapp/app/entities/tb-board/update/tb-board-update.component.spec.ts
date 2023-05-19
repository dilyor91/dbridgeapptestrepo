import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TbBoardFormService } from './tb-board-form.service';
import { TbBoardService } from '../service/tb-board.service';
import { ITbBoard } from '../tb-board.model';

import { TbBoardUpdateComponent } from './tb-board-update.component';

describe('TbBoard Management Update Component', () => {
  let comp: TbBoardUpdateComponent;
  let fixture: ComponentFixture<TbBoardUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tbBoardFormService: TbBoardFormService;
  let tbBoardService: TbBoardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TbBoardUpdateComponent],
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
      .overrideTemplate(TbBoardUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TbBoardUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tbBoardFormService = TestBed.inject(TbBoardFormService);
    tbBoardService = TestBed.inject(TbBoardService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tbBoard: ITbBoard = { id: 456 };

      activatedRoute.data = of({ tbBoard });
      comp.ngOnInit();

      expect(comp.tbBoard).toEqual(tbBoard);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbBoard>>();
      const tbBoard = { id: 123 };
      jest.spyOn(tbBoardFormService, 'getTbBoard').mockReturnValue(tbBoard);
      jest.spyOn(tbBoardService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbBoard });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tbBoard }));
      saveSubject.complete();

      // THEN
      expect(tbBoardFormService.getTbBoard).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tbBoardService.update).toHaveBeenCalledWith(expect.objectContaining(tbBoard));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbBoard>>();
      const tbBoard = { id: 123 };
      jest.spyOn(tbBoardFormService, 'getTbBoard').mockReturnValue({ id: null });
      jest.spyOn(tbBoardService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbBoard: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tbBoard }));
      saveSubject.complete();

      // THEN
      expect(tbBoardFormService.getTbBoard).toHaveBeenCalled();
      expect(tbBoardService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbBoard>>();
      const tbBoard = { id: 123 };
      jest.spyOn(tbBoardService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbBoard });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tbBoardService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});

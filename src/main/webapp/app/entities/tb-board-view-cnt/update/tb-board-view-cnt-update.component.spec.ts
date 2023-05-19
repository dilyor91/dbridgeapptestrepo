import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TbBoardViewCntFormService } from './tb-board-view-cnt-form.service';
import { TbBoardViewCntService } from '../service/tb-board-view-cnt.service';
import { ITbBoardViewCnt } from '../tb-board-view-cnt.model';
import { ITbBoard } from 'app/entities/tb-board/tb-board.model';
import { TbBoardService } from 'app/entities/tb-board/service/tb-board.service';

import { TbBoardViewCntUpdateComponent } from './tb-board-view-cnt-update.component';

describe('TbBoardViewCnt Management Update Component', () => {
  let comp: TbBoardViewCntUpdateComponent;
  let fixture: ComponentFixture<TbBoardViewCntUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tbBoardViewCntFormService: TbBoardViewCntFormService;
  let tbBoardViewCntService: TbBoardViewCntService;
  let tbBoardService: TbBoardService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TbBoardViewCntUpdateComponent],
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
      .overrideTemplate(TbBoardViewCntUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TbBoardViewCntUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tbBoardViewCntFormService = TestBed.inject(TbBoardViewCntFormService);
    tbBoardViewCntService = TestBed.inject(TbBoardViewCntService);
    tbBoardService = TestBed.inject(TbBoardService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TbBoard query and add missing value', () => {
      const tbBoardViewCnt: ITbBoardViewCnt = { id: 456 };
      const bdSeq: ITbBoard = { id: 15274 };
      tbBoardViewCnt.bdSeq = bdSeq;

      const tbBoardCollection: ITbBoard[] = [{ id: 91971 }];
      jest.spyOn(tbBoardService, 'query').mockReturnValue(of(new HttpResponse({ body: tbBoardCollection })));
      const additionalTbBoards = [bdSeq];
      const expectedCollection: ITbBoard[] = [...additionalTbBoards, ...tbBoardCollection];
      jest.spyOn(tbBoardService, 'addTbBoardToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tbBoardViewCnt });
      comp.ngOnInit();

      expect(tbBoardService.query).toHaveBeenCalled();
      expect(tbBoardService.addTbBoardToCollectionIfMissing).toHaveBeenCalledWith(
        tbBoardCollection,
        ...additionalTbBoards.map(expect.objectContaining)
      );
      expect(comp.tbBoardsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tbBoardViewCnt: ITbBoardViewCnt = { id: 456 };
      const bdSeq: ITbBoard = { id: 29550 };
      tbBoardViewCnt.bdSeq = bdSeq;

      activatedRoute.data = of({ tbBoardViewCnt });
      comp.ngOnInit();

      expect(comp.tbBoardsSharedCollection).toContain(bdSeq);
      expect(comp.tbBoardViewCnt).toEqual(tbBoardViewCnt);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbBoardViewCnt>>();
      const tbBoardViewCnt = { id: 123 };
      jest.spyOn(tbBoardViewCntFormService, 'getTbBoardViewCnt').mockReturnValue(tbBoardViewCnt);
      jest.spyOn(tbBoardViewCntService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbBoardViewCnt });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tbBoardViewCnt }));
      saveSubject.complete();

      // THEN
      expect(tbBoardViewCntFormService.getTbBoardViewCnt).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tbBoardViewCntService.update).toHaveBeenCalledWith(expect.objectContaining(tbBoardViewCnt));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbBoardViewCnt>>();
      const tbBoardViewCnt = { id: 123 };
      jest.spyOn(tbBoardViewCntFormService, 'getTbBoardViewCnt').mockReturnValue({ id: null });
      jest.spyOn(tbBoardViewCntService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbBoardViewCnt: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tbBoardViewCnt }));
      saveSubject.complete();

      // THEN
      expect(tbBoardViewCntFormService.getTbBoardViewCnt).toHaveBeenCalled();
      expect(tbBoardViewCntService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbBoardViewCnt>>();
      const tbBoardViewCnt = { id: 123 };
      jest.spyOn(tbBoardViewCntService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbBoardViewCnt });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tbBoardViewCntService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareTbBoard', () => {
      it('Should forward to tbBoardService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tbBoardService, 'compareTbBoard');
        comp.compareTbBoard(entity, entity2);
        expect(tbBoardService.compareTbBoard).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

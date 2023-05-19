import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TbAttachmentFormService } from './tb-attachment-form.service';
import { TbAttachmentService } from '../service/tb-attachment.service';
import { ITbAttachment } from '../tb-attachment.model';
import { ITbBoard } from 'app/entities/tb-board/tb-board.model';
import { TbBoardService } from 'app/entities/tb-board/service/tb-board.service';
import { ITbGuide } from 'app/entities/tb-guide/tb-guide.model';
import { TbGuideService } from 'app/entities/tb-guide/service/tb-guide.service';

import { TbAttachmentUpdateComponent } from './tb-attachment-update.component';

describe('TbAttachment Management Update Component', () => {
  let comp: TbAttachmentUpdateComponent;
  let fixture: ComponentFixture<TbAttachmentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tbAttachmentFormService: TbAttachmentFormService;
  let tbAttachmentService: TbAttachmentService;
  let tbBoardService: TbBoardService;
  let tbGuideService: TbGuideService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TbAttachmentUpdateComponent],
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
      .overrideTemplate(TbAttachmentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TbAttachmentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tbAttachmentFormService = TestBed.inject(TbAttachmentFormService);
    tbAttachmentService = TestBed.inject(TbAttachmentService);
    tbBoardService = TestBed.inject(TbBoardService);
    tbGuideService = TestBed.inject(TbGuideService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TbBoard query and add missing value', () => {
      const tbAttachment: ITbAttachment = { id: 456 };
      const bdSeq: ITbBoard = { id: 80576 };
      tbAttachment.bdSeq = bdSeq;

      const tbBoardCollection: ITbBoard[] = [{ id: 22178 }];
      jest.spyOn(tbBoardService, 'query').mockReturnValue(of(new HttpResponse({ body: tbBoardCollection })));
      const additionalTbBoards = [bdSeq];
      const expectedCollection: ITbBoard[] = [...additionalTbBoards, ...tbBoardCollection];
      jest.spyOn(tbBoardService, 'addTbBoardToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tbAttachment });
      comp.ngOnInit();

      expect(tbBoardService.query).toHaveBeenCalled();
      expect(tbBoardService.addTbBoardToCollectionIfMissing).toHaveBeenCalledWith(
        tbBoardCollection,
        ...additionalTbBoards.map(expect.objectContaining)
      );
      expect(comp.tbBoardsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TbGuide query and add missing value', () => {
      const tbAttachment: ITbAttachment = { id: 456 };
      const gdSeq: ITbGuide = { id: 94069 };
      tbAttachment.gdSeq = gdSeq;

      const tbGuideCollection: ITbGuide[] = [{ id: 31064 }];
      jest.spyOn(tbGuideService, 'query').mockReturnValue(of(new HttpResponse({ body: tbGuideCollection })));
      const additionalTbGuides = [gdSeq];
      const expectedCollection: ITbGuide[] = [...additionalTbGuides, ...tbGuideCollection];
      jest.spyOn(tbGuideService, 'addTbGuideToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tbAttachment });
      comp.ngOnInit();

      expect(tbGuideService.query).toHaveBeenCalled();
      expect(tbGuideService.addTbGuideToCollectionIfMissing).toHaveBeenCalledWith(
        tbGuideCollection,
        ...additionalTbGuides.map(expect.objectContaining)
      );
      expect(comp.tbGuidesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tbAttachment: ITbAttachment = { id: 456 };
      const bdSeq: ITbBoard = { id: 90295 };
      tbAttachment.bdSeq = bdSeq;
      const gdSeq: ITbGuide = { id: 66556 };
      tbAttachment.gdSeq = gdSeq;

      activatedRoute.data = of({ tbAttachment });
      comp.ngOnInit();

      expect(comp.tbBoardsSharedCollection).toContain(bdSeq);
      expect(comp.tbGuidesSharedCollection).toContain(gdSeq);
      expect(comp.tbAttachment).toEqual(tbAttachment);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbAttachment>>();
      const tbAttachment = { id: 123 };
      jest.spyOn(tbAttachmentFormService, 'getTbAttachment').mockReturnValue(tbAttachment);
      jest.spyOn(tbAttachmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbAttachment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tbAttachment }));
      saveSubject.complete();

      // THEN
      expect(tbAttachmentFormService.getTbAttachment).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(tbAttachmentService.update).toHaveBeenCalledWith(expect.objectContaining(tbAttachment));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbAttachment>>();
      const tbAttachment = { id: 123 };
      jest.spyOn(tbAttachmentFormService, 'getTbAttachment').mockReturnValue({ id: null });
      jest.spyOn(tbAttachmentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbAttachment: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tbAttachment }));
      saveSubject.complete();

      // THEN
      expect(tbAttachmentFormService.getTbAttachment).toHaveBeenCalled();
      expect(tbAttachmentService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ITbAttachment>>();
      const tbAttachment = { id: 123 };
      jest.spyOn(tbAttachmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tbAttachment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tbAttachmentService.update).toHaveBeenCalled();
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

    describe('compareTbGuide', () => {
      it('Should forward to tbGuideService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(tbGuideService, 'compareTbGuide');
        comp.compareTbGuide(entity, entity2);
        expect(tbGuideService.compareTbGuide).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});

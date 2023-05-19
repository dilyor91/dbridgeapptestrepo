import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { ITbBoardViewCnt } from '../tb-board-view-cnt.model';
import { TbBoardViewCntService } from '../service/tb-board-view-cnt.service';

import { TbBoardViewCntRoutingResolveService } from './tb-board-view-cnt-routing-resolve.service';

describe('TbBoardViewCnt routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TbBoardViewCntRoutingResolveService;
  let service: TbBoardViewCntService;
  let resultTbBoardViewCnt: ITbBoardViewCnt | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(TbBoardViewCntRoutingResolveService);
    service = TestBed.inject(TbBoardViewCntService);
    resultTbBoardViewCnt = undefined;
  });

  describe('resolve', () => {
    it('should return ITbBoardViewCnt returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTbBoardViewCnt = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTbBoardViewCnt).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTbBoardViewCnt = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTbBoardViewCnt).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITbBoardViewCnt>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTbBoardViewCnt = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTbBoardViewCnt).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});

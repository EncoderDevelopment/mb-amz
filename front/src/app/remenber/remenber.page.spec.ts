import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { RemenberPage } from './remenber.page';

describe('RemenberPage', () => {
  let component: RemenberPage;
  let fixture: ComponentFixture<RemenberPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RemenberPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(RemenberPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

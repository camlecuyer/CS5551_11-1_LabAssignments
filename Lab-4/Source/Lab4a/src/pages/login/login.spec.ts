import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { By }           from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { LoginPage } from './login';
import { RegisterPage } from '../register/register'
import { IonicModule, Platform, NavController, NavParams} from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { MyApp } from '../../app/app.component';

describe('LoginPage', () => {
    var registerPage = RegisterPage;
  let de: DebugElement;
  let comp: LoginPage;
  let fixture: ComponentFixture<LoginPage>;
  const data = {data: 'foo'};
  const navParams = new NavParams(data);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [MyApp, LoginPage],
      imports: [
        IonicModule.forRoot(MyApp)
      ],
      providers: [
        NavController,
        {provide: NavParams, useValue: navParams},
        {provide: StatusBar, useValue: statusbar},
      ]
    });
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginPage);
    comp = fixture.componentInstance;
  });

  it('should create component', () => expect(comp instanceof LoginPage).toBe(true));

  /*it('should navigate to registerPage', () => {
    let navCtrl = fixture.debugElement.injector.get(NavController);
    spyOn(navCtrl, 'push');
    comp.registerClick();
    expect(navCtrl.push).toHaveBeenCalledWith(registerPage);
  });*/
});

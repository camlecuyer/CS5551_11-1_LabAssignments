import { Component } from '@angular/core';
import { Platform, IonicModule } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { LoginPage } from '../pages/login/login';
@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  rootPage:any = LoginPage;
  
  pages: Array<{ title: string, component: any }>;

  constructor(platform: Platform, statusBar: StatusBar, splashScreen: SplashScreen) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      statusBar.styleDefault();
      splashScreen.hide();
          });
      this.pages = [
      { title: 'Login', component: 'LoginPage' },
      { title: 'Home', component: 'HomePage' },
      { title: 'Register', component: 'RegisterPage' }
    ];
  }
}


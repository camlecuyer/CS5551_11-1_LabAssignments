import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, AlertController } from 'ionic-angular';
import { RegisterPage } from '../register/register';
import { HomePage } from '../home/home';
import { StatusBar } from '@ionic-native/status-bar';

/**
 * Generated class for the LoginPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {
    registerPage = RegisterPage;
    homePage = HomePage;
    user = "";
    pass = "";
    private data: string;

  constructor(public navCtrl: NavController, public navParams: NavParams, public alertCtrl: AlertController, public statusBar: StatusBar) {
    this.navCtrl = navCtrl;
    this.statusBar.overlaysWebView(true);
    this.data = navParams.get('data');
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad LoginPage');
  }
  
  loginClick() {
        if ((this.user !== undefined && this.pass !== undefined) && (this.user !== "" && this.pass !== "")) {
            if (this.user === "admin" && this.pass === "grey") {
                this.navCtrl.push(this.homePage);
            } else {
                let alert = this.alertCtrl.create({
                    title: 'Login Error',
                    subTitle: 'Incorrect login or password',
                    buttons: ['ok']
                });
                alert.present();
            }
        } else {
            let alert = this.alertCtrl.create({
                    title: 'Login Error',
                    subTitle: 'Please enter a username and password',
                    buttons: ['ok']
                });
                alert.present();
        }
    }
    
    registerClick() {
        this.navCtrl.push(this.registerPage);
    }

}

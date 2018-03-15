import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, AlertController } from 'ionic-angular';
import { HomePage } from '../home/home';

/**
 * Generated class for the RegisterPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-register',
  templateUrl: 'register.html',
})
export class RegisterPage {
    homePage = HomePage;
    user = "";
    pass = "";

  constructor(public navCtrl: NavController, public navParams: NavParams, public alertCtrl: AlertController) {
    this.navCtrl = navCtrl;
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad RegisterPage');
  }
  
  registerClick() {
        if ((this.user !== undefined && this.pass !== undefined) && (this.user !== "" && this.pass !== "")) {
            this.navCtrl.push(this.homePage);
        } else {
            let alert = this.alertCtrl.create({
                    title: 'Register Error',
                    subTitle: 'Please enter a username and password',
                    buttons: ['ok']
                });
                alert.present();
        }
    }

}

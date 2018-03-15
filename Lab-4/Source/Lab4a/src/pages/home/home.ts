import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { RestApiProvider } from '../../providers/rest-api/rest-api';

/**
 * Generated class for the HomePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
})
export class HomePage {
    searchTermIn = "";
    searches: any = [];
    searchLang: any = [];

  constructor(public navCtrl: NavController, public navParams: NavParams, public apiCall: RestApiProvider) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad HomePage');
  }
  
  search()
  {
    this.apiCall.search(this.searchTermIn).then(data => {
        this.searches = data;
       
       console.log(this.searches);
    });
    
    this.apiCall.searchLang(this.searchTermIn).then(data => {
       this.searchLang = data;
       console.log(this.searchLang);
    });
  }

}

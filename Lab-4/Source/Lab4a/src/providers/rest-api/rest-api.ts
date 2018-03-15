import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import 'rxjs/add/operator/map';

/*
  Generated class for the RestApiProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class RestApiProvider {

  constructor(public http: HttpClient) {
    
  }
  
  search(searchTerm:string) {
    var service_url = 'https://kgsearch.googleapis.com/v1/entities:search?query=' + searchTerm + '&limit=10' +
        '&key=AIzaSyAGPo7uHzvrdNuUGRtxoIDjWsFZVjSw2ko&indent=True';
    
    return new Promise (resolve => {
        this.http.get(service_url).subscribe((data: any) => {
            console.log(data.itemListElement);
            resolve(data.itemListElement);
        }, err => {
            console.log(err);
        });
    });
  }
  
  searchLang(searchTerm:string) {
    var wat_url = 'https://watson-api-explorer.mybluemix.net/language-translator/api/v2/identify?text=' + searchTerm;
    
    return new Promise (resolve => {
        this.http.get(wat_url).subscribe((data: any) => {
            resolve(data.languages);
        }, err => {
            console.log(err);
        });
    });
  }

}
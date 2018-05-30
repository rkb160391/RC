
import { Http, Request, RequestOptionsArgs, Response, XHRBackend, RequestOptions, ConnectionBackend, Headers } from '@angular/http';
import { Router } from '@angular/router';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { Observable } from 'rxjs/Observable';
import { AuthdataService } from './authdata.service';

import { DataLoadSpinner } from './dataloadspinner.service';


export class HttpClientService extends Http {

    constructor(backend: ConnectionBackend, defaultOptions: RequestOptions, 
        private authdataService: AuthdataService ,private _spinner: DataLoadSpinner) {

        super(backend, defaultOptions);
    }

    request(url: string | Request, options?: RequestOptionsArgs): Observable<Response> {
        return this.intercept(super.request(url, options));
    }

    get(url: string, options?: RequestOptionsArgs): Observable<Response> {

        return this.intercept(super.get(url, this.getRequestOptionArgs(options)));
    }

    post(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {

        // this._spinner.show();

        return this.intercept(super.post(url, body, this.getRequestOptionArgs(options)));
    }

    put(url: string, body: string, options?: RequestOptionsArgs): Observable<Response> {
        return this.intercept(super.put(url, body, this.getRequestOptionArgs(options)));
    }

    delete(url: string, options?: RequestOptionsArgs): Observable<Response> {
        return this.intercept(super.delete(url, options));
    }

    options(url: string, options?: RequestOptionsArgs): Observable<Response> {
        return this.intercept(super.options(url, this.getRequestOptionArgs(options)));
    }

    getRequestOptionArgs(options?: RequestOptionsArgs): RequestOptionsArgs {
        if (options == null) {
            options = new RequestOptions();
        }
        if (options.headers == null) {
            options.headers = new Headers();
        }

        let authtoken: any = this.authdataService.getAuthToken();

        options.headers.append('AUTH-TOKEN', authtoken);


        return options;
    }

    intercept(observable: Observable<Response>): Observable<Response> {
        return observable.catch((err, source) => {
            if (err.status != 200) {
     if (err._body.type == "error") {
         this._spinner.showErrorMessage("");
         return observable;
     } else {
         this._spinner.showErrorMessage(JSON.parse(err._body));
         return observable;
     }
 }
});
           }
}



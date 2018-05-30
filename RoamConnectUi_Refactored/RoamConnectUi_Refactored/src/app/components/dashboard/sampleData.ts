export default {
    "isEnabled": null,
    "userName": null,
    "firstName": null,
    "lastName": null,
    "email": null,
    "lastLoggedIn": null,
    "featureConfig": null,
    "dashboardReports": [
        {
            "id": "0EDvbL",
            "name": "ROAM CONNECT",
            "type": "BT user",
            "viewType": null,
            "config": [
                {
                    "reportname": "DESTINATION",
                    "reportid": "as7niE",
                    "reportPolicy": {
                        "islink": false,
                        "url": false
                    }
                },
                {
                    "reportname": "DESTINATION-2",
                    "reportid": "obIP4t",
                    "reportPolicy": {
                        "islink": false,
                        "url": false
                    }
                }
            ],
            "reports": null
			
        },
        {
            "id": "2lMS47",
            "name": "LTE  WIFI",
            "type": "BT user",
            "viewType": null,
            "config": [
                {
                    "reportname": "DESTINATION",
                    "reportid": "as7niE",
                    "reportPolicy": {
                        "islink":true,
                        "url": "http://10.50.125.27:61006/npm/report?id=1901&startDate=09-10-2017&startIndex=0&timespan=Days&endDate=09-10-2017&endIndex=50&customer=NO_CUSTOMER&&startDate=09-10-2017&endDate=09-10-2017&timespan=Days&startIndex=0&endIndex=50"
                    }
                },
                {
                    "reportname": "DESTINATION-2",
                    "reportid": "obIP4t",
                    "reportPolicy": {
                        "islink": true,
                        "url": "http://10.50.125.27:61006/npm/report?id=1901&startDate=09-10-2017&startIndex=0&timespan=Days&endDate=09-10-2017&endIndex=50&customer=NO_CUSTOMER&&startDate=09-10-2017&endDate=09-10-2017&timespan=Days&startIndex=0&endIndex=50"
                    }
                }
            ],
            "reports": null
        }
    ]
};
export default {
    "reportType": "SecurityViolationReport",
    "response": {
      "userTemplateId": null,
      "reportConfiguration": {
        "configuration": {
          "attributes": null,
          "properties": [
            {
              "name": "INBOUND",
              "displayName": "Security Violations",
              "type": "",
              "refrenceField": "INBOUND_count",
              "level": 0,
              "subHeaders": [
                {
                  "name": "camelINBOUND",
                  "displayName": "camel",
                  "id": null,
                  "type": null,
                  "refrenceField": "INBOUND_countcamel",
                  "level": 3,
                  "subHeaders": null,
                  "link": true
                },
                {
                  "name": "diameterINBOUND",
                  "displayName": "diameter",
                  "id": null,
                  "type": null,
                  "refrenceField": "INBOUND_countdiameter",
                  "level": 3,
                  "subHeaders": null,
                  "link": true
                },
                {
                  "name": "gsmINBOUND",
                  "displayName": "gsm",
                  "id": null,
                  "type": null,
                  "refrenceField": "INBOUND_countgsm",
                  "level": 3,
                  "subHeaders": null,
                  "link": true
                }
              ],
              "link": true
            }
          ],
          "metrics": [],
          "name": "Security Violation Report",
          "type": "table",
          "subType": "pie",
          "entity": null,
          "legends": [
            {
              "name": "operatorName",
              "displayName": "Operator",
              "id": null,
              "type": "",
              "refrenceField": "operatorName",
              "level": 3,
              "subHeaders": [
                {
                  "name": "operatorName",
                  "displayName": "Operator",
                  "id": null,
                  "type": null,
                  "refrenceField": "operatorName",
                  "level": 0,
                  "subHeaders": null,
                  "link": true
                }
              ],
              "link": true
            },
            {
              "name": "Product",
              "displayName": "Product",
              "id": null,
              "type": "",
              "refrenceField": "product",
              "level": 3,
              "subHeaders": [
                {
                  "name": "Product",
                  "displayName": "Product",
                  "id": null,
                  "type": "",
                  "refrenceField": "product",
                  "level": 0,
                  "subHeaders": null,
                  "link": false
                }
              ],
              "link": false
            },
            {
              "name": "timeStamp",
              "displayName": "TimeStamp",
              "id": null,
              "type": "",
              "refrenceField": "timeStamp",
              "level": 3,
              "subHeaders": [
                {
                  "name": "timeStamp",
                  "displayName": "TimeStamp",
                  "id": null,
                  "type": "",
                  "refrenceField": "timeStamp",
                  "level": 0,
                  "subHeaders": null,
                  "link": false
                }
              ],
              "link": false
            }
          ],
          "context": null
        },
        "filter": null,
        "samplingPeriod": "15 minutes",
        "intervals": "last 2 years",
        "pagination": {
          "pagenumber": 1,
          "pagesize": 100000,
          "pagingSpec": null
        },
        "granularity": null,
        "level": 2,
        "datasource": null,
        "totalRecords": 0,
        "reportType": null
      },
      "response": null,
      "reports": [
        // {
        //     "timeStamp": "2017-02-23T10:00:00.000Z",
        //     "data": {
        //               "Operation_Type":{   
        //                                   "Operation Type:64": 5,
        //                                   "Operation Type:53": 3,
        //                                   "Operation Type:11": 7
        //                                   }
        //              },
        //     "operatorName": "Smartone (45406), Hong Kong",
        //     "product": "LTE"
        // }
        {
            "timeStamp": "2017-02-23T10:00:00.000Z",
            "data": {
                      "Inbound_Error_Code": {
                                              "InboundErrorCode:64": 5,
                                              "InboundErrorCode:53": 3,
                                              "InboundErrorCode:11": 7
                                            },
                      "Outbound_Error_Code": {
                                                "OutboundErrorCode:33": 8,
                                                "OutboundErrorCode:63": 6,
                                                "OutboundErrorCode:22": 1
                                             }
                    },
            "operatorName": "Smartone (45406), Hong Kong",
            "product": "LTE"
        }
      ],
      "headers": null
    }
  };